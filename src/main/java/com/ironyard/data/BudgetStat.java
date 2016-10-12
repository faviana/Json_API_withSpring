package com.ironyard.data;

/**
 * Created by favianalopez on 10/11/16.
 */
public class BudgetStat {

    private String category;
    private double totalBudgetedAmount;
    private double totalActualAmount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalBudgetedAmount() {
        return totalBudgetedAmount;
    }

    public void setTotalBudgetedAmount(double totalBudgetedAmount) {
        this.totalBudgetedAmount = totalBudgetedAmount;
    }

    public double getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(double totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }
}
