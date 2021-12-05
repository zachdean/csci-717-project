package com.zachdean.lambda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zachdean.data_access.DataStore;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamodbDataStore implements DataStore {
    private final String tableName;
    private DynamoDbClient ddb;
    private DynamoDbEnhancedClient enhancedClient;

    public DynamodbDataStore(String tableName) {
        super();

        this.tableName = tableName;
    }

    public void initialize(){
        Region region = Region.US_EAST_1;
        ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();  
    }

    public void close() {
        ddb.close();
    }

    @Override
    public <T> T getItem(String userId, String key, Class<T> cls) {
        
        return null;
    }

    @Override
    public <T> List<T> getItems(String userId, Class<T> cls) {
        List<T> items = new ArrayList<>();

        DynamoDbTable<T> table = enhancedClient.table(tableName, TableSchema.fromBean(cls));
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                .partitionValue(userId)
                .build());

        // Get items in the table and write out the ID value
        Iterator<T> results = table.query(queryConditional).items().iterator();
        
        while (results.hasNext()) {
            T rec = results.next();
            items.add(rec);
        }
        return items;
    }

    @Override
    public <T> void saveItem(String userId, String key, T item, Class<T> cls) {
        DynamoDbTable<T> table = enhancedClient.table(tableName, TableSchema.fromBean(cls));

        table.putItem(item);
    }
}
