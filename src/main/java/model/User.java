package model;

import java.math.BigDecimal;

/**
 * @author confff
 */
public class User {
    private Integer id;
    private String name;
    private String phone;
    //身份证
    private String idCard;
    //卡号
    private String cardId;

    private BigDecimal balance;
    private String password;

    public User() {
    }

    public User(String name, String phone, String idCard, String cardId, String password) {
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.cardId = cardId;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
