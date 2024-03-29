package ui.models;

import de.Steamon.AccountException_Exception;
import de.Steamon.DefaultSteamonService;
import de.Steamon.Software;
import entity.BankAccount;
import entity.BankInstitute;
import entity.enums.BankAccountStatus;
import service.BankAccountService;
import service.TransactionService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
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
    private DefaultSteamonService steamonService;

    private Software selectedSoftware;
    private BankInstitute selectedBankInstitute;

    @Transactional(Transactional.TxType.REQUIRED)
    public String createBankAccount() {
        try {
            String iban = generateIBAN();
            BankAccount bankAccount = new BankAccount(iban, BankAccountStatus.NEW, selectedBankInstitute, loginUserModel.getUser());

            if (isFirstBankAccount()) {
                bankAccountService.createFirstBankAccount(bankAccount, selectedSoftware);
            } else {
                bankAccountService.createBankAccount(bankAccount);
            }
            // Give initial money
            transactionService.giveMoneyToIban(1000.00, bankAccount.getIban());
        } catch(AccountException_Exception e) {
            String error = "SteamonKey konnte nicht gekauft werden:" + e.getMessage() + "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, error, error));
            return "create-bank-account";
        } catch(Exception e) {
            String error = "Fehler beim Erstellen des Bankaccounts:" + e.getMessage() + "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, error, error));
            return "create-bank-account";
        }
        String message = "Neuen Bank Account erstellt. Aber dein Account muss zuerst noch von einem Mitarbeiter bestätigt werden, bevor du etwas überweisen kannst.";
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

    public BankInstitute getSelectedBankInstitute() {
        return selectedBankInstitute;
    }

    public void setSelectedBankInstitute(BankInstitute selectedBankInstitute) {
        this.selectedBankInstitute = selectedBankInstitute;
    }

    public Software getSelectedSoftware() {
        return selectedSoftware;
    }

    public void setSelectedSoftware(Software selectedSoftware) {
        this.selectedSoftware = selectedSoftware;
    }

    public boolean isFirstBankAccount() {
        return bankAccountService.getBankAccountsOfUser(loginUserModel.getUser().getId()).size() == 0;
    }

    private String generateIBAN() {
        StringBuffer iban = new StringBuffer("DE");
        for(int i = 0; i < 20; i++) {
            iban.append((int)(Math.random() * 10));
        }
        return iban.toString();
    }

    public List<Software> getSteamonChoices() {
        try {
            return steamonService.getSoftwareChoicesForFloBank();
        } catch (Exception e) {
            List<Software> softwareList = new ArrayList<>();

            Software software = new Software();
            software.setTitle("Die MIMS 4: Zerstöre virtuelle Leben");
            softwareList.add(software);

            software = new Software();
            software.setTitle("Rätselfield 4: Die Wunder der Levolution");
            softwareList.add(software);

            software = new Software();
            software.setTitle("FIFA 75");
            softwareList.add(software);

            return softwareList;
        }
    }
}
