package service;

import entity.BankAccount;
import entity.Transaction;
import entity.dto.LoginDTO;
import entity.dto.TransactionDTO;
import org.apache.log4j.Logger;
import service.Exceptions.LoginException;
import service.Exceptions.TransactionException;

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

    @Inject
    BankAccountService bankAccountService;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private transient Logger logger;

    private void validateTransactionInput(Transaction transaction) throws InvalidInputException {
        if (transaction == null)
            throw new InvalidInputException("Invalid Transaction: Transaction is null", null);

        if (transaction.getPayeeBic() == null)
            throw new InvalidInputException("Invalid Payee BIC.", null);

        if (transaction.getPayeeIban() == null)
            throw new InvalidInputException("Invalid Payee IBAN.", null);

        if (!checkIbanAndBic(transaction.getPayeeIban(), transaction.getPayeeBic()))
            throw new InvalidInputException("Could not find payee account with specified iban and bic", null);

        if (transaction.getPayerBic() == null)
            throw new InvalidInputException("Invalid Payer BIC.", null);

        if (transaction.getPayerIban() == null)
            throw new InvalidInputException("Invalid Payer IBAN.", null);

        if (!checkIbanAndBic(transaction.getPayerIban(), transaction.getPayerBic()))
            throw new InvalidInputException("Could not find payer account with specified iban and bic", null);

        if (transaction.getPayeeIban().equals(transaction.getPayerIban()))
            throw new InvalidInputException("You can not transfer money to yourself", null);

        if (transaction.getPayerIban() == null)
            throw new InvalidInputException("Invalid Payer IBAN.", null);

        if (transaction.getAmount() <= 0)
            throw new InvalidInputException("Invalid amount.", null);

        if (transaction.getReasonOfUsage() == null)
            throw new InvalidInputException("Invalid reason of usage.", null);

        if(transaction.getTransactionStatus() == null)
            throw new InvalidInputException("Invalid transaction status.", null);

        if (transaction.getTransactionType() == null)
            throw new InvalidInputException("Invalid transaction type.", null);

        if (transaction.getDuration() == null)
            throw new InvalidInputException("Invalid duration.", null);
        logger.info("Logger :: nix");
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDTO transfer(LoginDTO loginDTO, TransactionDTO transactionDTO) throws LoginException, TransactionException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        return new TransactionDTO(transfer(transaction));
    }

    @Override
    public TransactionDTO directDebit(LoginDTO loginDTO, TransactionDTO transactionDTO) throws LoginException, TransactionException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        return new TransactionDTO(transfer(transaction));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Transaction transfer(Transaction transaction) throws InvalidInputException {
        logger.info("transfer :: Check Transaction data");
        validateTransactionInput(transaction);
        logger.info("transfer :: Save transaction");
        em.persist(transaction);
        logger.info("transfer :: Successfully saved transaction!");
        return transaction;
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

    public boolean checkIbanAndBic(String iban, String bic) {
        BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
        if (bankAccount != null && iban != null && bic!= null) {
            if (bankAccount.getBankInstitute().getBic().equals(bic)) {
                return true;
            }
        }
        return false;
    }

    public static class InvalidInputException extends TransactionException {
        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
