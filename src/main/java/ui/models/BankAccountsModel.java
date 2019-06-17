package ui.models;

import entity.BankAccount;
import entity.Transaction;
import service.BankAccountService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class BankAccountsModel {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private BankAccountService bankAccountService;

    private BankAccount selectedBankAccount;
    private List<BankAccount> allBankAccounts;

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
        // TODO:
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
}
