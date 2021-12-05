package com.zachdean.functions;

import java.util.ArrayList;
import java.util.List;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;

import com.zachdean.data_access.DataStore;

public class CosmosDataStore implements DataStore {

    private CosmosClient client;

    private static final String DATABASE_NAME = "CSCI717Project";
    private final String containerName;
    private CosmosContainer container;
    private CosmosDatabase database;

    public CosmosDataStore(String containerName, String rowKey) {
        this.containerName = containerName;
    }

    public void Initialize() throws Exception {
        client = new CosmosClientBuilder()
        .endpoint(System.getenv().get("AccountEndpoint"))
        .key(System.getenv().get("AccountKey"))
        .consistencyLevel(ConsistencyLevel.EVENTUAL)
        .buildClient();
        
        database = client.getDatabase(DATABASE_NAME);
        container = database.getContainer(containerName);
    }

    @Override
    public <T> T getItem(String userId, String key, Class<T> cls) {
        
        return null;
    }

    @Override
    public <T> List<T> getItems(String userId, Class<T> cls) {
        ArrayList<T> items = new ArrayList<>();
        String sql = "SELECT * FROM c WHERE c.userId = '"+ userId +"'";

        CosmosPagedIterable<T> filteredItems = container.queryItems(sql, new CosmosQueryRequestOptions(), cls);
        
        for (T item : filteredItems) {
            items.add(item);
        }              

        return items;
    }

    @Override
    public <T> void saveItem(String userId, String key, T item, Class<T> cls) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();        
        container.createItem(item, cosmosItemRequestOptions);
    }
    

    // private void createDatabaseIfNotExists() throws Exception {
    //     CosmosDatabaseResponse cosmosDatabaseResponse = client.createDatabaseIfNotExists(DATABASE_NAME);
    //     database = client.getDatabase(cosmosDatabaseResponse.getProperties().getId());
    // }

    // private void createContainerIfNotExists() throws Exception {
    //     //  Create container if not exists
    //     //  <CreateContainerIfNotExists>
    //     CosmosContainerProperties containerProperties =
    //         new CosmosContainerProperties(containerName, "/userId");

    //     List<String> uniqueKeyPaths = new ArrayList<String>();
    //     uniqueKeyPaths.add("/userId");
    //     uniqueKeyPaths.add("/" + rowKey);

    //     UniqueKey uniqueKey = new UniqueKey(uniqueKeyPaths);

    //     UniqueKeyPolicy uniqueKeyPolicy = new UniqueKeyPolicy();
    //     List<UniqueKey> uniqueKeys = new ArrayList<UniqueKey>();
    //     uniqueKeys.add(uniqueKey);
    //     uniqueKeyPolicy.setUniqueKeys(uniqueKeys);

        
    //     containerProperties.setUniqueKeyPolicy(uniqueKeyPolicy);

    //     // Create container with 400 RU/s
    //     CosmosContainerResponse cosmosContainerResponse =
    //         database.createContainerIfNotExists(containerProperties, ThroughputProperties.createAutoscaledThroughput(4));
    //     container = database.getContainer(cosmosContainerResponse.getProperties().getId());        
    // }

    public void close() {
        client.close();
    }
}
