package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class BankInstitute extends GeneratedIdEntity implements Serializable {
    private String name;
    private String bic;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bankInstitute", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<BankAccount> bankAccounts;

    public BankInstitute() {

    }

    public BankInstitute(String name, String bic) {
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

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankInstitute)) return false;
        if (!super.equals(o)) return false;
        BankInstitute that = (BankInstitute) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getBic(), that.getBic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getBic());
    }

    @Override
    public String toString() {
        return "BankInstitute{" +
                "name='" + name + '\'' +
                ", bic='" + bic + '\'' +
                ", id=" + id +
                '}';
    }
}
