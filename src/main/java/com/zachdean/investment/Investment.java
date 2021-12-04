package com.zachdean.investment;

import java.math.BigDecimal;

public class Investment implements Cloneable {
    private BigDecimal interestRate;
    private BigDecimal amount;
    private String name;
    private String userId;

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() { return userId + "-" + name;}
    
    public String getUserId() { return userId; }
    public void serUserId(String userId) { this.userId = userId; }

    public Investment clone() throws CloneNotSupportedException
    {
        return (Investment)super.clone();
    }
}
