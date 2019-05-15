package entity.repo;


import entity.LoginAccount;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class LoginAccountRepo extends SingleIdEntityRepository<String, LoginAccount> {
}
