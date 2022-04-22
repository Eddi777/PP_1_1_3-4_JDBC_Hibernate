package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class Util {

    public SessionFactory sessionFactory = null;

    public SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        final Configuration config = new Configuration()
                .setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("connection.url", "jdbc:mysql://localhost:3306/test")
                .setProperty("connection.username", "root")
                .setProperty("connection.password", "Root@12345")
                .setProperty("dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("current_session_context_class", "thread")
                .setProperty("connection.pool_size", "1")
                .setProperty("hbm2ddl.auto" ,"create-drop")
                .setProperty("show_sql", "true")
                .addClass(User.class);

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(config)
                .build();
        try {

            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            System.out.println("DataBase session opening exception " + e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
            return null;
        }
    }
    public void closeSessionFactory(){
        sessionFactory.close();
    }
}
