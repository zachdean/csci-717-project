package com.zachdean.functions;

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
import com.zachdean.debt_snowball.Debt;
import com.zachdean.debt_snowball.SnowballService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class DebtSnowballFunction {

    private final SnowballService snowballService;
    private final CosmosDataStore dataStore;

    public DebtSnowballFunction() {
        super();

        dataStore = new CosmosDataStore("snowball", "name");
        snowballService = new SnowballService(dataStore);
    }

    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it
     * using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     * 
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @FunctionName("UpdateDebtSnowball")
    public HttpResponseMessage update(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS, route = "snowball/{userId}/debt") HttpRequestMessage<Optional<String>> request,
            @BindingName("userId") String userId,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String body = request.getBody().orElse(null);

        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string or in the request body").build();
        }

        try {
            this.dataStore.Initialize();
            Debt debt = new ObjectMapper().readValue(body, Debt.class);
            this.snowballService.saveDebt(userId, debt);

        } catch (JsonProcessingException e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to parse").build();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            context.getLogger().warning(e.toString());
            context.getLogger().warning(sw.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body(e.getMessage() + " " + sw.toString())
                    .build();
        }

        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    @FunctionName("GetDebtSnowball")
    public HttpResponseMessage get(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, authLevel = AuthorizationLevel.ANONYMOUS, route = "snowball/{userId}") HttpRequestMessage<Optional<String>> request,
            @BindingName("userId") String userId,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        try {
            this.dataStore.Initialize();
            List<Debt> debts = this.snowballService.getDebts(userId);
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(debts)
                    .header("Content-Type", "application/json")
                    .build();

        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to fetch " + e.toString())
                    .build();
        }
    }
}
