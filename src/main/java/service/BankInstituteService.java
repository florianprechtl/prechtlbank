package service;

import entity.BankInstitute;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class BankInstituteService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;

    @Transactional(Transactional.TxType.REQUIRED)
    public void createBankInstitute(BankInstitute bankInstitute) {
        em.persist(bankInstitute);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteBankInstituteByBic(String bic) {
        BankInstitute bankInstitute = getBankInstituteByBic(bic);
        em.remove(bankInstitute);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankInstitute getBankInstituteByBic(String bic) {
        Query query = em.createQuery("SELECT u FROM BankInstitute AS u WHERE u.bic = ?1", BankInstitute.class);
        query.setParameter(1, bic);
        try {
            return (BankInstitute) query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankInstitute> getAllBankInstitutes() {
        List<BankInstitute> bankInstitutes =  em.createQuery("SELECT u FROM BankInstitute AS u", BankInstitute.class).getResultList();
        return bankInstitutes;
    }

    public void logAllBankInstituates() {
        List<BankInstitute> users = getAllBankInstitutes();
        Iterator<BankInstitute> iterator = users.iterator();
        while(iterator.hasNext()) {
            BankInstitute bankInstitute = iterator.next();
            logger.info("logAllBankInstitutes :: " + bankInstitute.getName() + "   " + bankInstitute.getBic());
        }
    }
}
