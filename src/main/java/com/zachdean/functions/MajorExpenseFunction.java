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
import com.zachdean.major_expense.*;

import java.util.List;
import java.util.Optional;

public class MajorExpenseFunction {
    private final ExpenseService expenseService;
    private final CosmosDataStore dataStore;

    public MajorExpenseFunction() {
        super();

        dataStore = new CosmosDataStore("expense", "name");
        expenseService = new ExpenseService(dataStore);
    }

    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @FunctionName("UpdateExpense")
    public HttpResponseMessage update(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS,
                route = "expenses/{userId}/expense")
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
            Expense expense = new ObjectMapper().readValue(body, Expense.class);
            this.expenseService.saveExpense(userId, expense);

        } catch (JsonProcessingException e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to parse").build();
        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to initialize Data Store").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    @FunctionName("GetExpenses")
    public HttpResponseMessage get(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS,
                route = "expenses/{userId}")
                HttpRequestMessage<Optional<String>> request,
                @BindingName("userId") String userId,
            final ExecutionContext context) {
        
        context.getLogger().info("Java HTTP trigger processed a request.");

        try {
            this.dataStore.Initialize();
            List<Expense> expenses = this.expenseService.getExpenses(userId);
            return request.createResponseBuilder(HttpStatus.OK).body(expenses).build();

        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to fetch " + e.toString()).build();
        }        
    }
}
