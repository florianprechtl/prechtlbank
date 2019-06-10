package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class SteamonKey extends GeneratedIdEntity implements Serializable {

    private String keyCode;

    @OneToOne(fetch = FetchType.EAGER)
    private User keyReceiver;


    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public User getKeyReceiver() {
        return keyReceiver;
    }

    public void setKeyReceiver(User keyReceiver) {
        this.keyReceiver = keyReceiver;
    }

    @Override
    public String toString() {
        return "SteamonKey{" +
                "keyCode='" + keyCode + '\'' +
                ", keyReceiver=" + keyReceiver +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SteamonKey that = (SteamonKey) o;
        return Objects.equals(getKeyCode(), that.getKeyCode()) &&
                Objects.equals(getKeyReceiver(), that.getKeyReceiver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKeyCode(), getKeyReceiver());
    }

}
