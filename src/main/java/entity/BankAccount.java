package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class BankAccount extends GeneratedIdEntity implements Serializable {
    public enum AccountStatus {
        NEW,
        PENDING,
        APPROVED,
        DENIED
    }

    private AccountStatus accountStatus;
    private String iban;

    @ManyToOne(fetch = FetchType.EAGER)
    private BankingInstitute bankingInstitute;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Transaction> trasactions;


    public BankAccount() {

    }

    public BankAccount(String iban, AccountStatus accountStatus, BankingInstitute bankingInstitute) {
        this.iban = iban;
        this.accountStatus = accountStatus;
        this.bankingInstitute = bankingInstitute;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BankingInstitute getBankingInstitute() {
        return bankingInstitute;
    }

    public void setBankingInstitute(BankingInstitute bankingInstitute) {
        this.bankingInstitute = bankingInstitute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankAccount that = (BankAccount) o;
        return getAccountStatus() == that.getAccountStatus() &&
                Objects.equals(getIban(), that.getIban()) &&
                Objects.equals(getBankingInstitute(), that.getBankingInstitute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountStatus(), getIban(), getBankingInstitute());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountStatus=" + accountStatus +
                ", iban='" + iban + '\'' +
                ", bankingInstitute=" + bankingInstitute +
                ", id=" + id +
                '}';
    }
}
