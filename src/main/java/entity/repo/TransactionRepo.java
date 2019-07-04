package entity.repo;

import entity.Transaction;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class TransactionRepo extends SingleIdEntityRepository<Long, Transaction> {

}
