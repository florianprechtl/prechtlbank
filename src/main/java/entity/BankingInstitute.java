package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class BankingInstitute extends GeneratedIdEntity implements Serializable {
    private String name;
    private String bic;

    public BankingInstitute() {

    }

    public BankingInstitute(String name, String iban) {
        this.name = name;
        this.bic = bic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankingInstitute that = (BankingInstitute) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getBic(), that.getBic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getBic());
    }

    @Override
    public String toString() {
        return "BankingInstitute{" +
                "name='" + name + '\'' +
                ", bic='" + bic + '\'' +
                ", id=" + id +
                '}';
    }
}
