package service;

import entity.LoginAccount;
import entity.repo.LoginAccountRepo;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@WebService
public class TestService {

    // Dependencies
    @PersistenceContext(unitName = "prechtlBankPU")
    private EntityManager entityManager;

    @Inject
    private transient Logger logger;

    @Inject
    private LoginAccountRepo loginAccountRepo;

    @Transactional(Transactional.TxType.REQUIRED)
    public void init() {
        logger.info("test :: Inizialisiert!");

        LoginAccount loginAccount = new LoginAccount("loginId", "password");
        entityManager.persist(loginAccount);

        logger.info("test :: Persistiert!");

        LoginAccount lFound = entityManager.find(LoginAccount.class, loginAccount.getId());
        List<LoginAccount> accounts = loginAccountRepo.getAll();

        logger.info("test :: " + accounts.size());

    }

    @WebMethod(exclude = true)
    public List<LoginAccount> getAllLoginAccounts() {
        return loginAccountRepo.getAll();
    }




}
