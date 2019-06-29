package service;

import entity.SteamonKey;
import entity.User;
import entity.dto.LoginDTO;
import entity.repo.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import service.Exceptions.LoginException;
import service.Exceptions.ValidationException;
import ui.models.LoginUserModel;

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
    private Logger logger;

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private UserRepo userRepo;

    private void validateUserInput(User user, boolean update) throws ValidationException {

        if (user == null)
            throw new ValidationException("User is null.", null);

        if (user.getLoginId() == null || user.getLoginId().length() < 4)
            throw new ValidationException("The generated Login Id is not valid.", null);

        if (user.getFirstname() == null || user.getFirstname().length() < 1)
            throw new ValidationException("The entered First Name is too short.", null);

        if (user.getLastname() == null || user.getLastname().length() < 1)
            throw new ValidationException("The entered Last Name is too short.", null);

        if (user.getAddress().getStreet() == null || user.getAddress().getStreet().length() < 2)
            throw new ValidationException("The entered Street is too short.", null);

        if (user.getAddress().getCity() == null || user.getAddress().getCity().length() < 2)
            throw new ValidationException("The entered City is too short.", null);

        if (!StringUtils.isNumeric(user.getAddress().getZip()))
            throw new ValidationException("The entered ZIP Code is not valid.", null);

        if (user.getAddress().getCountry() == null || user.getAddress().getCountry().length() < 2)
            throw new ValidationException("The entered Country is too short.", null);

        if (user.getUserType() == null)
            throw new ValidationException("userType is not set.", null);

        if (user.getPassword() == null || user.getPassword().length() < 2)
            throw new ValidationException("Password is invalid or too short (min: 2 characters).", null);

        if (!update) {
            User existingUser = getUserByLoginId(user.getLoginId());
            if (existingUser != null) {
                throw new ValidationException("Duplicate User: User with loginId: " + user.getLoginId() + " does already exist.", null);
            }
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User registerUser(User user) throws ValidationException {
        logger.info("registerUser :: Validate user input");
        validateUserInput(user, false);

        em.persist(user);
        logger.info("registerUser :: User with loginId: " + user.getLoginId() + " successfully registered");

        return user;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public User updateUser(User user) throws ValidationException {
        logger.info("updateUser :: Validate user input");
        validateUserInput(user, true);

        em.merge(user);
        logger.info("updateUser :: User successfully updated");

        return user;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteUserById(Long id) {
        User user = userRepo.getById(id);
        logger.info("deleteUser :: delete dependencies");
/*
        transactionService.deleteTransactionByUserId(id);
*/
        bankAccountService.deleteBankAccountByUserId(id);
        logger.info("deleteUser :: delete user");
        em.remove(user);

        logger.info("deleteUser :: user successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User loginUser(LoginDTO loginData) throws LoginException {
        logger.info("loginUser :: Check Login DTO data");
        if(loginData == null || loginData.getLoginId() == null || loginData.getPassword() == null)
            throw new LoginException("Entered data seems to be incomplete or invalid.", null);

        logger.info("loginUser :: Check credentials");
        User user = getUserByLoginId(loginData.getLoginId());
        if (user == null) {
            throw new LoginException("User '" + loginData.getLoginId() + "' does not exist.", null);
        }
        if (!user.checkPassword(loginData.getPassword())) {
            throw new LoginException("The entered password is wrong.", null);
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

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateSteamonKey(SteamonKey steamonKey) {
        User user = userRepo.getById(loginUserModel.getUser().getId());
        user.setSteamonKey(null);
        em.persist(user);
        user.setSteamonKey(steamonKey);
        em.persist(user);
    }
}
