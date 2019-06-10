package ui.models;

import entity.User;
import entity.dto.LoginDTO;
import org.apache.log4j.Logger;
import service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginUserModel implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private transient Logger logger;

    private String error;
    private String loginId;
    private String password;
    private User user;


    public String doLogin() {
        error = null;

        try {
            user = userService.loginUser(new LoginDTO(loginId, password));
            loginId = "";
            password = "";

        } catch(UserService.InvalidCredentialsException e) {
            error = e.getMessage();
            logger.info(e.getMessage());
        }
        return "index";
    }

    public String doLogout() {
        user = null;
        loginId = "";
        password = "";
        return "index";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean hasError() {
        return error != null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLoggedIn() {
        return user != null;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
