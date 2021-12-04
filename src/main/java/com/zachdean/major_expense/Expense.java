package com.zachdean.major_expense;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense implements Cloneable {
    private BigDecimal amount;
    private Date purchaseDate;
    private String name;
    private String userId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() { return userId + "-" + name;}

    public String getUserId() { return userId; }    
    public void setUserId(String userId) { this.userId = userId; }
}
