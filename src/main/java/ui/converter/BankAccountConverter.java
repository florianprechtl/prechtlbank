package ui.converter;

import entity.BankAccount;
import service.BankAccountService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

@ApplicationScoped
public class BankAccountConverter implements Converter {

    @Inject
    private BankAccountService bankAccountService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null) {
            return "";
        }
        //Get IBAN from String representation
        String iban = s.split(" ")[0];
        BankAccount bankAccount = bankAccountService.getBankAccountByIban(iban);
        return bankAccount;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return null;
        }
        if (!o.getClass().equals(BankAccount.class)) {
            return null;
        }

        return o.toString();
    }
}
