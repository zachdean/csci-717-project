package com.zachdean.lambda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.debt_snowball.Debt;
import com.zachdean.debt_snowball.SnowballService;

public class DebtsFetchLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamodbDataStore dataStore = new DynamodbDataStore("csci717-project-debts");
    private final SnowballService snowballService = new SnowballService(dataStore);
    
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String userId = input.getPathParameters().get("userId");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {

            this.dataStore.initialize();
            List<Debt> debts = this.snowballService.getDebts(userId);
            this.dataStore.close();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(debts);

            return response
                    .withBody(json)
                    .withStatusCode(200);
        } catch (Exception e) {
            return response
                    .withBody(e.toString())
                    .withStatusCode(500);
        }
    }
}
