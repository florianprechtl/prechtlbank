package ui.models;

import entity.BankAccount;
import entity.Transaction;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;
import org.apache.log4j.Logger;
import service.BankAccountService;
import service.TransactionService;
import service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@RequestScoped
public class TransactionModel {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private UserService userService;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    TransactionService transactionService;

    @Inject
    private Logger logger;

    private BankAccount selectedBankAccount;
    private List<BankAccount> allBankAccounts;

    private String iban;
    private String bic;
    private Double amount;
    private String reasonOfUsage;
    private String receiver;

    @Transactional(Transactional.TxType.REQUIRED)
    public String makeTransaction() {
        try {
            String payeeIban = iban;
            String payerIban = selectedBankAccount.getIban();
            String payeeBic = bic;
            String payerBic = selectedBankAccount.getBankInstitute().getBic();
            Transaction transaction = new Transaction(payeeIban, payerIban, amount, payeeBic, payerBic,
                    TransactionStatus.NEW, TransactionType.TRANSFER, reasonOfUsage,
                    Duration.ONCE);
            transactionService.transfer(transaction);
        } catch(Exception e) {
            logger.info(e.getMessage());
            String message = "Überweisung fehlgeschlagen!: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            return "bank-accounts";
        }
        String message = "Überweisung geklappt!";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        return "bank-accounts";
    }

    public boolean isBankAccountSelected() {
        return this.selectedBankAccount != null;
    }

    public BankAccount getSelectedBankAccount() {
        return selectedBankAccount;
    }

    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReasonOfUsage() {
        return reasonOfUsage;
    }

    public void setReasonOfUsage(String reasonOfUsage) {
        this.reasonOfUsage = reasonOfUsage;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
