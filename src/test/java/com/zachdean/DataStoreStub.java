package com.zachdean;

import com.zachdean.data_access.DataStore;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DataStoreStub<Type> implements DataStore {

    public ArrayList<Type> Items;
    public Type Item;
    public String UserId;
    public String Key;

    @Override
    public <T> T getItem(String userId, String key, Class<T> cls) {
        if (userId == UserId && key == Key){
            return (T) Item;
        }
        return null;
    }

    @Override
    public <T> List<T> getItems(String userId, Class<T> cls) {
        if (userId == UserId){
            return (List<T>) Items;
        }
        return null;
    }

    @Override
    public <T> void saveItem(String userId, String key, T item, Class<T> cls) {
        Item = (Type)item;
        UserId = userId;
        Key = key;
    }
}
