package com.zachdean.life_table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.zachdean.debt_snowball.Debt;
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

       assertEquals(36, simulation.Steps.size());
       SimulationStep step = simulation.Steps.get(14);

       Date expected = Date.from(localDate.plusMonths(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
       assertEquals(expected, step.Date);
       assertEquals(debts.get(0).getName(), step.DebtsPaidOff.get(0).getName());
       assertEquals(BigDecimal.valueOf(16988.43), step.Amount);
       
       step = simulation.Steps.get(28);
       assertEquals(expenses.get(1).getName(), step.Expenses.get(0).getName());
       assertEquals(BigDecimal.valueOf(17501.44), step.Amount);
    }
}