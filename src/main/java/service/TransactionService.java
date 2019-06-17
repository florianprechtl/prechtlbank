package service;

import entity.Transaction;
import entity.User;
import entity.dto.LoginDTO;
import entity.dto.TransactionDTO;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@RequestScoped
public class TransactionService {

    @Inject
    UserService userService;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;

    private void validateTransactionInput(Transaction transaction) throws InvalidInputException
    {
        if(transaction == null || transaction.getPayeeBic() == null)
            throw new InvalidInputException("Invalid Payee BIC.", null);
        if(transaction == null || transaction.getPayeeIban() == null)
            throw new InvalidInputException("Invalid Payee IBAN.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void transfer(LoginDTO loginDTO, TransactionDTO transactionDTO) throws UserService.InvalidCredentialsException, InvalidInputException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        transfer(transaction);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void transfer(Transaction transaction) throws InvalidInputException {
        try {
            logger.info("transfer :: Check Transaction data");
            validateTransactionInput(transaction);
            logger.info("transfer :: Save transaction");
            em.persist(transaction);
            logger.info("transfer :: Successfully saved transaction!");
        } catch(Exception e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions =  em.createQuery("SELECT u FROM Transaction AS u", Transaction.class).getResultList();
        return transactions;
    }

    public void logAllTransactions() {
        List<Transaction> transactions = getAllTransactions();
        Iterator<Transaction> iterator = transactions.iterator();
        while(iterator.hasNext()) {
            Transaction transaction = iterator.next();
            logger.info("logAllTransactions :: " + transaction);
        }
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
