package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String dbConf = "jdbc:mysql://localhost:3306/test113";
    private final String user = "root";
    private final String password = "Root@12345";
    private Connection connection = null;
    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(dbConf, user, password);
            return connection;
        } catch (SQLException e) {
            System.out.println("DB connection exception");
            e.printStackTrace();
            return null;
        }
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("DB closing exception");
                e.printStackTrace();
            }
        } else {
            System.out.println("DB is closed");
        }
    }
}
