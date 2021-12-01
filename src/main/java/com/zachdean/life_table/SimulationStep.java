package com.zachdean.life_table;

import com.zachdean.debt_snowball.Debt;
import com.zachdean.major_expense.Expense;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SimulationStep {
    BigDecimal Amount;
    Date Date;
    List<Debt> DebtsPaidOff;
    List<Expense> Expenses;

    public SimulationStep(BigDecimal amount, Date date, List<Debt> debtsPaidOff, List<Expense> expenses) {
        Amount = amount;
        Date = date;
        DebtsPaidOff = debtsPaidOff;
        Expenses = expenses;
    }
}
