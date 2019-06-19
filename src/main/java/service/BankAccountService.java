package service;

import entity.BankAccount;
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
public class BankAccountService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;


    private void validateBankAccountInput(BankAccount bankAccount) throws InvalidInputException {
        if(bankAccount == null || bankAccount.getAccountStatus() == null)
            throw new InvalidInputException("The Account status is invalid.", null);

        if(bankAccount == null || bankAccount.getBankInstitute() == null)
            throw new InvalidInputException("The Banking institute is invalid.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createBankAccount(BankAccount bankAccount) throws InvalidInputException {
        validateBankAccountInput(bankAccount);
        em.persist(bankAccount);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount updateBankAccount(BankAccount bankAccount) throws InvalidInputException {
        logger.info("updateBankAccount :: Check bankAccount data");
        validateBankAccountInput(bankAccount);
        logger.info("updateBankAccount :: Update bankAccount");
        em.merge(bankAccount);
        logger.info("updateBankAccount :: Successfully updated bankAccount!");
        return bankAccount;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteBankAccountById(Long id) {
        BankAccount bankAccount = getBankAccountById(id);
        logger.info("deleteBankAccount :: Delete bankAccount");
        em.remove(bankAccount);
        logger.info("deleteBankAccount :: BankAccount successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount getBankAccountById(long id) {
        BankAccount bankAccount =  em.createQuery("SELECT b FROM BankAccount AS b WHERE b.id = ?1", BankAccount.class)
                .setParameter(1, id)
                .getSingleResult();
        return bankAccount;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount getBankAccountByIban(String iban) {
        Query query = em.createQuery("SELECT u FROM BankAccount AS u WHERE u.iban = ?1", BankAccount.class);
        query.setParameter(1, iban);
        try {
            return (BankAccount) query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankAccount> getBankAccountsOfUser(long userId) {
        List<BankAccount> bankAccounts =  em.createQuery("SELECT b FROM BankAccount AS b WHERE b.user.id = ?1", BankAccount.class)
                .setParameter(1, userId)
                .getResultList();
        return bankAccounts;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts =  em.createQuery("SELECT u FROM BankAccount AS u", BankAccount.class).getResultList();
        return bankAccounts;
    }

    public void logAllBankAccounts() {
        List<BankAccount> users = getAllBankAccounts();
        Iterator<BankAccount> iterator = users.iterator();
        while(iterator.hasNext()) {
            BankAccount bankAccount = iterator.next();
            logger.info("logAllBankAccounts :: " + bankAccount);
        }
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
