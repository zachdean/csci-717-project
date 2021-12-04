package com.zachdean.functions;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.zachdean.investment.Investment;
import com.zachdean.investment.InvestmentService;

public class InvestmentFunction {
    private final InvestmentService investmentService;
    private final CosmosDataStore dataStore;

    public InvestmentFunction() {
        super();

        dataStore = new CosmosDataStore("investment", "name");
        investmentService = new InvestmentService(dataStore);
    }

    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @FunctionName("UpdateInvestments")
    public HttpResponseMessage update(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS,
                route = "investments/{userId}/investment")
                HttpRequestMessage<Optional<String>> request,
                @BindingName("userId") String userId,
            final ExecutionContext context) {
        
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String body = request.getBody().orElse(null);
        
        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        }

        try {
            this.dataStore.Initialize(); 
            Investment investment = new ObjectMapper().readValue(body, Investment.class);
            this.investmentService.saveInvestment(userId, investment);

        } catch (JsonProcessingException e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to parse").build();
        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to initialize Data Store").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    @FunctionName("GetInvestments")
    public HttpResponseMessage get(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS,
                route = "investments/{userId}")
                HttpRequestMessage<Optional<String>> request,
                @BindingName("userId") String userId,
            final ExecutionContext context) {
        
        context.getLogger().info("Java HTTP trigger processed a request.");

        try {
            this.dataStore.Initialize();
            List<Investment> investments = this.investmentService.getInvestments(userId);
            return request.createResponseBuilder(HttpStatus.OK).body(investments).build();

        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to fetch " + e.toString()).build();
        }        
    }
}
