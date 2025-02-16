package model;

import java.math.BigDecimal;

public class User {
    private Integer id;
    private String name;
    private String phone;
    private String id_card;
    private String card_number;
    private BigDecimal balance;
    private String password;

    public User() {
    }

    public User(String name, String phone, String id_card, String card_number, String password) {
        this.name = name;
        this.phone = phone;
        this.id_card = id_card;
        this.card_number = card_number;
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

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
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
