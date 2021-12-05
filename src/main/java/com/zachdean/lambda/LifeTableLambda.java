package com.zachdean.lambda;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.major_expense.*;
import com.zachdean.investment.*;
import com.zachdean.data_access.DataFetcher;
import com.zachdean.debt_snowball.*;
import com.zachdean.life_table.*;

public class LifeTableLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private DataFetcher dataFetcher = new DataFetcher();
    private LifeTableService lifeTableService = new LifeTableService();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String userId = input.getPathParameters().get("userId");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
                
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate = formatter.parse(input.getQueryStringParameters().get("targetDate"));
            
            this.dataFetcher.setBaseAddess(new URL("https://0le7d7w2df.execute-api.us-east-1.amazonaws.com").toURI());
            
            List<Debt> debts = this.dataFetcher.fetchDebts(userId);
            List<Investment> investments = this.dataFetcher.fetchInvestments(userId);
            List<Expense> expenses = this.dataFetcher.fetchExpenses(userId);
            
            Simulation simulation = this.lifeTableService.GetSimulation(targetDate, debts, expenses, investments);

            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            String json = mapper.writeValueAsString(simulation);

            return response
                    .withBody(json)
                    .withStatusCode(200);

        } catch (IOException | ParseException | URISyntaxException e) {
            context.getLogger().log(e.toString());
            return response
                    .withBody(e.toString())
                    .withStatusCode(500);
        }
    }
}
