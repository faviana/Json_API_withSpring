package com.ironyard.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by favianalopez on 10/11/16.
 */
public class DbService {

    private String user="postgres";
    private String password="admin";
    private String jdbcUrl="jdbc:postgresql://localhost:5432/postgres";

    /**
     * Creates connection to the database
     * @return returns the connection
     * @throws SQLException
     */

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl,user,password);

    }
}
