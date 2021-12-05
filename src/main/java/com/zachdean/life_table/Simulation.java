package com.zachdean.life_table;

import java.util.Date;
import java.util.List;

public class Simulation {
    private List<SimulationStep> steps;
    private Date targetDate;

    public Simulation(List<SimulationStep> steps, Date targetDate) {
        this.setSteps(steps);
        this.setTargetDate(targetDate);
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public List<SimulationStep> getSteps() {
        return steps;
    }

    public void setSteps(List<SimulationStep> steps) {
        this.steps = steps;
    }
}
