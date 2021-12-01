package com.zachdean.data_access;

import java.util.List;

public interface DataStore {
    <T> T getItem(String userId, String key);
    <T> List<T> getItems(String userId);
    void saveItem(String userId, String key, Object item);
}
