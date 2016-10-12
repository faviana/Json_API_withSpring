package com.ironyard.service;

import com.ironyard.data.Budget;
import com.ironyard.data.BudgetStat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by favianalopez on 10/11/16.
 */
public class BudgetService {

    /**
     * Get all budget in the database
     * @return returns List<Budget>
     * @throws SQLException
     */
    public List<Budget> getAllBudgets() throws SQLException {

        List<Budget> budgetList = new ArrayList<>();
        DbService Dbservice = new DbService();
        Connection connection = Dbservice.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM budget");
        budgetList = convertResultsToList(rs);

        return budgetList;
        }


    /**
     * Retrieves data that starts with "%" from category
     * @param search
     * @return returns object as List<Budget>
     * @throws SQLException
     */
    public List<Budget> search (String search) throws SQLException {
        List<Budget> budgetList = new ArrayList<>();
        DbService Dbservice = new DbService();
        Connection connection = null;

        try {
            connection = Dbservice.getConnection();

            // do a starts with search
            search = search + "%";
            PreparedStatement ps = connection.prepareStatement("SELECT  * FROM budget WHERE (bud_category ILIKE ?);");
            ps.setString(1, search);
            ResultSet resultSet = ps.executeQuery();
            budgetList = convertResultsToList(resultSet);

        }catch(SQLException exception){
            exception.printStackTrace();
            connection.rollback();

            throw exception;

        }finally {
            connection.close();

        }return budgetList;
    }

    /**
     * Generates Budget Statistics on all Budget
     * @return returns the List<BudgetStat>
     * @throws SQLException
     */
    public List<BudgetStat> getBudgetStats() throws SQLException {
        List<BudgetStat> budgetStatList = new ArrayList<>();
        DbService Dbservice = new DbService();
        Connection connection = Dbservice.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT bud_category, SUM (bud_budgeted_amount) AS bud_total, sum(bud_actual_amount) AS bud_total_a FROM budget GROUP BY bud_category;");

        while(resultSet.next()){
            BudgetStat budgetStat = new BudgetStat();
            budgetStat.setCategory(resultSet.getString("bud_category"));
            budgetStat.setTotalBudgetedAmount(resultSet.getDouble("bud_total"));
            budgetStat.setTotalActualAmount(resultSet.getInt("bud_total_a"));

            budgetStatList.add(budgetStat);

        }return budgetStatList;
    }

    /**
     * Retrieves budget objects as a List<Budget>
     * @param resultSet parameter is the set of results, the List<Budget>
     * @return returns the List<Budget>
     * @throws SQLException
     */
    private List<Budget> convertResultsToList(ResultSet resultSet) throws SQLException {
        List<Budget> budgetList = new ArrayList<>();

        while (resultSet.next()) {
            Budget budget = new Budget();
            budget.setId(resultSet.getInt("bud_id"));
            budget.setDescription(resultSet.getString("bud_description"));
            budget.setCategory(resultSet.getString("bud_category"));
            budget.setBudgetedAmount(resultSet.getDouble("bud_budgeted_amount"));
            budget.setActualAmount(resultSet.getDouble("bud_actual_amount"));

            budgetList.add(budget);

        }return budgetList;
    }

    /**
     * Saves a movie object to database.
     * Generates a new Budget id
     * @param myBudget
     * @throws SQLException
     */
    public void createBudget (Budget myBudget) throws SQLException {
        DbService Dbservice = new DbService();
        Connection connection = null;

        try{
            connection = Dbservice.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO budget (bud_id, bud_description, bud_category, bud_budgeted_amount, bud_actual_amount) VALUES (nextval('BUDGET_SEQ'),?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, myBudget.getDescription());
            preparedStatement.setString(2, myBudget.getCategory());
            preparedStatement.setDouble(3, myBudget.getBudgetedAmount());
            preparedStatement.setDouble(4, myBudget.getBudgetedAmount());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                myBudget.setId(generatedKeys.getLong(1));
            }

        }catch(SQLException exception){
            exception.printStackTrace();
            connection.rollback();

            throw exception;

        }finally {
            connection.close();
        }
    }

    /**
     * retrieves budget objects by ID from database
     * @param id
     * @return returns budget by id
     * @throws SQLException
     */

    public Budget getBudgetById (long id) throws SQLException {
        DbService Dbservice = new DbService();
        Connection connection = null;
        Budget foundById = null;

        try {
            connection = Dbservice.getConnection();

            // do a starts with search
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM budget WHERE bud_id = ?;");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                foundById = new Budget();
                foundById.setDescription(resultSet.getString("bud_description"));
                foundById.setCategory(resultSet.getString("bud_category"));
                foundById.setBudgetedAmount(resultSet.getDouble("bud_budgeted_amount"));
                foundById.setActualAmount(resultSet.getDouble("bud_actual_amount"));
                foundById.setId(resultSet.getLong("bud_id"));
            }

        }catch(SQLException exception){
            exception.printStackTrace();
            connection.rollback();
            throw exception;

        }finally {
            connection.close();

        }return foundById;
    }

    /**
     * Updates the movie specified to the values specified in the parameter movie
     * @param budget
     * @throws SQLException
     */
    public void update (Budget budget) throws SQLException{
        DbService Dbservice = new DbService();
        Connection connection = null;
        try {
            connection = Dbservice.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE budget SET bud_description=?, bud_category=?, bud_budgeted_amount=?, bud_actual_amount=? WHERE bud_id= ?;");
            preparedStatement.setString(1, budget.getDescription());
            preparedStatement.setString(2, budget.getCategory());
            preparedStatement.setDouble(3, budget.getBudgetedAmount());
            preparedStatement.setDouble(4, budget.getActualAmount());
            preparedStatement.setLong(5, budget.getId());

            preparedStatement.executeUpdate();

        }catch(SQLException exception){
            exception.printStackTrace();
            connection.rollback();
            throw exception;

        }finally {
            connection.close();
        }
    }

    /**
     * Retrieves id object from database
     * nulls object id and updates id object.
     * @param id
     * @throws SQLException
     */

    public void delete (long id) throws SQLException {
        DbService Dbservice = new DbService();
        Connection connection = null;
        try {
            connection = Dbservice.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM budget where bud_id=?");
            prepareStatement.setLong(1, id);

            prepareStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
            connection.rollback();
            throw exception;

        } finally {
            connection.close();
        }
    }
}
