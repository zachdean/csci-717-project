package com.zachdean.functions;

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
import com.zachdean.investment.Investment;
import com.zachdean.life_table.*;
import com.zachdean.major_expense.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LifeTableFunction {
    private AzureDataFetcher dataFetcher = new AzureDataFetcher();
    private LifeTableService lifeTableService = new LifeTableService();

    @FunctionName("GetLifeTable")
    public HttpResponseMessage get(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET},
                authLevel = AuthorizationLevel.ANONYMOUS,
                route = "life-table/{userId}")
                HttpRequestMessage<Optional<String>> request,
                @BindingName("userId") String userId,
                @BindingName("targetDate") Date targetDate,
            final ExecutionContext context) {
        
        context.getLogger().info("Java HTTP trigger processed a request.");

        try {            
            List<Debt> debts = this.dataFetcher.fetchDebts(userId);
            List<Investment> investments = this.dataFetcher.fetchInvestments(userId);
            List<Expense> expenses = this.dataFetcher.fetchExpenses(userId);
            
            Simulation simulation = this.lifeTableService.GetSimulation(targetDate, debts, expenses, investments);

            return request.createResponseBuilder(HttpStatus.OK).body(simulation).build();

        } catch (Exception e) {
            context.getLogger().warning(e.toString());
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Failed to fetch " + e.toString()).build();
        }        
    }

}
