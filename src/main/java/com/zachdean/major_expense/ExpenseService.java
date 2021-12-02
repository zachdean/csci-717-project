package com.zachdean.major_expense;

import com.zachdean.data_access.DataStore;

import java.util.List;

public class ExpenseService {
    private final DataStore dataStore;

    public ExpenseService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void saveExpense(String userId, Expense expense) {
        dataStore.saveItem(userId, expense.getName(), expense);
    }

    public List<Expense> getExpenses(String userId) {
        return dataStore.getItems(userId, Expense.class);
    }
}
