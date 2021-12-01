package com.zachdean.debt_snowball;

import java.math.BigDecimal;

public class Debt implements Cloneable {
    private boolean isPaidOff;
    private BigDecimal interestRate;
    private BigDecimal balance;
    private BigDecimal payment;
    private String name;    

    public boolean isPaidOff() {
        return isPaidOff;
    }

    public void setPaidOff(boolean paidOff) {
        isPaidOff = paidOff;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Debt clone() throws CloneNotSupportedException
    {
        return (Debt)super.clone();
    }
}
