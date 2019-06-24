package service;

import entity.Transaction;
import org.apache.log4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

@Singleton
public class TimingService {

    @Inject
    private TransactionService transactionService;

    @Inject
    private Logger logger;

    @Schedule(second="*", minute="*", hour="*/24", persistent=false)
    public void TransactionScheduler() {
        logger.info("now!!!!");
        List<Transaction> plannedTransactions = transactionService.getAllPlannedTransactions();
        Iterator<Transaction> transactionIterator = plannedTransactions.iterator();
        while(transactionIterator.hasNext()) {
            logger.info(transactionIterator.next());
        }
    }
}
