package service;

import entity.SteamonKey;
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

    @Transactional(Transactional.TxType.REQUIRED)
    public void persistSteamonKey(String keyCode) {
        SteamonKey steamonKey = new SteamonKey(keyCode, loginUserModel.getUser());
        loginUserModel.getUser().setSteamonKey(steamonKey);
        em.persist(loginUserModel.getUser());
        em.persist(steamonKey);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<SteamonKey> getAllSteamonKeys() {
        List<SteamonKey> steamonKeys =  em.createQuery("SELECT u FROM SteamonKey AS u", SteamonKey.class).getResultList();
        return steamonKeys;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public SteamonKey getSteamonKeyById(Long id) {
        Query query = em.createQuery("SELECT b FROM SteamonKey AS b WHERE b.id = ?1", SteamonKey.class);
        query.setParameter(1, id);
        try {
            return (SteamonKey) query.getSingleResult();
        }
        catch(Exception e) {
            logger.info("getSteamonKeyById: No element found!");
            return null;
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteSteamonKeyById(Long id) {
        SteamonKey steamonKey = getSteamonKeyById(id);
        logger.info("deleteSteamonKey :: Delete steamonKey");
        em.remove(steamonKey);
        logger.info("deleteSteamonKey :: SteamonKey successfully deleted");
    }
}
