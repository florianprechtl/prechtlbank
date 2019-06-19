package service;

import entity.BankInstitute;
import entity.Transaction;
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

    private void validateBankInstitutionInput(BankInstitute bankInstitute) throws InvalidInputException {
        if(bankInstitute == null || bankInstitute.getBic() == null)
            throw new InvalidInputException("The bic is invalid.", null);

        if(bankInstitute == null || bankInstitute.getName() == null)
            throw new InvalidInputException("The name is invalid.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public BankInstitute createBankInstitute(BankInstitute bankInstitute)  throws InvalidInputException {
        logger.info("createBankInstitute :: Check bankInstitute data");
        validateBankInstitutionInput(bankInstitute);
        logger.info("createBankInstitute :: Create bankInstitute");
        em.persist(bankInstitute);
        logger.info("createBankInstitute :: Successfully created bankInstitute!");
        return bankInstitute;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankInstitute updateBankInstitute(BankInstitute bankInstitute) throws InvalidInputException {
        logger.info("updateBankInstitute :: Check bankInstitute data");
        validateBankInstitutionInput(bankInstitute);
        logger.info("updateBankInstitute :: Update bankInstitute");
        em.merge(bankInstitute);
        logger.info("updateBankInstitute :: Successfully updated bankInstitute!");
        return bankInstitute;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteBankInstituteById(Long id) {
        BankInstitute bankInstitute = getBankInstituteById(id);
        logger.info("deleteBankInstitute :: Delete bankInstitute");
        em.remove(bankInstitute);
        logger.info("deleteBankInstitute :: BankInstitute successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankInstitute getBankInstituteById(Long id) {
        BankInstitute bankInstitute =  em.createQuery("SELECT u FROM BankInstitute AS u WHERE id = ?1 OR id = ?1", BankInstitute.class)
                .setParameter(1, id)
                .getSingleResult();
        return bankInstitute;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankInstitute> getAllBankInstitutes() {
        List<BankInstitute> bankInstitutes =  em.createQuery("SELECT u FROM BankInstitute AS u", BankInstitute.class)
                .getResultList();
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

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
