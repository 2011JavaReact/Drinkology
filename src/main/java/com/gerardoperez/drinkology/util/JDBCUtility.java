        package com.gerardoperez.drinkology.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {

    public static Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "pokemon";

        Connection connection;

        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(url, username, password);

        return connection;

    }
}
