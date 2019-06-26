package entity.dto;

import entity.BankAccount;
import entity.Transaction;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDTO {
    private BankAccount payee;
    private BankAccount payer;
    private double amount;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String reasonOfUsage;
    private Duration duration;
    private Date date;
    private Date lastTransactionDate;

    public TransactionDTO() {

    }

    public TransactionDTO(Transaction transaction) {
        this(transaction.getPayee(), transaction.getPayer(), transaction.getAmount(),
                transaction.getTransactionStatus(), transaction.getTransactionType(),
                transaction.getReasonOfUsage(), transaction.getDuration(), transaction.getDate());
    }

    public TransactionDTO(BankAccount payee, BankAccount payer, double amount,
                          TransactionStatus transactionStatus, TransactionType transactionType,
                          String reasonOfUsage, Duration duration, Date date) {
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.reasonOfUsage = reasonOfUsage;
        this.duration = duration;
        this.date = date;
        this.lastTransactionDate = date;
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

    public Date getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(Date lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }
}
