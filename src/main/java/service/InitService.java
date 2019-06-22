package service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import entity.BankAccount;
import entity.BankInstitute;
import entity.User;
import entity.Address;
import entity.enums.BankAccountStatus;
import entity.enums.UserType;
import org.apache.log4j.Logger;

@Startup
@Singleton
@ApplicationScoped
public class InitService {

    @Inject
    private transient Logger logger;

    @Inject
    private UserService userService;

    @Inject
    private BankInstituteService bankInstituteService;

    @Inject
    private BankAccountService bankAccountService;

    @PostConstruct
    public void init() {
        logger.info("initService :: PostConstruct!");
        try {
            /////////////////////////////////////////// USER REGISTRATION //////////////////////////////////////////////
            Address address = new Address("Marktplatz 35", "92249", "Vilseck", "Germany");
            User newUser = new User("Florian", "Prechtl", "testID", UserType.CONSULTANT, "testPW", address);
            userService.registerUser(newUser);

            address = new Address("Marktplatz 35", "92249", "Vilseck", "Germany");
            newUser = new User("Florian", "Prechtl", "testID2", UserType.CUSTOMER, "testPW", address);
            userService.registerUser(newUser);

            address = new Address("Marktplatz 35", "92249", "Vilseck", "Germany");
            newUser = new User("Florian", "Prechtl", "testID3", UserType.ADMIN, "testPW", address);
            userService.registerUser(newUser);

            address = new Address("BANK", "00000", "BANK", "BANK");
            newUser = new User("BANK", "BANK", "BANK", UserType.ADMIN, "BANK", address);
            userService.registerUser(newUser);

            ////////////////////////////////////////////// BANK INSTITUTE //////////////////////////////////////////////
            BankInstitute bankInstitute = new BankInstitute("Regensburg PB", "RPGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstitute);

            bankInstitute = new BankInstitute("Amberg PB", "AMGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstitute);

            bankInstitute = new BankInstitute("Ingolstadt PB", "INGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstitute);

            bankInstitute = new BankInstitute("BANK", "BANK");
            bankInstituteService.createBankInstitute(bankInstitute);

            ////////////////////////////////////////////// BANK ACCOUNT ////////////////////////////////////////////////

            BankAccount bankAccount = new BankAccount("BANK", BankAccountStatus.APPROVED, bankInstitute, newUser);
            bankAccountService.createBankAccount(bankAccount);

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
