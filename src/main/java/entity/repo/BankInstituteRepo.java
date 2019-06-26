package entity.repo;

import entity.BankInstitute;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class BankInstituteRepo extends SingleIdEntityRepository<Long, BankInstitute> {

}
