package ui.models;

import entity.*;
import entity.enums.*;
import org.apache.log4j.Logger;
import service.BankAccountService;
import service.BankInstituteService;
import service.TransactionService;
import service.UserService;

import javax.enterprise.context.RequestScoped;
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

    //////////////////////////////////////////////// VARIABLES /////////////////////////////////////////////////////////

    private int tabViewIndex = 0;

    ////////////////////////////////////////////// TMP VARIABLES ///////////////////////////////////////////////////////

    private User tmpUser = new User();
    private Transaction tmpTransaction = new Transaction();
    private BankAccount tmpBankAccount = new BankAccount();
    private BankInstitute tmpBankInstitute = new BankInstitute();

    ///////////////////////////////////////////////// EVENTS ///////////////////////////////////////////////////////////

    public void onUserSelect(User user) {
        tabViewIndex = 0;
        tmpUser = user;
    }

    public void onTransactionSelect(Transaction transaction) {
        tabViewIndex = 1;
        tmpTransaction = transaction;
    }

    public void onBankAccountSelect(BankAccount bankAccount) {
        tabViewIndex = 2;
        tmpBankAccount = bankAccount;
    }

    public void onBankInstituteSelect(BankInstitute bankInstitute) {
        tabViewIndex = 3;
        tmpBankInstitute = bankInstitute;
    }

    ///////////////////////////////////////////////// ACTIONS //////////////////////////////////////////////////////////

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertUser() {
        tabViewIndex = 0;
        User user = userService.getUserById(tmpUser.getId());
        try {
            if (user != null) {
                userService.updateUser(tmpUser);
            } else {
                userService.registerUser(tmpUser);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert user: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }

        tmpUser = new User();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteUser(User user) {
        tabViewIndex = 0;

        try {
            userService.deleteUserById(user.getId());
        } catch(Exception e) {
            String error = "Failed to delete user!: " +  e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertTransaction() {
        tabViewIndex = 1;
        try {
            Transaction transaction = transactionService.getTransactionById(tmpTransaction.getId());
            BankAccount payee = bankAccountService.getBankAccountByIban(tmpTransaction.getPayee().getIban());
            BankAccount payer = bankAccountService.getBankAccountByIban(tmpTransaction.getPayer().getIban());
            tmpTransaction.setPayee(payee);
            tmpTransaction.setPayer(payer);
            if (transaction != null) {
                transactionService.updateTransaction(tmpTransaction);
            } else {
                tmpTransaction.setDate(new Date());
                transactionService.transfer(tmpTransaction);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert transaction: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }

        tmpUser = new User();
    }

    public void clearTransactionForm() {
        tmpTransaction = new Transaction();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteTransaction(Transaction transaction) {
        tabViewIndex = 1;

        try {
            transactionService.deleteTransactionById(transaction.getId());
        } catch(Exception e) {
            String error = "Failed to delete transaction!: " +  e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertBankAccount() {
        tabViewIndex = 2;
        BankAccount bankAccount = bankAccountService.getBankAccountById(tmpBankAccount.getId());
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

        tmpUser = new User();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteBankAccount(BankAccount bankAccount) {
        tabViewIndex = 2;

        try {
            bankAccountService.deleteBankAccountById(bankAccount.getId());
        } catch(Exception e) {
            String error = "Failed to delete bankAccount" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertBankInstitute() {
        tabViewIndex = 3;
        BankInstitute bankInstitute = bankInstituteService.getBankInstituteById(tmpBankInstitute.getId());
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

        tmpUser = new User();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doDeleteBankInstitute(BankInstitute bankInstitute) {
        tabViewIndex = 3;

        try {
            bankInstituteService.deleteBankInstituteById(bankInstitute.getId());
        } catch(Exception e) {
            String error = "Failed to delete bankAccount" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    //////////////////////////////////////////////// GETTER & SETTER ///////////////////////////////////////////////////

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }

    public List<BankInstitute> getAllBankInstitutes() {
        return bankInstituteService.getAllBankInstitutes();
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
}
