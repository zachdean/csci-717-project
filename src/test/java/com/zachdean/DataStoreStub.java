package com.zachdean;

import com.zachdean.data_access.DataStore;

import java.util.ArrayList;
import java.util.List;

public class DataStoreStub<T> implements DataStore {

    public ArrayList<T> Items;
    public T Item;
    public String UserId;
    public String Key;

    @Override
    public <T> T getItem(String userId, String key) {
        if (userId == UserId && key == Key){
            return (T) Item;
        }
        return null;
    }

    @Override
    public <T> List<T> getItems(String userId) {
        if (userId == UserId){
            return (List<T>) Items;
        }
        return null;
    }

    @Override
    public void saveItem(String userId, String key, Object item) {
        Item = (T)item;
        UserId = userId;
        Key = key;
    }
}
