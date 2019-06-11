package entity;

import entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Transaction extends GeneratedIdEntity implements Serializable {
    public enum TransactionStatus {
        NEW,
        PENDING,
        DONE,
    }
    public enum TransactionType {
        NEW,
        PENDING,
        DONE,
    }
    public enum Duration {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY,
        ANNUALLY,
    }
    private String payeeIban;
    private String payerIban;
    private double amount;
    private String payeeBic;
    private String payerBic;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private Duration duration;

    public Transaction() {

    }


}
