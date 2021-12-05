package com.zachdean.debt_snowball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.zachdean.DataStoreStub;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnowballServiceTest {
    DataStoreStub<Debt> dataStore = new DataStoreStub<>();
    SnowballService service = new SnowballService(dataStore);
    Debt debt;

    @BeforeEach
    void setUp() {
        debt = new Debt();
        debt.setName("Test");
    }

    @Test
    void saveDebt() {
        service.saveDebt("userId", debt);

        assertEquals(debt, dataStore.Item);
        assertEquals("userId", dataStore.UserId);
        assertEquals("Test", dataStore.Key);
    }

    @Test
    void getDebts() {
        dataStore.UserId = "userId";

        dataStore.Items = new ArrayList<>();
        dataStore.Items.add(debt);
        dataStore.Items.add(new Debt());

        List<Debt> actual = service.getDebts("userId");

        assertEquals(dataStore.Items, actual);
    }
}