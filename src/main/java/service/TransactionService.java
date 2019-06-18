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
public class TransactionService implements TransactionServiceIF{

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
    public TransactionDTO transfer(LoginDTO loginDTO, TransactionDTO transactionDTO) throws UserService.LoginException, TransactionException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        return new TransactionDTO(transfer(transaction));
    }

    @Override
    public TransactionDTO directDebit(LoginDTO loginDTO, TransactionDTO transactionDTO) throws UserService.LoginException, TransactionException {
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Transaction transfer(Transaction transaction) throws InvalidInputException {
        try {
            logger.info("transfer :: Check Transaction data");
            validateTransactionInput(transaction);
            logger.info("transfer :: Save transaction");
            em.persist(transaction);
            logger.info("transfer :: Successfully saved transaction!");
            return transaction;
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

    public static class InvalidInputException extends TransactionException {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TransactionException extends Exception {
        public TransactionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
