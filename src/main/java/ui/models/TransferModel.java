package ui.models;

import entity.BankAccount;
import entity.Transaction;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;
import service.BankAccountService;
import service.TransactionService;

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
public class TransferModel {

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
    private String receiver;

    @Transactional(Transactional.TxType.REQUIRED)
    public String makeTransaction() {
        try {
            BankAccount payee = bankAccountService.getBankAccountByIban(selectedBankAccount.getIban());
            BankAccount payer = bankAccountService.getBankAccountByIban(iban);
            Transaction transaction = new Transaction(payee, payer, amount,
                    TransactionStatus.NEW, TransactionType.TRANSFER, reasonOfUsage,
                    Duration.ONCE, new Date());
            transactionService.transfer(transaction);
        } catch(Exception e) {
            String message = "Überweisung fehlgeschlagen!: " + (e.getMessage() != null ? e.getMessage() : "Nicht alle Felder ausgefüllt!");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            return "transaction";
        }
        String message = "Überweisung geklappt!";
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
            allBankAccounts = bankAccountService.getApprovedBankAccountsOfUser(loginUserModel.getUser().getId());
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
