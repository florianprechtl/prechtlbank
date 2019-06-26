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
    @Schedule(second="*", minute="*/30", hour="*", persistent=false)
    public void TransactionScheduler() {
        List<Transaction> plannedTransactions = transactionService.getAllPlannedTransactions();
        Iterator<Transaction> transactionIterator = plannedTransactions.iterator();
        while(transactionIterator.hasNext()) {
            Transaction transaction = cloneTransaction(transactionIterator.next());
            if (overdueTransactionInterval(transaction)) {
                try {
                    if (transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
                        transactionService.transfer(transaction);
                    } else {
                        transactionService.directDebit(transaction);
                    }
                } catch (TransactionException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public Transaction cloneTransaction(Transaction transaction) {
        Transaction clonedTransaction = new Transaction();

        clonedTransaction.setAmount(transaction.getAmount());
        clonedTransaction.setDate(transaction.getDate());
        clonedTransaction.setReasonOfUsage(transaction.getReasonOfUsage());
        clonedTransaction.setDuration(Duration.ONCE);
        clonedTransaction.setTransactionType(transaction.getTransactionType());
        clonedTransaction.setTransactionStatus(transaction.getTransactionStatus());
        clonedTransaction.setPayee(transaction.getPayee());
        clonedTransaction.setPayer(transaction.getPayer());

        return clonedTransaction;
    }

    public boolean overdueTransactionInterval(Transaction transaction) {
        Calendar now = Calendar.getInstance();
        Calendar dueToDate = Calendar.getInstance();
        dueToDate.setTime(new Date(transaction.getLastTransactionDate().getTime()));

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
        return dueToDate.after(now);
    }
}
