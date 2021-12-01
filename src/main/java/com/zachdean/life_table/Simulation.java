package com.zachdean.life_table;

import java.util.Date;
import java.util.List;

public class Simulation {
    List<SimulationStep> Steps;
    Date TargetDate;
    
    public Simulation(List<SimulationStep> steps, Date targetDate) {
        Steps = steps;
        TargetDate = targetDate;
    }
}
