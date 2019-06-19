package ui.models;

import entity.BankAccount;
import entity.Transaction;
import org.apache.log4j.Logger;
import service.BankAccountService;
import service.TransactionService;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class BankAccountsModel implements Serializable {

//    @Inject
//    private Logger logger;
    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private TransactionService transactionService;

    private BankAccount selectedBankAccount;
    private List<BankAccount> allBankAccounts;
    private List<Transaction> transactions;

    public void onLoad() {
        if (!loginUserModel.isCustomer()) {
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
        } else {
            allBankAccounts = bankAccountService.getBankAccountsOfUser(loginUserModel.getUser().getId());
            selectedBankAccount = null;
        }
    }

    public String getTransactionPartner(Transaction transaction) {
        // TODO: Always return name/iban of partner of the transaction
        return "hans";
    }

    public List<Transaction> getTransactions() {
        return null;
    }

    public int getTransactionAmount(Transaction transaction) {
        return 0;
    }

    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
    }

    public BankAccount getSelectedBankAccount() {
        return selectedBankAccount;
    }

    public List<BankAccount> getAllBankAccounts() {
        if (allBankAccounts == null) {
            allBankAccounts = bankAccountService.getBankAccountsOfUser(loginUserModel.getUser().getId());
        }
        return allBankAccounts;
    }

    public void setAllBankAccounts(List<BankAccount> allBankAccounts) {
        this.allBankAccounts = allBankAccounts;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions(List<Transaction> transactions) {
        if (selectedBankAccount != null) {
            return transactionService.getAllTransactionsByIban(selectedBankAccount.getIban());
        }
        return null;
    }

    public void onBankAccountChange() {
    }
}
