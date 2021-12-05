package com.zachdean.lambda;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.investment.*;

public class InvestmentUpdateLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamodbDataStore dataStore = new DynamodbDataStore("csci717-project-investments");
    private final InvestmentService snowballService = new InvestmentService(dataStore);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            String userId = input.getPathParameters().get("userId");
            Investment investment = new ObjectMapper().readValue(input.getBody(), Investment.class);

            this.dataStore.initialize();
            this.snowballService.saveInvestment(userId, investment);
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
