package entity;

import entity.dto.TransactionDTO;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;
import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction extends GeneratedIdEntity implements Serializable {

    private String payeeIban;
    private String payerIban;
    private double amount;
    private String payeeBic;
    private String payerBic;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String reasonOfUsage;
    private Duration duration;
    private Date date;

    public Transaction() {

    }

    public Transaction(String payeeIban, String payerIban, double amount, String payeeBic, String payerBic, TransactionStatus transactionStatus, TransactionType transactionType, String reasonOfUsage, Duration duration, Date date) {
        this.payeeIban = payeeIban;
        this.payerIban = payerIban;
        this.amount = amount;
        this.payeeBic = payeeBic;
        this.payerBic = payerBic;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.reasonOfUsage = reasonOfUsage;
        this.duration = duration;
        this.date = date;
    }

    public Transaction(TransactionDTO transactionDTO) {
        this.payeeIban = transactionDTO.getPayeeIban();
        this.payerIban = transactionDTO.getPayerIban();
        this.amount = transactionDTO.getAmount();
        this.payeeBic = transactionDTO.getPayeeBic();
        this.payerBic = transactionDTO.getPayerBic();
        this.transactionStatus = transactionDTO.getTransactionStatus();
        this.transactionType = transactionDTO.getTransactionType();
        this.reasonOfUsage = transactionDTO.getReasonOfUsage();
        this.duration = transactionDTO.getDuration();
        this.date = transactionDTO.getDate();
    }

    public String getPayeeIban() {
        return payeeIban;
    }

    public void setPayeeIban(String payeeIban) {
        this.payeeIban = payeeIban;
    }

    public String getPayerIban() {
        return payerIban;
    }

    public void setPayerIban(String payerIban) {
        this.payerIban = payerIban;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayeeBic() {
        return payeeBic;
    }

    public void setPayeeBic(String payeeBic) {
        this.payeeBic = payeeBic;
    }

    public String getPayerBic() {
        return payerBic;
    }

    public void setPayerBic(String payerBic) {
        this.payerBic = payerBic;
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.getAmount(), getAmount()) == 0 &&
                Objects.equals(getPayeeIban(), that.getPayeeIban()) &&
                Objects.equals(getPayerIban(), that.getPayerIban()) &&
                Objects.equals(getPayeeBic(), that.getPayeeBic()) &&
                Objects.equals(getPayerBic(), that.getPayerBic()) &&
                getTransactionStatus() == that.getTransactionStatus() &&
                getTransactionType() == that.getTransactionType() &&
                Objects.equals(getReasonOfUsage(), that.getReasonOfUsage()) &&
                getDuration() == that.getDuration();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPayeeIban(), getPayerIban(), getAmount(), getPayeeBic(), getPayerBic(), getTransactionStatus(), getTransactionType(), getReasonOfUsage(), getDuration());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "payeeIban='" + payeeIban + '\'' +
                ", payerIban='" + payerIban + '\'' +
                ", amount=" + amount +
                ", payeeBic='" + payeeBic + '\'' +
                ", payerBic='" + payerBic + '\'' +
                ", transactionStatus=" + transactionStatus +
                ", transactionType=" + transactionType +
                ", reasonOfUsage='" + reasonOfUsage + '\'' +
                ", duration=" + duration +
                ", id=" + id +
                '}';
    }
}
