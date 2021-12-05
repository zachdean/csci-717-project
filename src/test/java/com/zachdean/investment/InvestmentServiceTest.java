package com.zachdean.investment;

import org.junit.jupiter.api.BeforeEach;
import com.zachdean.DataStoreStub;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentServiceTest {
    DataStoreStub<Investment> dataStore = new DataStoreStub<>();
    InvestmentService service = new InvestmentService(dataStore);
    Investment investment;

    @org.junit.jupiter.api.Test
    void saveInvestment() {

        service.saveInvestment("userId", investment);

        assertEquals(investment, dataStore.Item);
        assertEquals("userId", dataStore.UserId);
        assertEquals("Test", dataStore.Key);
    }

    @org.junit.jupiter.api.Test
    void getInvestments() {
        dataStore.UserId = "userId";

        dataStore.Items = new ArrayList<>();
        dataStore.Items.add(investment);
        dataStore.Items.add(new Investment());

        List<Investment> actual = service.getInvestments("userId");

        assertEquals(dataStore.Items, actual);

    }

    @BeforeEach
    void setUp() {
        investment = new Investment();
        investment.setName("Test");
    }
}