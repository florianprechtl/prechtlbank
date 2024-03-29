package service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.*;
import entity.enums.BankAccountStatus;
import entity.enums.UserType;
import entity.repo.*;
import org.apache.log4j.Logger;

import java.util.List;

@Startup
@Singleton
@ApplicationScoped
public class InitService {

    @Inject
    private Logger logger;

    @Inject
    private UserService userService;

    @Inject
    private BankInstituteService bankInstituteService;

    @Inject
    private BankAccountService bankAccountService;

    @PersistenceContext
    private EntityManager em;

    ////////////////////////////////////////////////// REPOS ///////////////////////////////////////////////////////////

    @Inject
    private TransactionRepo transactionRepo;

    @Inject
    private UserRepo userRepo;

    @Inject
    private BankAccountRepo bankAccountRepo;

    @Inject
    private BankInstituteRepo bankInstituteRepo;

    @Inject
    private SteamonKeyRepo steamonKeyRepo;

    //////////////////////////////////////////////// FUNCTIONS /////////////////////////////////////////////////////////

    // @PostConstruct
    public void init() {

        logger.info("init :: PostConstruct!");
        removeAll();
        try {
            /////////////////////////////////////////// USER REGISTRATION //////////////////////////////////////////////
            Address address = new Address("Mozartalee 8", "93562", "Mumflingen", "Deutschland");
            User consultant = new User("Daniel", "Jobst", "consultant", UserType.CONSULTANT, "1234", address);
            userService.registerUser(consultant);

            address = new Address("Kranweg 35", "92249", "Wingersdorf", "Deutschland");
            User customer = new User("Florian", "Prechtl", "customer", UserType.CUSTOMER, "1234", address);
            userService.registerUser(customer);

            address = new Address("Am Markt 4", "98421", "Dunhausen", "Deutschland");
            User admin = new User("Adrian", "Admin", "admin", UserType.ADMIN, "1234", address);
            userService.registerUser(admin);

            address = new Address("Secret Straße 666", "93049", "Regensburg", "Deutschland");
            User meier = new User("Josef", "Meier", "JME261", UserType.CUSTOMER, "abc", address);
            userService.registerUser(meier);

            address = new Address("Irgendne Straße", "93049", "Regensburg", "Deutschland");
            User payPal = new User("HPP", "HofmeisterPayPal", "PayPal23543", UserType.CUSTOMER, "payPalPwd4", address);
            userService.registerUser(payPal);

            // Has to be last insert
            address = new Address("BANK", "00000", "BANK", "BANK");
            User bank = new User("BANK", "BANK", "BANK", UserType.ADMIN, "BANK", address);
            userService.registerUser(bank);

            ////////////////////////////////////////////// BANK INSTITUTE //////////////////////////////////////////////
            BankInstitute bankInstituteRegensburg = new BankInstitute("Regensburg PB", "RPGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstituteRegensburg);

            BankInstitute bankInstitute = new BankInstitute("Amberg PB", "AMGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstitute);

            bankInstitute = new BankInstitute("Ingolstadt PB", "INGE1SZ6");
            bankInstituteService.createBankInstitute(bankInstitute);

            bankInstitute = new BankInstitute("BANK", "BANK");
            bankInstituteService.createBankInstitute(bankInstitute);

            ////////////////////////////////////////////// BANK ACCOUNT ////////////////////////////////////////////////

            BankAccount bankAccount = new BankAccount("BANK", BankAccountStatus.APPROVED, bankInstitute, bank);
            bankAccountService.createBankAccount(bankAccount);

            bankAccount = new BankAccount("DE20614672326156345227", BankAccountStatus.APPROVED, bankInstitute, payPal);
            bankAccountService.createBankAccount(bankAccount);

            bankAccount = new BankAccount("DE62480599303994628351", BankAccountStatus.APPROVED, bankInstituteRegensburg, meier);
            bankAccountService.createBankAccount(bankAccount);

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        }
    }

    // @PreDestroy
    public void removeAll() {
        logger.info("exit :: PreDestroy!");
        List<User> users = userRepo.getAll();
        List<BankAccount> bankAccounts = bankAccountRepo.getAll();
        List<BankInstitute> bankInstitutes = bankInstituteRepo.getAll();
        List<SteamonKey> steamonKeys = steamonKeyRepo.getAll();
        List<Transaction> transactions = transactionRepo.getAll();

        logger.info("exit :: Delete all transactions");
        for (Transaction transaction : transactions)
            em.remove(transaction);

        logger.info("exit :: Delete all steamonKeys");
        for (SteamonKey steamonKey : steamonKeys)
            em.remove(steamonKey);

        logger.info("exit :: Delete all users");
        for (User user : users)
            em.remove(user);

        logger.info("exit :: Delete all bankAccounts");
        for (BankAccount bankAccount : bankAccounts)
            em.remove(bankAccount);

        logger.info("exit :: Delete all bankInstitutes");
        for (BankInstitute bankInstitute : bankInstitutes)
            em.remove(bankInstitute);

    }
}
