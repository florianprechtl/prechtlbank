package ui.models;

import entity.Address;
import entity.User;
import entity.util.EntityUtils;
import service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterUserModel {

    @Inject
    UserService userService;

    @Inject
    LoginUserModel loginUserModel;

    private String firstname;
    private String lastname;
    private String password;
    private String passwordRepeat;
    private String street;
    private String zip;
    private String city;
    private String country;
    private String error;

    public String doRegister() {
        try {
            if (password == null || passwordRepeat == null || !password.equals(passwordRepeat)) {
                throw new Exception("Passwords do not match!");
            }

            // Create random loginId
            String loginId = firstname.substring(0,1).toUpperCase() + lastname.substring(0,2).toUpperCase() + EntityUtils.createRandomString(2);
            Address address = new Address(street, zip, city, country);
            User user = new User(firstname, lastname, loginId, User.UserType.CUSTOMER, password, address);
            userService.registerUser(user);
            loginUserModel.setLoginId(loginId);
            loginUserModel.setPassword(password);
            loginUserModel.doLogin();
            String message = "Registration as " + loginId + " successful!";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));

        } catch (Exception e) {
            error = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, error, error));
            return "signup";
        }
        return "index";
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
