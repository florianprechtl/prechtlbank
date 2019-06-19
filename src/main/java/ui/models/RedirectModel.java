package ui.models;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class RedirectModel implements Serializable {
    private boolean redirected = false;

    public void doRedirect(String url) {
        if (!redirected) {
            redirected = true;
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context, null, url + "?faces-redirect=true");
        }
    }
}
