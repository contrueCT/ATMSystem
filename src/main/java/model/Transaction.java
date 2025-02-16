package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer id;
    private Integer user_id;
    private String type;
    private BigDecimal amount;
    private String sourceCard;
    private String targetCard;
    private LocalDateTime transactionDate;

    public Transaction(Integer user_id, String type, BigDecimal amount, String sourceCard, String targetCard) {
        this.user_id = user_id;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
}
