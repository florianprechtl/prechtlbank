package entity.dto;

import entity.Transaction;

public class TransactionDTO {
    private String payeeIban;
    private String payerIban;
    private double amount;
    private String payeeBic;
    private String payerBic;
    private Transaction.TransactionStatus transactionStatus;
    private Transaction.TransactionType transactionType;
    private String reasonOfUsage;
    private Transaction.Duration duration;

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

    public Transaction.TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Transaction.TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getReasonOfUsage() {
        return reasonOfUsage;
    }

    public void setReasonOfUsage(String reasonOfUsage) {
        this.reasonOfUsage = reasonOfUsage;
    }

    public Transaction.Duration getDuration() {
        return duration;
    }

    public void setDuration(Transaction.Duration duration) {
        this.duration = duration;
    }
}
