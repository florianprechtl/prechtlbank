package ui.models;

import entity.BankAccount;
import entity.Transaction;
import entity.User;
import entity.enums.BankAccountStatus;
import entity.enums.TransactionStatus;
import entity.repo.BankAccountRepo;
import entity.repo.TransactionRepo;
import entity.repo.UserRepo;
import service.BankAccountService;
import service.Exceptions.TransactionException;
import service.Exceptions.ValidationException;
import service.TransactionService;
import service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ConsultantModel  implements Serializable {

    //////////////////////////////////////////////// SERVICES //////////////////////////////////////////////////////////

    @Inject
    private UserService userService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BankAccountService bankAccountService;

    ////////////////////////////////////////////////// REPOS ///////////////////////////////////////////////////////////

    @Inject
    private UserRepo userRepo;

    @Inject
    private TransactionRepo transactionRepo;

    @Inject
    private BankAccountRepo bankAccountRepo;

    //////////////////////////////////////////////// VARIABLES /////////////////////////////////////////////////////////

    private int tabViewIndex = 0;

    //////////////////////////////////////////////// FUNCTIONS /////////////////////////////////////////////////////////

    public void doDeleteUser(User user) {
        tabViewIndex = 0;
        userService.deleteUserById(user.getId());
    }

    public void doDeleteTransaction(Transaction transaction) {
        tabViewIndex = 1;
        transactionService.deleteTransactionById(transaction.getId());
    }

    public void doDeleteBankAccount(BankAccount bankAccount) {
        tabViewIndex = 2;
        bankAccountService.deleteBankAccountById(bankAccount.getId());
    }

    public void doUpdateTransactionStatus(Transaction transaction, String status) {
        tabViewIndex = 1;
        TransactionStatus transactionStatus = getTransactionStatusByStringStatus(status);
        transaction.setTransactionStatus(transactionStatus);
        try {
            transactionService.updateTransaction(transaction);
        } catch (ValidationException e) {
            String error = "Failed to update Transaction status" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    public void doUpdateBankAccountStatus(BankAccount bankAccount, String status) {
        tabViewIndex = 2;
        BankAccountStatus bankAccountStatus = getBankAccountStatusByStringStatus(status);
        bankAccount.setAccountStatus(bankAccountStatus);
        try {
            bankAccountService.updateBankAccount(bankAccount);
        } catch (ValidationException e) {
            String error = "Failed to update bankAccount staus" + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    public boolean hasTransactionStatus(TransactionStatus transactionStatus, String status) {
        if (transactionStatus == null) {
            return false;
        }
        return transactionStatus.equals(getTransactionStatusByStringStatus(status));
    }

    public TransactionStatus getTransactionStatusByStringStatus(String status) {
        switch(status) {
            case "DONE":
                return TransactionStatus.DONE;
            case "PENDING":
                return TransactionStatus.PENDING;
            default:
                return TransactionStatus.NEW;
        }
    }

    public boolean hasBankAccountStatus(BankAccountStatus bankAccountStatus, String status) {
        if (bankAccountStatus == null) {
            return false;
        }
        return bankAccountStatus.equals(getBankAccountStatusByStringStatus(status));
    }

    public BankAccountStatus getBankAccountStatusByStringStatus(String status) {
        switch(status) {
            case "APPROVED":
                return BankAccountStatus.APPROVED;
            case "PENDING":
                return BankAccountStatus.PENDING;
            case "FROZEN":
                return BankAccountStatus.FROZEN;
            default:
                return BankAccountStatus.NEW;
        }
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

    public int getTabViewIndex() {
        return tabViewIndex;
    }

    public void setTabViewIndex(int tabViewIndex) {
        this.tabViewIndex = tabViewIndex;
    }
}