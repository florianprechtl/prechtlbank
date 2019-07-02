package service;

import entity.BankAccount;
import entity.Transaction;
import entity.dto.LoginDTO;
import entity.dto.TransactionDTO;
import entity.enums.BankAccountStatus;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;
import entity.repo.TransactionRepo;
import org.apache.log4j.Logger;
import service.Exceptions.LoginException;
import service.Exceptions.TransactionException;
import service.Exceptions.ValidationException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebService
@RequestScoped
public class TransactionService implements TransactionServiceIF{

    @Inject
    private UserService userService;

    @Inject
    private BankAccountService bankAccountService;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Logger logger;

    @Inject
    private TransactionRepo transactionRepo;

    private void validateTransactionInput(Transaction transaction) throws ValidationException {
        if (transaction == null)
            throw new ValidationException("Invalid Transaction: Transaction is null", null);

        if (transaction.getPayee() == null)
            throw new ValidationException("Invalid Payee.", null);

        if (transaction.getPayee().getBankInstitute() == null)
            throw new ValidationException("Invalid Payee bankInstitute.", null);

        if (transaction.getPayee().getBankInstitute().getBic() == null)
            throw new ValidationException("Invalid Payee BIC.", null);

        if (transaction.getPayee().getIban() == null)
            throw new ValidationException("Invalid Payee IBAN.", null);

        if (transaction.getPayer().getAccountStatus() != BankAccountStatus.APPROVED)
            throw new ValidationException("Payer account is not approved.", null);

        if (transaction.getPayee().getAccountStatus() != BankAccountStatus.APPROVED)
            throw new ValidationException("Payee account is not approved.", null);

        if (transaction.getPayer() == null)
            throw new ValidationException("Invalid Payer.", null);

        if (transaction.getPayer().getBankInstitute() == null)
            throw new ValidationException("Invalid Payer bankInstitute.", null);

        if (transaction.getPayer().getBankInstitute().getBic() == null)
            throw new ValidationException("Invalid Payer BIC.", null);

        if (transaction.getPayer().getIban() == null)
            throw new ValidationException("Invalid Payer IBAN.", null);

        if (!checkIbanAndBic(transaction.getPayee().getIban(), transaction.getPayee().getBankInstitute().getBic()))
            throw new ValidationException("Could not find payee account with specified iban and bic", null);

        if (!checkIbanAndBic(transaction.getPayer().getIban(), transaction.getPayer().getBankInstitute().getBic()))
            throw new ValidationException("Could not find payer account with specified iban and bic", null);

        if (transaction.getAmount() <= 0)
            throw new ValidationException("Invalid amount.", null);

        if (transaction.getReasonOfUsage() == null)
            throw new ValidationException("Invalid reason of usage.", null);

        if(transaction.getTransactionStatus() == null)
            throw new ValidationException("Invalid transaction status.", null);

        if (transaction.getTransactionType() == null)
            throw new ValidationException("Invalid transaction type.", null);

        if (transaction.getDuration() == null)
            throw new ValidationException("Invalid duration.", null);

        if (transaction.getDate() == null)
            throw new ValidationException("Invalid date.", null);
    }

    @Override
    @WebMethod
    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDTO transfer(LoginDTO loginDTO, TransactionDTO transactionDTO) throws LoginException, TransactionException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        transaction = setPayeeAndPayer(transaction);
        return new TransactionDTO(transfer(transaction));
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDTO giveMoneyToIban(Double amount, String iban) {
        BankAccount payee = bankAccountService.getBankAccountByIban(iban);
        BankAccount payer = bankAccountService.getBank();
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPayee(payee);
        transaction.setPayer(payer);
        transaction.setTransactionStatus(TransactionStatus.DONE);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setDuration((Duration.ONCE));
        transaction.setReasonOfUsage("BANK gibt dir Geld");
        transaction.setDate(new Date());
        em.persist(transaction);
        return new TransactionDTO(transaction);
    }

    @Override
    @WebMethod
    @Transactional(Transactional.TxType.REQUIRED)
    public TransactionDTO directDebit(LoginDTO loginDTO, TransactionDTO transactionDTO) throws LoginException, TransactionException {
        userService.loginUser(loginDTO);
        Transaction transaction = new Transaction(transactionDTO);
        transaction = setPayeeAndPayer(transaction);
        return new TransactionDTO(transfer(transaction));
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Transaction directDebit(Transaction transaction) throws TransactionException {
        try {
            logger.info("directDebit :: Check directDebit data");
            validateTransactionInput(transaction);
            logger.info("directDebit :: Save directDebit");
            em.persist(transaction);
            logger.info("directDebit :: Successfully saved transaction as directDebit!");
        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
        return transaction;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Transaction transfer(Transaction transaction) throws TransactionException {
        try {
            logger.info("transfer :: Check transfer data");
            validateTransactionInput(transaction);
            logger.info("transfer :: Save transfer");
            em.persist(transaction);
            logger.info("transfer :: Successfully saved transaction as Transfer!");
        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
        return transaction;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Transaction> getAllTransactionsByIban(String iban) {
        List<Transaction> transactions =  em.createQuery("SELECT u FROM Transaction AS u WHERE payee.iban = ?1 OR payer.iban = ?1", Transaction.class)
                .setParameter(1, iban)
                .getResultList();
        return transactions;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Transaction> getAllDoneTransactionsByIban(String iban) {
        List<Transaction> transactions =  em.createQuery("SELECT u FROM Transaction AS u WHERE transactionStatus = ?2 AND (payee.iban = ?1 OR payer.iban = ?1)", Transaction.class)
                .setParameter(1, iban)
                .setParameter(2, TransactionStatus.DONE)
                .getResultList();
        return transactions;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Transaction updateTransaction(Transaction transaction) throws ValidationException {
        logger.info("updateTransaction :: Check Transaction data");
        validateTransactionInput(transaction);
        logger.info("updateTransaction :: Update transaction");
        em.merge(transaction);
        logger.info("updateTransaction :: Successfully updated transaction!");
        return transaction;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteTransactionById(Long id) {
        Transaction transaction = transactionRepo.getById(id);
        logger.info("deleteTransaction :: Delete transaction");
        em.remove(transaction);
        logger.info("deleteTransaction :: Transaction successfully deleted");
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteTransactionsByIban(String iban) {
        List<Transaction> transactions =  em.createQuery("SELECT u FROM Transaction AS u WHERE payee.iban = ?1 OR payer.iban = ?1", Transaction.class)
                .setParameter(1, iban)
                .getResultList();
        logger.info("deleteTransaction :: Delete transactions");
        Iterator<Transaction> transactionIterator = transactions.iterator();
        while(transactionIterator.hasNext()) {
            em.remove(transactionIterator.next());
        }
        logger.info("deleteTransaction :: Transactions successfully deleted");
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Transaction> getAllPlannedTransactions() {
        List<Transaction> transactions =  em.createQuery("SELECT u FROM Transaction AS u WHERE duration != ?1", Transaction.class)
                .setParameter(1, Duration.ONCE)
                .getResultList();
        return transactions;
    }


    @WebMethod(exclude = true)
    public boolean checkIbanAndBic(String iban, String bic) {
        BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
        if (bankAccount != null && iban != null && bic!= null) {
            if (bankAccount.getBankInstitute().getBic().equals(bic)) {
                return true;
            }
        }
        return false;
    }

    @WebMethod(exclude = true)
    public Transaction setPayeeAndPayer(Transaction transaction) {
        String ibanPayee = transaction.getPayee().getIban();
        String ibanPayer = transaction.getPayer().getIban();
        if (ibanPayee != null) {
            transaction.setPayee(bankAccountService.getBankAccountByIban(ibanPayee));
        }
        if (ibanPayer != null) {
            transaction.setPayer(bankAccountService.getBankAccountByIban(ibanPayer));
        }
        return transaction;
    }
}
