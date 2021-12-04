package com.zachdean.data_access;

import java.util.List;

import com.zachdean.debt_snowball.Debt;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

public interface DataFetcher {
    List<Debt> fetchDebts(String userId);
    List<Investment> fetchInvestments(String userId);
    List<Expense> fetchExpenses(String userId);
}
