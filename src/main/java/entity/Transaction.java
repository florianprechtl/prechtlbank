package entity;

import entity.dto.TransactionDTO;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;
import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction extends GeneratedIdEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount payee;

    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount payer;

    private double amount;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String reasonOfUsage;
    private Duration duration;
    private Date date;

    public Transaction() {
        payee = new BankAccount();
        payer = new BankAccount();
    }

    public Transaction(BankAccount payee, BankAccount payer, double amount, TransactionStatus transactionStatus, TransactionType transactionType, String reasonOfUsage, Duration duration, Date date) {
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.reasonOfUsage = reasonOfUsage;
        this.duration = duration;
        this.date = date;
    }

    public Transaction(TransactionDTO transactionDTO) {
        this.payee = transactionDTO.getPayee();
        this.payer = transactionDTO.getPayer();
        this.amount = transactionDTO.getAmount();
        this.transactionStatus = transactionDTO.getTransactionStatus();
        this.transactionType = transactionDTO.getTransactionType();
        this.reasonOfUsage = transactionDTO.getReasonOfUsage();
        this.duration = transactionDTO.getDuration();
        this.date = transactionDTO.getDate();
    }

    public BankAccount getPayee() {
        return payee;
    }

    public void setPayee(BankAccount payee) {
        this.payee = payee;
    }

    public BankAccount getPayer() {
        return payer;
    }

    public void setPayer(BankAccount payer) {
        this.payer = payer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getReasonOfUsage() {
        return reasonOfUsage;
    }

    public void setReasonOfUsage(String reasonOfUsage) {
        this.reasonOfUsage = reasonOfUsage;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        if (!super.equals(o)) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.getAmount(), getAmount()) == 0 &&
                Objects.equals(getPayee(), that.getPayee()) &&
                Objects.equals(getPayer(), that.getPayer()) &&
                getTransactionStatus() == that.getTransactionStatus() &&
                getTransactionType() == that.getTransactionType() &&
                Objects.equals(getReasonOfUsage(), that.getReasonOfUsage()) &&
                getDuration() == that.getDuration() &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPayee(), getPayer(), getAmount(), getTransactionStatus(), getTransactionType(), getReasonOfUsage(), getDuration(), getDate());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "payee=" + payee +
                ", payer=" + payer +
                ", amount=" + amount +
                ", transactionStatus=" + transactionStatus +
                ", transactionType=" + transactionType +
                ", reasonOfUsage='" + reasonOfUsage + '\'' +
                ", duration=" + duration +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
