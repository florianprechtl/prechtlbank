package ui.models;

import entity.LoginAccount;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginAccountModel implements Serializable {

    private String error;
    private String username;
    private String password;
    private LoginAccount loginAccount;
    private String sessionId;


    public String doLogin() {
        return "index";
    }

    public String doLogout() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginAccount getLoginAccount() {
        return loginAccount;
    }

    public void setUser(LoginAccount user) {
        this.loginAccount = user;
    }

    public Boolean isLoggedIn() {
        return loginAccount != null;
    }

    public Boolean isAdmin() {
        return true;
    }

    public Boolean isEmployee() {
        return false;
    }

    public String getSessionId() {
        return sessionId;
    }
}
