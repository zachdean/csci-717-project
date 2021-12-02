package com.zachdean.life_table;

import com.zachdean.debt_snowball.Debt;
import com.zachdean.investment.Investment;
import com.zachdean.major_expense.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LifeTableService {
    // devide interest rate by twelve in simplified monthly interest rate
    private static final BigDecimal INTEREST_DENOMINATOR = BigDecimal.valueOf(12);

    public Simulation GetSimulation(Date target, List<Debt> rawDebts, List<Expense> rawExpenses, List<Investment> rawInvestments) {
        ArrayList<Debt> debts = new ArrayList<>();
        rawDebts.forEach((debt) -> {try {
            debts.add(debt.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }});

        ArrayList<Investment> investments = new ArrayList<>();
        rawInvestments.forEach((investment) -> {try {
            investments.add(investment.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }});

        ArrayList<SimulationStep> steps = new ArrayList<>();
        
        LocalDate localDate = LocalDate.now();
        Date simulationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        while (simulationDate.before(target)) {
            
            ArrayList<Debt> paidOffDebts = calulateDebts(debts);
            ArrayList<Expense> expenses = getExpenses(rawExpenses, simulationDate);
            
            BigDecimal netWorth = sumInvestmentsBalance(investments)
                                        .subtract(sumDebtBalances(debts))
                                        .subtract(sumExpenses(expenses));

            SimulationStep step = new SimulationStep(netWorth, simulationDate, paidOffDebts, expenses);

            steps.add(step);
            localDate = localDate.plusMonths(1);
            simulationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }        

        return new Simulation(steps, target);
    }

    private ArrayList<Expense> getExpenses(List<Expense> rawExpenses, Date simulationDate) {
        ArrayList<Expense> expenses = new ArrayList<>();
        for (Expense expense : rawExpenses) {
            if (expense.getPurchaseDate().compareTo(simulationDate) == 0) {
                expenses.add(expense);
            }
        }
        return expenses.size() == 0 ? null : expenses;
    }

    private BigDecimal sumExpenses(List<Expense> expenses) {
        BigDecimal amount = BigDecimal.ZERO;
        if (expenses == null){
            return amount;
        }
        for (Expense expense : expenses){
            amount = amount.add(expense.getAmount());
        }
        return amount;
    }

    private ArrayList<Debt> calulateDebts(ArrayList<Debt> debts)
    {
        ArrayList<Debt> paidOffDebts = new ArrayList<>();

        for (Debt debt : debts) {
            if (debt.isPaidOff()){
                continue;
            }

            BigDecimal interest = debt.getBalance().multiply(debt.getInterestRate()).divide(INTEREST_DENOMINATOR, 2, RoundingMode.HALF_EVEN);
            BigDecimal newBalance = debt.getBalance().add(interest).subtract(debt.getPayment());

            if (newBalance.compareTo(BigDecimal.ZERO) <= 0){
                debt.setPaidOff(true);
                debt.setBalance(BigDecimal.ZERO);
                paidOffDebts.add(debt);
                continue;
            }

            debt.setBalance(newBalance);
        }

        return paidOffDebts.size() == 0 ? null : paidOffDebts;
    }

    private BigDecimal sumDebtBalances(List<Debt> debts){
        BigDecimal balance = BigDecimal.ZERO;
        for (Debt debt : debts) {
            balance = balance.add(debt.getBalance());
        }
        return balance;
    }

    private BigDecimal sumInvestmentsBalance(List<Investment> investments){

        BigDecimal totalBalance = BigDecimal.ZERO;
        for (Investment investment : investments) {
            BigDecimal interest = investment.getAmount().multiply(investment.getInterestRate()).divide(INTEREST_DENOMINATOR, 2, RoundingMode.HALF_EVEN);
            BigDecimal amount = investment.getAmount().add(interest);
            investment.setAmount(amount);

            totalBalance = totalBalance.add(investment.getAmount());
        }
        return totalBalance;
    }
}
