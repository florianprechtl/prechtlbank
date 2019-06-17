package ui.models;

import entity.BankAccount;
import entity.BankInstitute;
import service.BankAccountService;
import service.BankInstituteService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

@Named
@RequestScoped
public class CreateBankAccountModel implements Serializable {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private BankInstituteService bankInstituteService;

    private String selectedSteamonKey;


    @Transactional(Transactional.TxType.REQUIRED)
    public String createBankAccount() {
        try {
            // TODO: Bankinstitut setzen
            BankInstitute bankInstitute = new BankInstitute("Regensburg PB", "RGB1FSZ7");
            bankInstituteService.createBankInstitute(bankInstitute);
            String iban = generateIBAN();
            BankAccount bankAccount = new BankAccount(iban, BankAccount.AccountStatus.NEW, bankInstitute, loginUserModel.getUser());
            bankAccountService.createBankAccount(bankAccount);
        } catch(Exception e) {
            String error = "Fehler beim Erstellen des Bankaccounts:" + e.getMessage() + "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, error, error));
            return createFirstBankAccount();
        }
        String error = "Neuen Bank Account erstellt.";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, error, error));
        return "bank-accounts";
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String createFirstBankAccount() {
        if (!loginUserModel.isLoggedIn()) {
            String message = "Um ein Bankkonto zu eröffnen, musst du dich zuerst einloggen oder ein neues Profil registrieren.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "signup";
        }
        // TODO: check if first account
        if (bankAccountService.getBankAccountsOfUser(loginUserModel.getUser().getId()).size() > 0) {
            String message = "Du hast bereits einen Bank Account. Deshalb wirst du für ein neues Konto keinen Steam Key erhalten.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "bank-accounts";
        }
        return "create-bank-account";
    }

    public void test() {
        bankAccountService.logAllBankAccounts();
    }

    public String getSelectedSteamonKey() {
        return selectedSteamonKey;
    }

    public void setSelectedSteamonKey(String selectedSteamonKey) {
        this.selectedSteamonKey = selectedSteamonKey;
    }

    public String generateIBAN() {
        String iban = "DE";
        for(int i = 0; i < 20; i++) {
            iban += (int)(Math.random() * 10);
        }
        return iban;
    }
}
