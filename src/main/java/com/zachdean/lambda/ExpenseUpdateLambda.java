package com.zachdean.lambda;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.major_expense.*;

public class ExpenseUpdateLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamodbDataStore dataStore = new DynamodbDataStore("csci717-project-expenses");
    private final ExpenseService expenseService = new ExpenseService(dataStore);
    
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            String userId = input.getPathParameters().get("userId");
            Expense expense = new ObjectMapper().readValue(input.getBody(), Expense.class);

            this.dataStore.initialize();
            this.expenseService.saveExpense(userId, expense);
            this.dataStore.close();

            return response
                    .withStatusCode(200);

        } catch (IOException e) {
            context.getLogger().log(e.toString());
            return response
                    .withBody(e.toString())
                    .withStatusCode(500);
        }
    }    
}
