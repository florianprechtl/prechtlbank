package entity.dto;

import entity.Transaction;
import entity.enums.Duration;
import entity.enums.TransactionStatus;
import entity.enums.TransactionType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDTO {
    private String payeeIban;
    private String payerIban;
    private double amount;
    private String payeeBic;
    private String payerBic;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String reasonOfUsage;
    private Duration duration;

    public TransactionDTO() {

    }

    public TransactionDTO(Transaction transaction) {
        this(transaction.getPayeeIban(), transaction.getPayerIban(), transaction.getAmount(), transaction.getPayeeBic(),
                transaction.getPayerBic(), transaction.getTransactionStatus(), transaction.getTransactionType(),
                transaction.getReasonOfUsage(), transaction.getDuration());
    }

    public TransactionDTO(String payeeIban, String payerIban, double amount, String payeeBic, String payerBic,
                          TransactionStatus transactionStatus, TransactionType transactionType,
                          String reasonOfUsage, Duration duration) {
        this.payeeIban = payeeIban;
        this.payerIban = payerIban;
        this.amount = amount;
        this.payeeBic = payeeBic;
        this.payerBic = payerBic;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.reasonOfUsage = reasonOfUsage;
        this.duration = duration;
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
}
