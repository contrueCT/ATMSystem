package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author confff
 */
public class Transaction {
    private Integer id;
    private final Integer userId;
    private final String type;
    private final BigDecimal amount;
    private final String sourceCard;
    private final String targetCard;
    private LocalDateTime transactionDate;

    public Transaction(Integer userId, String type, BigDecimal amount, String sourceCard, String targetCard) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.sourceCard = sourceCard;
        this.targetCard = targetCard;
    }

    public Transaction(Integer id, Integer userId, String type, BigDecimal amount, String sourceCard, String targetCard, LocalDateTime transactionDate) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.sourceCard = sourceCard;
        this.targetCard = targetCard;
        this.transactionDate = transactionDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String toCsvString(){
        return String.format("%d,%d,%s,%.2f,%s,%s,%s",
                id,
                userId,
                type,
                amount,
                sourceCard,
                targetCard != null ? targetCard : "",
                transactionDate != null ? transactionDate : ""
        );
    }
}
