package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.MetadataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Util {

    public SessionFactory sessionFactory = null;

    public SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "Root@12345");
        properties.put(Environment.POOL_SIZE, "1");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "update");

        try {
            Configuration configuration = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            System.out.println("DataBase session opening exception " + e.getMessage());
            //StandardServiceRegistryBuilder.destroy(registry);
            return null;
        }
    }
    public void closeSessionFactory(){
        sessionFactory.close();
    }

    private final String dbConf = "jdbc:mysql://localhost:3306/test";
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
