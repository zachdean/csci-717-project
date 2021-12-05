package com.zachdean.life_table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zachdean.debt_snowball.Debt;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

public class LifeTableTestData {

    public static List<Debt> getDebts() {
        ArrayList<Debt> debts = new ArrayList<>();
        Debt debt = new Debt();
        debt.setBalance(BigDecimal.valueOf(20000));
        debt.setPayment(BigDecimal.valueOf(1500));
        debt.setInterestRate(BigDecimal.valueOf(.12));
        debt.setName("Test");

        debts.add(debt);
        return debts;
    }

    public static List<Investment> getInvestments() {
        ArrayList<Investment> investments = new ArrayList<>();
        Investment investment = new Investment();
        investment.setAmount(BigDecimal.valueOf(15000));
        investment.setInterestRate(BigDecimal.valueOf(.10));
        investment.setName("Test");

        investments.add(investment);
        return investments;
    }

    public static List<Expense> getExpenses() {
        LocalDate localDate = LocalDate.now();

        ArrayList<Expense> expenses = new ArrayList<>();

        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(1500));
        expense.setPurchaseDate(Date.from(localDate.plusMonths(10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        expense.setName("Thing 1");
        expenses.add(expense);

        expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(1580));
        expense.setPurchaseDate(Date.from(localDate.plusMonths(28).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        expense.setName("Thing 2");
        expenses.add(expense);

        expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(5000));
        expense.setPurchaseDate(Date.from(localDate.plusMonths(34).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        expense.setName("Thing 3");

        expenses.add(expense);
        return expenses;
    }

}
