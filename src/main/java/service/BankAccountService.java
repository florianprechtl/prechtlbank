package service;

import entity.BankAccount;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class BankAccountService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;


    private void validateBankAccountInput(BankAccount bankAccount) throws InvalidBankAccountInputException {
        if(bankAccount == null || bankAccount.getAccountStatus() == null)
            throw new BankAccountService.InvalidBankAccountInputException("The Account status is invalid.", null);

        if(bankAccount == null || bankAccount.getBankingInstitute() == null)
            throw new BankAccountService.InvalidBankAccountInputException("The Banking institute is invalid.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createBankAccount(BankAccount bankAccount) throws InvalidBankAccountInputException {
        validateBankAccountInput(bankAccount);
        em.persist(bankAccount);
        logger.info("jaj :: jaj");
    }

    public static class InvalidBankAccountInputException extends Exception {
        public InvalidBankAccountInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
