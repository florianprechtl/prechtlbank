package ui.models;

import entity.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginUserModel implements Serializable {

    private String error;
    private String username;
    private String password;
    private User user;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isLoggedIn() {
        return user != null;
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
