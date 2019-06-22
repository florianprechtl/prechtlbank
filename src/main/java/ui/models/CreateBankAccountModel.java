package ui.models;

import entity.BankAccount;
import entity.BankInstitute;
import entity.enums.BankAccountStatus;
import service.BankAccountService;
import service.BankInstituteService;
import service.TransactionService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CreateBankAccountModel implements Serializable {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BankInstituteService bankInstituteService;

    private String selectedSteamonKey;
    private BankInstitute selectedBankInstitute;

    @Transactional(Transactional.TxType.REQUIRED)
    public String createBankAccount() {
        try {
            String iban = generateIBAN();
            BankAccount bankAccount = new BankAccount(iban, BankAccountStatus.NEW, selectedBankInstitute, loginUserModel.getUser());

            if (isFirstBankAccount()) {
                bankAccountService.createFirstBankAccount(bankAccount);
            } else {
                bankAccountService.createBankAccount(bankAccount);
            }
            // Give initial money
            transactionService.giveMoneyToIban(1000.00, bankAccount.getIban());
        } catch(Exception e) {
            String error = "Fehler beim Erstellen des Bankaccounts:" + e.getMessage() + "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, error, error));
            return "create-bank-account";
        }
        String message = "Neuen Bank Account erstellt.";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        return "bank-accounts";
    }

    public String navigateToCreateFirstBankAccount() {
        if (!loginUserModel.isLoggedIn()) {
            String message = "Um ein Bankkonto zu eröffnen, musst du dich zuerst einloggen oder ein neues Profil registrieren.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "signup";
        }
        // TODO: check if first account
        if (!isFirstBankAccount()) {
            String message = "Du hast bereits einen Bank Account. Deshalb wirst du für ein neues Konto keinen Steam Key erhalten.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "create-bank-account";
        }
        return "create-bank-account";
    }

    public String getSelectedSteamonKey() {
        return selectedSteamonKey;
    }

    public void setSelectedSteamonKey(String selectedSteamonKey) {
        this.selectedSteamonKey = selectedSteamonKey;
    }

    public BankInstitute getSelectedBankInstitute() {
        return selectedBankInstitute;
    }

    public void setSelectedBankInstitute(BankInstitute selectedBankInstitute) {
        this.selectedBankInstitute = selectedBankInstitute;
    }

    public boolean isFirstBankAccount() {
        return bankAccountService.getBankAccountsOfUser(loginUserModel.getUser().getId()).size() == 0;
    }

    private String generateIBAN() {
        String iban = "DE";
        for(int i = 0; i < 20; i++) {
            iban += (int)(Math.random() * 10);
        }
        return iban;
    }

    public List<BankInstitute> getAllBankInstitutes() {
        return bankInstituteService.getAllBankInstitutes();
    }
}
