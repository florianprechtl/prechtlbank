package ui.models;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class RedirectModel implements Serializable {
    private boolean redirected = false;

    @Inject
    private LoginUserModel loginUserModel;

    public void doRedirect(String url) {
        if (!redirected) {
            redirected = true;
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context, null, url + "?faces-redirect=true");
        }
    }

    public void onLoadTestUserType(String userType) {
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
        switch (userType) {
            case "admin":
                if (!loginUserModel.isAdmin()) {
                    navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
                }
                return;
            case "customer":
                if (!loginUserModel.isCustomer()) {
                    navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
                }
                return;
            case "consultant":
                if (!loginUserModel.isConsultant()) {
                    navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
                }
                return;
            case "notLoggedIn":
                if (loginUserModel.isLoggedIn()) {
                    navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
                }
                return;
        }

    }
}
