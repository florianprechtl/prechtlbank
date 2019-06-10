package entity;

import entity.util.EntityUtils;
import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class User extends GeneratedIdEntity implements Serializable {

    public enum UserType {
        ADMIN,
        CUSTOMER,
        CONSULTANT
    }

    private String firstname;
    private String lastname;
    private UserType userType;

    private String loginId;
    private String password;

    private Address address;

    public User() {
    }

    public User(String firstname, String lastname, UserType userType, String password, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.userType = userType;
        // Create random loginId
        this.loginId = "" + firstname.substring(0,1).toUpperCase() + lastname.substring(0,2).toUpperCase() + EntityUtils.createRandomString(2);
        this.password = password;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getFirstname(), user.getFirstname()) &&
                Objects.equals(getLastname(), user.getLastname()) &&
                getUserType() == user.getUserType() &&
                Objects.equals(getLoginId(), user.getLoginId()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getAddress(), user.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstname(), getLastname(), getUserType(), getLoginId(), getPassword(), getAddress());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", userType=" + userType +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", id=" + id +
                '}';
    }
}
