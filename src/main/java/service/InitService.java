package service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import entity.User;
import entity.Address;
import org.apache.log4j.Logger;

@Startup
@Singleton
@ApplicationScoped
public class InitService {

    @Inject
    private transient Logger logger;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        logger.info("initService :: PostConstruct!");
        try {
            Address address = new Address("Marktplatz 35", "92249", "Vilseck", "Germany");
            User newUser = new User("Florian", "Prechtl", User.UserType.CONSULTANT, "testPW", address);
            userService.registerUser(newUser);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        }
    }

    @PreDestroy
    public void removeAll() {
        logger.info("test :: PreDestroy!");
    }
}
