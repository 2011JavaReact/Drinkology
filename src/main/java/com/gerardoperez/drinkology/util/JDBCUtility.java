package com.gerardoperez.drinkology.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://my-database.cerqffq9i4nk.us-east-2.rds.amazonaws.com:5432/project1";
        String username = "postgres";
        String password = "charmander";

        Connection connection;

        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(url, username, password);

        return connection;

    }
}
