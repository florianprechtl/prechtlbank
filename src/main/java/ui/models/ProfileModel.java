package ui.models;

import entity.User;
import entity.repo.UserRepo;
import org.apache.log4j.Logger;
import service.Exceptions.ValidationException;
import service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class ProfileModel implements Serializable {

    @Inject
    private LoginUserModel loginUserModel;

    @Inject
    private UserService userService;

    @Inject
    private UserRepo userRepo;

    private boolean editMode = false;
    private boolean editPassword = false;

    private User tmpUser;

    private String oldPassword;
    private String newPassword;
    private String newPasswordRepeat;

    public void onLoad() {
        if (!loginUserModel.isLoggedIn()) {
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context, null, "index.html?faces-redirect=true");
        } else {
            tmpUser = userRepo.getById(loginUserModel.getUser().getId());
        }
    }

    public void showSuccessMessage() {
        if (tmpUser.getSteamonKey() != null) {
            String message = "SteamonKey " + tmpUser.getSteamonKey().getKeyCode() + " erfolgreich in Ablage gesichert!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        } else {
            String message = "Du hast leider keinen SteamonKey!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
        }
    }

    public String updateUserInformation() {
        try {
            userService.updateUser(tmpUser);
            loginUserModel.setUser(userRepo.getById(tmpUser.getId()));
            editMode = false;
            String message = "Bearbeitete Nutzerdaten erfogreich abgespeichert!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        } catch (ValidationException e) {
            String message = "Neues Password setzen war erfolgreich: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        }
        return "profile";
    }

    public String updateUserPassword() {
        try {
            if (!oldPassword.equals(tmpUser.getPassword()))
                throw new ValidationException("Das alte Passwort scheint falsch zu sein!", null);
            if (!newPassword.equals(newPasswordRepeat))
                throw new ValidationException("DIe Passwörter stimmen nicht überein!", null);

            tmpUser.setPassword(newPassword);
            userService.updateUser(tmpUser);
            loginUserModel.setUser(userRepo.getById(tmpUser.getId()));
            editPassword = false;
            String message = "Neues Password setzen war erfolgreich!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
        } catch (ValidationException e) {
            String message = "Fehler beim Setzen des neuen Passworts: " + e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        }
        return "profile";
    }

    public void resetUser() {
        tmpUser = userRepo.getById(loginUserModel.getUser().getId());
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public User getTmpUser() {
        return tmpUser;
    }

    public void setTmpUser(User tmpUser) {
        this.tmpUser = tmpUser;
    }

    public boolean isEditPassword() {
        return editPassword;
    }

    public void setEditPassword(boolean editPassword) {
        this.editPassword = editPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }
}
