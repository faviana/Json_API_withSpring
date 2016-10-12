package com.ironyard.controller;

import com.ironyard.data.BudgetStat;
import com.ironyard.service.BudgetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by favianalopez on 10/11/16.
 */

@RestController
public class BudgetStatController {

    private BudgetService budgetService = new BudgetService();

    @RequestMapping (value = "/budgetstat", method = RequestMethod.GET)
    public List<BudgetStat> budgetStatList (){
        List<BudgetStat> budgetStat = null;

        try{
            budgetStat = budgetService .getBudgetStats();

        }catch (SQLException exception){
            exception.printStackTrace();

        }return budgetStat;
    }
}
