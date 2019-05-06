package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class LoginAccount extends GeneratedIdEntity implements Serializable {

    private String loginId;
    private String password;

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
        if (!(o instanceof LoginAccount)) return false;
        if (!super.equals(o)) return false;
        LoginAccount that = (LoginAccount) o;
        return Objects.equals(getLoginId(), that.getLoginId()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getLoginId(), getPassword());
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
