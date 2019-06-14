package service;

import entity.BankingInstitute;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class BankInstituteService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;

    @Transactional(Transactional.TxType.REQUIRED)
    public void createBankInstitute(BankingInstitute bankingInstitute) {
        em.persist(bankingInstitute);
    }
}
