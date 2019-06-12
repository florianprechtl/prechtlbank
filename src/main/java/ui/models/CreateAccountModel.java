package ui.models;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CreateAccountModel  implements Serializable {

    @Inject
    private LoginUserModel loginUserModel;


    public String createBankAccount() {
        return "index";
    }

    public String createFirstBankAccount() {
        if (!loginUserModel.isLoggedIn()) {
            String message = "Um ein Bankkonto zu eröffnen, musst du dich zuerst einloggen oder ein neues Profil registrier.";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
            return "signup";
        }
        return "create-account";
    }
}
