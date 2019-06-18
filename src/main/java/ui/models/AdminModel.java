package ui.models;

import entity.User;
import service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@RequestScoped
public class AdminModel {

    @Inject
    UserService userService;

    private int tabViewIndex = 0;
    private User tmpUser = new User();

    ///////////////////////////////////////////////// EVENTS ///////////////////////////////////////////////////////////

    public void onUserSelect(User user)
    {
        tabViewIndex = 0;
        tmpUser = user;
    }

    ///////////////////////////////////////////////// ACTIONS //////////////////////////////////////////////////////////

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void doUpsertUser()
    {
        tabViewIndex = 0;
        User user = userService.getUserByLoginId(tmpUser.getLoginId());

        try {
            if (user != null) {
                userService.updateUser(tmpUser);
            } else {
                userService.registerUser(tmpUser);
            }
        } catch (Exception e) {
            String error = "Failed to update/insert user!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }

        tmpUser = new User();
    }

    public void doDeleteUser(User user) {
        tabViewIndex = 0;

        try {
            userService.deleteUser(user.getLoginId());
        } catch(Exception e) {
            String error = "Failed to delete user!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
        }
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public int getTabViewIndex() {
        return tabViewIndex;
    }

    public void setTabViewIndex(int tabViewIndex) {
        this.tabViewIndex = tabViewIndex;
    }

    public User getTmpUser() {
        return tmpUser;
    }

    public void setTmpUser(User tmpUser) {
        this.tmpUser = tmpUser;
    }
}
