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
public class CreateAccountModel  implements Serializable {

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
            BankInstitute bankInstitute = new BankInstitute("Regensburg PB", "GEN0DEFISZ");
            bankInstituteService.createBankInstitute(bankInstitute);
            BankAccount bankAccount = new BankAccount("DE1234123412349", BankAccount.AccountStatus.NEW, bankInstitute);
            bankAccountService.createBankAccount(bankAccount);
        } catch(Exception e) {

            String error = "Fehler beim Erstellen des Bankaccounts:" + e.getMessage() + "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, error, error));
        }
        return "accounts";
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public String createFirstBankAccount() {
        if (!loginUserModel.isLoggedIn()) {
            String message = "Um ein Bankkonto zu eröffnen, musst du dich zuerst einloggen oder ein neues Profil registrier.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "signup";
        }
        // TODO: check if first account
        // TODO: get steamonKey
        createBankAccount();

        return "create-account";
    }

    public void test() {
        bankInstituteService.logAllBankInstituates();
        bankAccountService.logAllBankAccounts();
    }

    public String getSelectedSteamonKey() {
        return selectedSteamonKey;
    }

    public void setSelectedSteamonKey(String selectedSteamonKey) {
        this.selectedSteamonKey = selectedSteamonKey;
    }
}
