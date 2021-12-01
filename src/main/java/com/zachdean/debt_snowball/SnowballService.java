package com.zachdean.debt_snowball;

import com.zachdean.data_access.DataStore;

import java.util.List;

public class SnowballService {
    private final DataStore dataStore;

    public SnowballService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void saveDebt(String userId, Debt debt) {
        dataStore.saveItem(userId, debt.getName(), debt);
    }

    public List<Debt> getDebts(String userId) {

        return dataStore.getItems(userId);
    }
}
