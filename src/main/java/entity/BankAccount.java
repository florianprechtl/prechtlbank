package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.*;
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
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private BankInstitute bankInstitute;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions;


    public BankAccount() {

    }

    public BankAccount(String iban, AccountStatus accountStatus, BankInstitute bankInstitute, User user) {
        this.iban = iban;
        this.accountStatus = accountStatus;
        this.bankInstitute = bankInstitute;
        this.user = user;
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

    public BankInstitute getBankInstitute() {
        return bankInstitute;
    }

    public void setBankInstitute(BankInstitute bankInstitute) {
        this.bankInstitute = bankInstitute;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankAccount that = (BankAccount) o;
        return getAccountStatus() == that.getAccountStatus() &&
                Objects.equals(getIban(), that.getIban()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getBankInstitute(), that.getBankInstitute()) &&
                Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountStatus(), getIban(), getUser(), getBankInstitute(), getTransactions());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountStatus=" + accountStatus +
                ", iban='" + iban + '\'' +
                ", user=" + user +
                ", bankInstitute=" + bankInstitute +
                ", transactions=" + transactions +
                ", id=" + id +
                '}';
    }
}
