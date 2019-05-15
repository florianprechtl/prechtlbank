package service;

import entity.LoginAccount;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
@WebService
public class TestService {

    // Dependencies
    @PersistenceContext(unitName = "prechtlBankPU")
    private EntityManager entityManager;

    @Inject
    private transient Logger logger;

    @Inject


    @PostConstruct
    public void init() {
        logger.info("test :: Inizialisiert!");

        LoginAccount loginAccount = new LoginAccount("loginId", "password");
        entityManager.persist(loginAccount);

        logger.info("test :: Persistiert!");
        logger.info("test :: Persistiert!");
        logger.info("test :: Persistiert!");



    }



}
