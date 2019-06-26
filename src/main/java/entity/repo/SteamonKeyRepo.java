package entity.repo;

import entity.SteamonKey;
import entity.util.SingleIdEntityRepository;

import javax.enterprise.context.Dependent;

@Dependent
public class SteamonKeyRepo extends SingleIdEntityRepository<Long, SteamonKey> {

}
