package com.zachdean.life_table;

import com.zachdean.debt_snowball.Debt;
import com.zachdean.major_expense.Expense;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SimulationStep {
    private BigDecimal amount;
    private Date date;
    private List<Debt> debtsPaidOff;
    private List<Expense> expenses;

    public SimulationStep(BigDecimal amount, Date date, List<Debt> debtsPaidOff, List<Expense> expenses) {
        this.setAmount(amount);
        this.setDate(date);
        this.setDebtsPaidOff(debtsPaidOff);
        this.setExpenses(expenses);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Debt> getDebtsPaidOff() {
        return debtsPaidOff;
    }

    public void setDebtsPaidOff(List<Debt> debtsPaidOff) {
        this.debtsPaidOff = debtsPaidOff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
