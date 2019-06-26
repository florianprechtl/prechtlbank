package service;

import entity.SteamonKey;
import entity.repo.SteamonKeyRepo;
import org.apache.log4j.Logger;
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

    @Transactional(Transactional.TxType.REQUIRED)
    public SteamonKey persistSteamonKey(String keyCode) {
        SteamonKey steamonKey = new SteamonKey(keyCode, loginUserModel.getUser());
        loginUserModel.getUser().setSteamonKey(steamonKey);
        em.persist(loginUserModel.getUser());
        em.persist(steamonKey);
        return steamonKey;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteSteamonKeyById(Long id) {
        SteamonKey steamonKey = steamonKeyRepo.getById(id);
        logger.info("deleteSteamonKey :: Delete steamonKey");
        em.remove(steamonKey);
        logger.info("deleteSteamonKey :: SteamonKey successfully deleted");
    }
}
