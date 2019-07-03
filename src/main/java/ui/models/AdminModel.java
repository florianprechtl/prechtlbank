package ui.models;

import entity.*;
import entity.enums.*;
import entity.repo.*;
import service.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class AdminModel implements Serializable {

    //////////////////////////////////////////////// SERVICES //////////////////////////////////////////////////////////

    @Inject
    private UserService userService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private BankInstituteService bankInstituteService;

    @Inject
    private SteamonKeyService steamonKeyService;

    ////////////////////////////////////////////////// REPOS ///////////////////////////////////////////////////////////

    @Inject
    private UserRepo userRepo;

    @Inject
    private TransactionRepo transactionRepo;

    @Inject
    private BankAccountRepo bankAccountRepo;

    @Inject
    private BankInstituteRepo bankInstituteRepo;

    @Inject
    private SteamonKeyRepo steamonKeyRepo;

    //////////////////////////////////////////////// VARIABLES /////////////////////////////////////////////////////////

    private int tabViewIndex = 0;

    ////////////////////////////////////////////// TMP VARIABLES ///////////////////////////////////////////////////////

    private User tmpUser = new User();
    private Transaction tmpTransaction = new Transaction();
    private BankAccount tmpBankAccount = new BankAccount();
    private BankInstitute tmpBankInstitute = new BankInstitute();
    private SteamonKey tmpSteamonKey = new SteamonKey();

    ///////////////////////////////////////////////// EVENTS ///////////////////////////////////////////////////////////

    public void onUserSelect(User user) {
        tabViewIndex = 0;
        tmpUser = user;
    }

    public void onTransactionSelect(Transaction transaction) {
        tabViewIndex = 1;
        tmpTransaction = transaction;
    }

    public void onSteamonKeySelect(SteamonKey steamonKey) {
        tabViewIndex = 2;
        tmpSteamonKey = steamonKey;
    }

    public void onBankAccountSelect(BankAccount bankAccount) {
        tabViewIndex = 3;
        tmpBankAccount = bankAccount;
    }

    public void onBankInstituteSelect(BankInstitute bankInstitute) {
        tabViewIndex = 4;
        tmpBankInstitute = bankInstitute;
    }


    ///////////////////////////////////////////////// ACTIONS //////////////////////////////////////////////////////////

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertUser() {
        User user = null;
        tabViewIndex = 0;
        if (tmpUser.getId() != null) {
            user = userRepo.getById(tmpUser.getId());
        }
        try {
            if (user != null) {
                tmpUser.setSteamonKey(null);
                userService.updateUser(tmpUser);
            } else {
                userService.registerUser(tmpUser);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert user: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteUser(User user) {
        tabViewIndex = 0;

        try {
            userService.deleteUserById(user.getId());
            clearAllForms();
        } catch(Exception e) {
            String error = "Failed to delete user!: " +  e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteSteamonKey(SteamonKey steamonKey) {
        tabViewIndex = 1;
        try {
            steamonKeyService.deleteSteamonKeyById(steamonKey.getId());
            clearAllForms();
        } catch(Exception e) {
            String error = "Failed to delete steamonKey" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertTransaction() {
        Transaction transaction = null;
        tabViewIndex = 2;
        try {
            if (tmpTransaction.getId() != null) {
                transaction = transactionRepo.getById(tmpTransaction.getId());
            }
            BankAccount payee = bankAccountService.getBankAccountByIban(tmpTransaction.getPayee().getIban());
            BankAccount payer = bankAccountService.getBankAccountByIban(tmpTransaction.getPayer().getIban());
            tmpTransaction.setPayee(payee != null ? payee : new BankAccount());
            tmpTransaction.setPayer(payer != null ? payer : new BankAccount());
            if (transaction != null) {
                transactionService.updateTransaction(tmpTransaction);
            } else {
                Date date = new Date();
                tmpTransaction.setDate(date);
                tmpTransaction.setLastTransactionDate(date);
                transactionService.transfer(tmpTransaction);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert transaction: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteTransaction(Transaction transaction) {
        tabViewIndex = 2;

        try {
            transactionService.deleteTransactionById(transaction.getId());
            clearAllForms();
        } catch(Exception e) {
            String error = "Failed to delete transaction!: " +  e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertBankAccount() {
        BankAccount bankAccount = null;
        tabViewIndex = 3;
        if (tmpBankAccount.getId() != null) {
            bankAccount = bankAccountRepo.getById(tmpBankAccount.getId());
        }
        User user = userService.getUserByLoginId(tmpBankAccount.getUser().getLoginId());
        tmpBankAccount.setUser(user != null ? user : new User());
        try {
            if (bankAccount != null) {
                bankAccountService.updateBankAccount(tmpBankAccount);
            } else {
                bankAccountService.createBankAccount(tmpBankAccount);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert bankAccount: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteBankAccount(BankAccount bankAccount) {
        tabViewIndex = 3;

        try {
            bankAccountService.deleteBankAccountById(bankAccount.getId());
            clearAllForms();
        } catch(Exception e) {
            String error = "Failed to delete bankAccount" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertBankInstitute() {
        BankInstitute bankInstitute = null;
        tabViewIndex = 4;
        if (tmpBankInstitute.getId() != null) {
            bankInstitute = bankInstituteRepo.getById(tmpBankInstitute.getId());
        }
        try {
            if (bankInstitute != null) {
                bankInstituteService.updateBankInstitute(tmpBankInstitute);
            } else {
                bankInstituteService.createBankInstitute(tmpBankInstitute);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert bankInstitute: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteBankInstitute(BankInstitute bankInstitute) {
        tabViewIndex = 4;

        try {
            bankInstituteService.deleteBankInstituteById(bankInstitute.getId());
            clearAllForms();
        } catch(Exception e) {
            String error = "Failed to delete bankAccount" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    public void clearUserForm() {
        tmpUser = new User();
    }

    public void clearTransactionForm() {
        tmpTransaction = new Transaction();
    }

    public void clearBankAccountForm() {
        tmpBankAccount = new BankAccount();
    }

    public void clearBankInstituteForm() {
        tmpBankInstitute = new BankInstitute();
    }

    public void clearSteamonKeyForm() {
        tmpSteamonKey = new SteamonKey();
    }

    public void clearAllForms() {
        clearBankAccountForm();
        clearBankInstituteForm();
        clearUserForm();
        clearSteamonKeyForm();
        clearTransactionForm();
    }

    //////////////////////////////////////////////// GETTER & SETTER ///////////////////////////////////////////////////

    public List<User> getAllUsers() {
        return userRepo.getAll();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.getAll();
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepo.getAll();
    }

    public List<BankInstitute> getAllBankInstitutes() {
        return bankInstituteRepo.getAll();
    }

    public List<SteamonKey> getAllSteamonKeys() {
        return steamonKeyRepo.getAll();
    }

    public BankAccountStatus[] getAllBankAccountStatuses() {
        return BankAccountStatus.values();
    }

    public TransactionStatus[] getAllTransactionStatuses() {
        return TransactionStatus.values();
    }

    public TransactionType[] getAllTransactionTypes() {
        return TransactionType.values();
    }

    public Duration[] getAllDurations() {
        return Duration.values();
    }

    public UserType[] getAllUserTypes() {
        return UserType.values();
    }

    public int getTabViewIndex() {
        return tabViewIndex;
    }

    public void setTabViewIndex(int tabViewIndex) {
        this.tabViewIndex = tabViewIndex;
    }

    public User getTmpUser() {
        return tmpUser;
    }

    public void setTmpUser(User tmpUser) {
        this.tmpUser = tmpUser;
    }

    public Transaction getTmpTransaction() {
        return tmpTransaction;
    }

    public void setTmpTransaction(Transaction tmpTransaction) {
        this.tmpTransaction = tmpTransaction;
    }

    public BankAccount getTmpBankAccount() {
        return tmpBankAccount;
    }

    public void setTmpBankAccount(BankAccount tmpBankAccount) {
        this.tmpBankAccount = tmpBankAccount;
    }

    public BankInstitute getTmpBankInstitute() {
        return tmpBankInstitute;
    }

    public void setTmpBankInstitute(BankInstitute tmpBankInstitute) {
        this.tmpBankInstitute = tmpBankInstitute;
    }

    public SteamonKey getTmpSteamonKey() {
        return tmpSteamonKey;
    }

    public void setTmpSteamonKey(SteamonKey tmpSteamonKey) {
        this.tmpSteamonKey = tmpSteamonKey;
    }
}
