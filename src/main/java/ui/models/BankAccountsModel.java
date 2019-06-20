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
@SessionScoped
public class BankAccountsModel implements Serializable {

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

    public List<Transaction> getTransactions() {
        if (selectedBankAccount != null) {
            List<Transaction> transactions = transactionService.getAllTransactionsByIban(selectedBankAccount.getIban());
            Transaction transaction = new Transaction();
            transaction.setAmount(20000);
            transactions.add(transaction);
            return transactions;
        }
        return null;

    }

    public String getTransactionPartner(Transaction transaction) {
        if (selectedBankAccount != null && transaction != null) {
            if (transaction.getPayeeIban() != null) {
                if (transaction.getPayeeIban().equals(selectedBankAccount.getIban())) {
                    return transaction.getPayerIban();
                } else {
                    return transaction.getPayeeIban();
                }
            }
        }
        return "Kontostand:";
    }

    public String getTransactionAmount(Transaction transaction) {
        if (selectedBankAccount != null && transaction != null) {
            if (transaction.getPayeeIban() != null) {
                if (transaction.getPayeeIban().equals(selectedBankAccount.getIban())) {
                    return "+ " + transaction.getAmount();
                } else {
                    return "- " + transaction.getAmount();
                }
            } else {
                return getSumOfAllTransactions(getTransactions());
            }
        }
        return "No money!";
    }

    public String onBankAccountChange() {
        return "bank-accounts";
    }

    public String getSumOfAllTransactions(List<Transaction> transactions) {
        double sum = 0;
        for (int i = 0; i < transactions.size() - 1; i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getPayeeIban().equals(selectedBankAccount.getIban())) {
                sum += transaction.getAmount();
            } else {
                sum -= transaction.getAmount();
            }
        }
        return sum < 0
                ? "- " + sum
                : "+ " + sum;
    }
}
