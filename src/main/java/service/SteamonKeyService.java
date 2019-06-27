package service;

import entity.SteamonKey;
import entity.User;
import entity.repo.SteamonKeyRepo;
import entity.repo.UserRepo;
import org.apache.log4j.Logger;
import service.Exceptions.ValidationException;
import ui.models.LoginUserModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class SteamonKeyService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Logger logger;

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private SteamonKeyRepo steamonKeyRepo;

    @Inject
    private UserRepo userRepo;

    @Inject UserService userService;

    @Transactional(Transactional.TxType.REQUIRED)
    public SteamonKey persistSteamonKey(String keyCode) throws ValidationException {
        SteamonKey steamonKey = new SteamonKey(keyCode, loginUserModel.getUser());
        em.persist(steamonKey);
        steamonKey = getSteamonKeyByKeyCode(keyCode);
        User user = userRepo.getById(loginUserModel.getUser().getId());
        user.setSteamonKey(steamonKey);
        userService.updateUser(user);
        return steamonKey;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteSteamonKeyById(Long id) {
        SteamonKey steamonKey = steamonKeyRepo.getById(id);
        logger.info("deleteSteamonKey :: Delete steamonKey");
        em.remove(steamonKey);
        logger.info("deleteSteamonKey :: SteamonKey successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public SteamonKey getSteamonKeyByKeyCode(String keyCode) {
        SteamonKey steamonKey =  em.createQuery("SELECT u FROM SteamonKey AS u WHERE keyCode = ?1", SteamonKey.class)
                .setParameter(1, keyCode)
                .getSingleResult();
        return steamonKey;
    }


}
