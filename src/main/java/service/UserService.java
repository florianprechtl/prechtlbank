package service;

import entity.User;
import entity.dto.LoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import service.Exceptions.LoginException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;

    private void validateUserInput(User user) throws InvalidInputException
    {
        //
        if(user == null || user.getLoginId() == null || user.getLoginId().length() < 4)
            throw new InvalidInputException("The generated Login Id is not valid.", null);

        if(user.getFirstname() == null || user.getFirstname().length() < 1)
            throw new InvalidInputException("The entered First Name is too short.", null);

        if(user.getLastname() == null || user.getLastname().length() < 1)
            throw new InvalidInputException("The entered Last Name is too short.", null);

        if(user.getAddress().getStreet() == null || user.getAddress().getStreet().length() < 2)
            throw new InvalidInputException("The entered Street is too short.", null);

        if(user.getAddress().getCity() == null || user.getAddress().getCity().length() < 2)
            throw new InvalidInputException("The entered City is too short.", null);

        if(!StringUtils.isNumeric(user.getAddress().getZip()))
            throw new InvalidInputException("The entered ZIP Code is not valid.", null);

        if(user.getAddress().getCountry() == null || user.getAddress().getCountry().length() < 2)
            throw new InvalidInputException("The entered Country is too short.", null);
    }

    private void checkForExistingUser(User user) throws DuplicateUserException {
        User existingUser = getUserByLoginId(user.getLoginId());
        if (existingUser != null) {
            throw new DuplicateUserException("Duplicate User: User with loginId: " + user.getLoginId() + " does already exist.", null);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User registerUser(User user) throws InvalidInputException, DuplicateUserException {
        logger.info("registerUser :: Validate user input");
        validateUserInput(user);

        logger.info("registerUser :: Check if user does already exist");
        checkForExistingUser(user);

        em.persist(user);
        logger.info("registerUser :: User with loginId: " + user.getLoginId() + " successfully registered");

        return user;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User updateUser(User user) throws InvalidInputException {
        logger.info("updateUser :: Validate user input");
        validateUserInput(user);

        em.merge(user);
        logger.info("updateUser :: User successfully updated");

        return user;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteUserById(Long id) {
        logger.info("deleteUser :: check loginId");
        User user = getUserById(id);

        logger.info("deleteUser :: delete dependencies");
        // TODO: Delete Dependencies
        logger.info("deleteUser :: delete user");
        em.remove(user);

        logger.info("deleteUser :: user successfully deleted");
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User loginUser(LoginDTO loginData) throws InvalidCredentialsException {
        logger.info("loginUser :: Check Login DTO data");
        if(loginData == null || loginData.getLoginId() == null || loginData.getPassword() == null)
            throw new InvalidCredentialsException("Entered data seems to be incomplete or invalid.", null);

        logger.info("loginUser :: Check credentials");
        User user = getUserByLoginId(loginData.getLoginId());
        if (user == null) {
            throw new InvalidCredentialsException("User '" + loginData.getLoginId() + "' does not exist.", null);
        }
        if (!user.checkPassword(loginData.getPassword())) {
            throw new InvalidCredentialsException("The entered password is wrong.", null);
        }
        return user;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User getUserByLoginId(String loginId) {
        Query query = em.createQuery("SELECT u FROM User AS u WHERE u.loginId = ?1", User.class);
        query.setParameter(1, loginId);
        try {
            return (User)query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User getUserById(Long id) {
        Query query = em.createQuery("SELECT u FROM User AS u WHERE u.id = ?1", User.class);
        query.setParameter(1, id);
        try {
            return (User)query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<User> getAllUsers() {
        List<User> users =  em.createQuery("SELECT u FROM User AS u", User.class).getResultList();
        return users;
    }

    public void logAllUsers() {
        List<User> users = getAllUsers();
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            logger.info("logAllUsers :: " + user.getPassword() + "   " + user.getLoginId());
        }
    }

    public static class InvalidInputException extends LoginException {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateUserException extends LoginException {
        public DuplicateUserException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidCredentialsException extends LoginException {
        public InvalidCredentialsException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
