package com.zachdean.life_table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachdean.debt_snowball.Debt;
import com.zachdean.functions.AzureDataFetcher;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

import org.junit.jupiter.api.Test;

class LifeTableServiceTest {

    @Test
    void getSimulation_ExpectCorrectResults() {
        LifeTableService service = new LifeTableService();

        LocalDate localDate = LocalDate.now();

        List<Debt> debts = LifeTableTestData.getDebts();
        List<Expense> expenses = LifeTableTestData.getExpenses();
        Simulation simulation = service.GetSimulation(
                                            Date.from(localDate.plusMonths(36).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                            debts,
                                            expenses,
                                            LifeTableTestData.getInvestments());

       assertEquals(36, simulation.getSteps().size());
       SimulationStep step = simulation.getSteps().get(14);

       Date expected = Date.from(localDate.plusMonths(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
       assertEquals(expected, step.getDate());
       assertEquals(debts.get(0).getName(), step.getDebtsPaidOff().get(0).getName());
       assertEquals(BigDecimal.valueOf(16988.43), step.getAmount());
       
       step = simulation.getSteps().get(28);
       assertEquals(expenses.get(1).getName(), step.getExpenses().get(0).getName());
       assertEquals(BigDecimal.valueOf(17501.44), step.getAmount());
    }

    @Test
    public void testDeserialization() throws JsonMappingException, JsonProcessingException{
        String json = "[{\"amount\":1500.0,\"purchaseDate\":\"2022-10-01T00:00:00.000+00:00\",\"name\":\"Thing 1\",\"userId\":\"123\",\"id\":\"123-Thing 1\"},{\"amount\":1580.0,\"purchaseDate\":\"2024-04-01T00:00:00.000+00:00\",\"name\":\"Thing 2\",\"userId\":\"123\",\"id\":\"123-Thing 2\"},{\"amount\":5000.0,\"purchaseDate\":\"2024-10-01T00:00:00.000+00:00\",\"name\":\"Thing 3\",\"userId\":\"123\",\"id\":\"123-Thing 3\"}]";
        ObjectMapper mapper = new ObjectMapper();
        List<Expense> expenses = mapper.readValue(json, new TypeReference<List<Expense>>(){});

        assertEquals(3, expenses.size());
        
    }
}