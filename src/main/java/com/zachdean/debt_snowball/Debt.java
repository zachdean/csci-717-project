package com.zachdean.debt_snowball;

import java.math.BigDecimal;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Debt implements Cloneable {
    private String userId;
    private boolean isPaidOff;
    private BigDecimal interestRate;
    private BigDecimal balance;
    private BigDecimal payment;
    private String name;    

    public boolean getIsPaidOff() {
        return isPaidOff;
    }

    public void setIsPaidOff(boolean paidOff) {
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

    @DynamoDbSortKey
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

    @DynamoDbPartitionKey
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getId() { return userId + "-" + name;}

    public Debt clone() throws CloneNotSupportedException
    {
        return (Debt)super.clone();
    }
}
