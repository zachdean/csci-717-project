package com.zachdean.data_access;

import java.util.List;

public interface DataStore {
    <T> T getItem(String userId, String key, Class<T> cls);

    <T> List<T> getItems(String userId, Class<T> cls);

    <T> void saveItem(String userId, String key, T item, Class<T> cls);
}
