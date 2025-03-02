package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author confff
 */
public class Transaction {
    private Integer id;
    private Integer userId;
    private String type;
    private BigDecimal amount;
    private String sourceCard;
    private String targetCard;
    private LocalDateTime transactionDate;

    public Transaction(Integer userId, String type, BigDecimal amount, String sourceCard, String targetCard) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.sourceCard = sourceCard;
        this.targetCard = targetCard;
    }

    public Transaction() {
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(String sourceCard) {
        this.sourceCard = sourceCard;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public void setTargetCard(String targetCard) {
        this.targetCard = targetCard;
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
