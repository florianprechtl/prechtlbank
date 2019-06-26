package entity.repo;

import entity.User;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class UserRepo extends SingleIdEntityRepository<Long, User> {

}
