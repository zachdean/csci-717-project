package com.zachdean.investment;

import com.zachdean.data_access.DataStore;

import java.util.List;

public class InvestmentService {
    private DataStore dataStore;

    public InvestmentService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void saveInvestment(String userId, Investment investment) {
        dataStore.saveItem(userId, investment.getName(), investment, Investment.class);
    }

    public List<Investment> getInvestments(String userId) {
        return dataStore.getItems(userId, Investment.class);

    }
}
