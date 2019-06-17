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


    private void validateBankAccountInput(BankAccount bankAccount) throws InvalidBankAccountInputException {
        if(bankAccount == null || bankAccount.getAccountStatus() == null)
            throw new BankAccountService.InvalidBankAccountInputException("The Account status is invalid.", null);

        if(bankAccount == null || bankAccount.getBankInstitute() == null)
            throw new BankAccountService.InvalidBankAccountInputException("The Banking institute is invalid.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createBankAccount(BankAccount bankAccount) throws InvalidBankAccountInputException {
        validateBankAccountInput(bankAccount);
        em.persist(bankAccount);
        logger.info("jaj :: jaj");
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteBankAccountByIban(String iban) {
        BankAccount bankAccount = getBankAccountByIban(iban);
        em.remove(bankAccount);
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

    public static class InvalidBankAccountInputException extends Exception {
        public InvalidBankAccountInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
