package com.zachdean.lambda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.major_expense.*;

public class ExpenseFetchLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamodbDataStore dataStore = new DynamodbDataStore("csci717-project-expenses");
    private final ExpenseService expenseService = new ExpenseService(dataStore);
    
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String userId = input.getPathParameters().get("userId");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {

            this.dataStore.initialize();
            List<Expense> expenses = this.expenseService.getExpenses(userId);
            this.dataStore.close();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(expenses);

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
