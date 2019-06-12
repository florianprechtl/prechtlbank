package ui.models;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CreateAccountModel  implements Serializable {

    public String createBankAccount() {
        return "index";
    }

    public String createFirstbankAccount() {
        return "index";
    }
}
