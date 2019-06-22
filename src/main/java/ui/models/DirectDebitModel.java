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
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class DirectDebitModel {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    TransactionService transactionService;

    private BankAccount selectedBankAccount;
    private List<BankAccount> allBankAccounts;

    private String iban;
    private String bic;
    private Double amount;
    private String reasonOfUsage;
    private String sender;
    private Duration duration;

    @Transactional(Transactional.TxType.REQUIRED)
    public String makeDirectDebit() {
        try {
            BankAccount payee = bankAccountService.getBankAccountByIban(selectedBankAccount.getIban());
            BankAccount payer = bankAccountService.getBankAccountByIban(iban);
            Transaction transaction = new Transaction(payee, payer, amount,
                    TransactionStatus.NEW, TransactionType.DIRECT_DEBIT, reasonOfUsage,
                    duration, new Date());
            transactionService.directDebit(transaction);
        } catch(Exception e) {
            String message = "Lastschrift fehlgeschlagen!: " + (e.getMessage() != null ? e.getMessage() : "Nicht alle Felder ausgefüllt!");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            return "direct-debit";
        }
        String message = "Lastschrift geklappt!";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        return "bank-accounts";
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

    public Duration[] getAllDurations() {
        return Duration.values();
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
