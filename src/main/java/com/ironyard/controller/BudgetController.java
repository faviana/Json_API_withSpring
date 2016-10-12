package com.ironyard.controller;

import com.ironyard.data.Budget;
import com.ironyard.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by favianalopez on 10/11/16.
 */

@RestController

public class BudgetController {

    private BudgetService budgetService = new BudgetService();

    //SAVE BUDGET
    @RequestMapping (value ="/mybudget", method = RequestMethod.POST)
    public Budget saveBudget (@RequestBody Budget budget){
        Budget savedBudget = null;

        try {
            budgetService.createBudget(budget);
            savedBudget = budgetService.getBudgetById(budget.getId());

        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return savedBudget;
    }

    //UPDATE BUDGET
    @RequestMapping(value = "/mybudget/update", method = RequestMethod.PUT)
    public Budget updateBudget (@RequestBody Budget budget){
        Budget updatedBudget = null;

        try {
            budgetService.update(budget);
            updatedBudget = budgetService.getBudgetById(budget.getId());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return updatedBudget;
    }

    //SHOW BUDGET
    @RequestMapping(value = "/mybudget/{id}", method = RequestMethod.GET)
    public Budget show(@PathVariable Integer id){
        Budget foundBudget = null;

        try {
            foundBudget = budgetService.getBudgetById(id);

        } catch (SQLException exception){
            exception.printStackTrace();
        }

        return foundBudget;
    }

    //LIST BUDGET
    @RequestMapping(value = "/mybudget", method = RequestMethod.GET)
    public List<Budget> budgetList(){
        List allBudget = null;

        try {
            allBudget = budgetService.getAllBudgets();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return allBudget;
    }

    //DELETE BUDGET
    @RequestMapping(value = "/mybudget/delete/{id}", method = RequestMethod.DELETE)
    public Budget deleteBudget (@PathVariable Integer id){
        Budget deletedBudget = null;

        try {
            deletedBudget = budgetService.getBudgetById(id);
            budgetService.delete(id);

        } catch (SQLException exception) {
            deletedBudget  = null;
            exception.printStackTrace();
        }
        return deletedBudget;
    }
}

