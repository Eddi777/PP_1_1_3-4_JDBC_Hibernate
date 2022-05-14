package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private final Util util;
    public UserDaoJDBCImpl() {
        util = new Util();
    }

    public void createUsersTable() {
        try {
            Connection conn = util.getConnection();
            Statement state = conn.createStatement();
            state.executeUpdate("CREATE TABLE IF NOT EXISTS User ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(50),"
                    + "lastname VARCHAR(50),"
                    + "age SMALLINT)");
        } catch (SQLException e) {
            System.out.println("An error has occured on User save");
            e.printStackTrace();
        }
        util.closeConnection();
    }

    public void dropUsersTable() {
        try {
            Connection conn = util.getConnection();
            Statement state = conn.createStatement();
            state.execute("DROP TABLE User;");
        } catch (SQLException e) {
            System.out.println("An error has occured on User save");
        }
        util.closeConnection();
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection conn = util.getConnection();
            Statement state = conn.createStatement();
            PreparedStatement prepSt = conn.prepareStatement("INSERT INTO User (name, lastname, age)"
                    + " VALUES (?, ?, ?)");
            //Set the parameters:
            prepSt.setString(1, name);
            prepSt.setString(2, lastName);
            prepSt.setShort(3, Short.valueOf(age));
            int rows = prepSt.executeUpdate();
            if (rows != 0) {
                System.out.println("User с именем – " + name);
            }
        } catch (SQLException e) {
            System.out.println("An error has occured on User save");
            e.printStackTrace();
        }
        util.closeConnection();
    }

    public void removeUserById(long id) {
        try {
            Connection conn = util.getConnection();
            PreparedStatement prepSt = conn.prepareStatement("DELETE FROM User WHERE Id = " + id);
            int rows = prepSt.executeUpdate();
            if (rows != 0) {
                System.out.println("Users с id " + id + " удален из базы данных");
            }
        } catch (SQLException e) {
            System.out.println("An error has occured on User save");
            e.printStackTrace();
        }
        util.closeConnection();
    }

    public List<User> getAllUsers() {
        List<User> lst = new ArrayList();
        try {
            Connection conn = util.getConnection();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("select * from User");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                lst.add(user);
            }
        } catch (SQLException e) {
            System.out.println("An error has occured on Get All User");
        }
        util.closeConnection();
        return lst;
    }

    public void cleanUsersTable() {
        try {
            Connection conn = util.getConnection();
            Statement state = conn.createStatement();
            PreparedStatement prepSt = conn.prepareStatement("TRUNCATE TABLE User");
            prepSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error has occured on User save " + e.getMessage());
        }
        util.closeConnection();
    }
}
