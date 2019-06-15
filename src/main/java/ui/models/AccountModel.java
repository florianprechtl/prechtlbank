package ui.models;

import entity.BankAccount;
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
public class AccountModel {

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
            allBankAccounts = bankAccountService.getAllBankAccounts();
            selectedBankAccount = null;
        }
    }

    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
    }

    public BankAccount getSelectedBankAccount() {
        return selectedBankAccount;
    }

    public List<BankAccount> getAllBankAccounts() {
        return allBankAccounts;
    }

    public void setAllBankAccounts(List<BankAccount> allBankAccounts) {
        this.allBankAccounts = allBankAccounts;
    }
}
