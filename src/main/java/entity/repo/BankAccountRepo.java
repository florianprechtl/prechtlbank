package entity.repo;

import entity.BankAccount;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class BankAccountRepo extends SingleIdEntityRepository<Long, BankAccount>

{
}
