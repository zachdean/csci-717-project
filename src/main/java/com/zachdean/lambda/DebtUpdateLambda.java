package com.zachdean.lambda;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.debt_snowball.Debt;
import com.zachdean.debt_snowball.SnowballService;

public class DebtUpdateLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamodbDataStore dataStore = new DynamodbDataStore("csci717-project-debts");
    private final SnowballService snowballService = new SnowballService(dataStore);

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            String userId = input.getPathParameters().get("userId");
            Debt debt = new ObjectMapper().readValue(input.getBody(), Debt.class);

            this.dataStore.initialize();
            this.snowballService.saveDebt(userId, debt);
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
