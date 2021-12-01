package com.zachdean.major_expense;

import java.math.BigDecimal;
import java.util.Date;

public class Expense implements Cloneable {
    private BigDecimal Amount;
    private Date PurchaseDate;
    private String Name;

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public Date getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
