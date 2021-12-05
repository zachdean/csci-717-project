package com.zachdean.major_expense;

import org.junit.jupiter.api.BeforeEach;
import com.zachdean.DataStoreStub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {
    DataStoreStub<Expense> dataStore = new DataStoreStub<>();
    ExpenseService service = new ExpenseService(dataStore);
    Expense expense;

    @org.junit.jupiter.api.Test
    void saveExpense() {

        service.saveExpense("userId", expense);

        assertEquals(expense, dataStore.Item);
        assertEquals("userId", dataStore.UserId);
        assertEquals("Test", dataStore.Key);
    }

    @org.junit.jupiter.api.Test
    void getExpenses() {
        dataStore.UserId = "userId";

        dataStore.Items = new ArrayList<>();
        dataStore.Items.add(expense);
        dataStore.Items.add(new Expense());

        List<Expense> actual = service.getExpenses("userId");

        assertEquals(dataStore.Items, actual);
    }

    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setName("Test");
        expense.setAmount(BigDecimal.valueOf(42));
    }
}