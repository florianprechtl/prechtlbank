package service;

import entity.Transaction;
import entity.enums.Duration;
import entity.enums.TransactionType;
import org.apache.log4j.Logger;
import service.Exceptions.TransactionException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Singleton
public class TimingService {

    @Inject
    private TransactionService transactionService;

    @Inject
    private Logger logger;

    /**
     *  Checks in a specific time interval if the planned transactions have to be renewed
     */
    @Schedule(second="*", minute="*", hour="*/12", persistent=false)
    public void TransactionScheduler() {
        logger.info("Scheduler started");
        List<Transaction> plannedTransactions = transactionService.getAllPlannedTransactions();
        Iterator<Transaction> transactionIterator = plannedTransactions.iterator();
        while(transactionIterator.hasNext()) {
            logger.info("Scheduler checks transaction");
            Transaction transaction = transactionIterator.next();
            if (overdueTransactionInterval(transaction)) {
                Transaction newTransaction = cloneTransaction(transaction);
                try {
                    if (newTransaction.getTransactionType().equals(TransactionType.TRANSFER)) {
                        transactionService.transfer(newTransaction);
                    } else {
                        transactionService.directDebit(newTransaction);
                    }
                } catch (TransactionException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public Transaction cloneTransaction(Transaction transaction) {
        Transaction clonedTransaction = new Transaction();
        Date date = new Date();

        clonedTransaction.setAmount(transaction.getAmount());
        clonedTransaction.setDate(date);
        clonedTransaction.setReasonOfUsage("Automatische Transaktion: " + transaction.getReasonOfUsage());
        clonedTransaction.setDuration(Duration.ONCE);
        clonedTransaction.setTransactionType(transaction.getTransactionType());
        clonedTransaction.setTransactionStatus(transaction.getTransactionStatus());
        clonedTransaction.setPayee(transaction.getPayee());
        clonedTransaction.setPayer(transaction.getPayer());
        clonedTransaction.setLastTransactionDate(date);

        return clonedTransaction;
    }

    public boolean overdueTransactionInterval(Transaction transaction) {
        Calendar now = Calendar.getInstance();
        Calendar dueToDate = Calendar.getInstance();
        dueToDate.setTime(transaction.getLastTransactionDate());

        switch (transaction.getDuration()) {
            case DAILY:
                dueToDate.add(Calendar.HOUR, 24);
                break;
            case WEEKLY:
                dueToDate.add(Calendar.DAY_OF_YEAR, 7);
                break;
            case MONTHLY:
                dueToDate.add(Calendar.MONTH, 1);
                break;
            case ANNUALLY:
                dueToDate.add(Calendar.YEAR, 1);
                break;

        }
        return dueToDate.before(now);
    }
}
