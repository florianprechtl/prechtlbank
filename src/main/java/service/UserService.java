package service;

import entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
    public User registerUser(User user) throws InvalidInputException, DuplicateUserException{
        logger.info("registerUser :: Validate user input");
        validateUserInput(user);

        logger.info("registerUser :: Check if user does already exist");
        checkForExistingUser(user);

        em.persist(user);
        logger.info("registerUser :: User with loginId: " + user.getLoginId() + " successfully registered");

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


    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateUserException extends Exception {
        public DuplicateUserException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}