package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;
    private final Util util;

    public UserDaoHibernateImpl() {
        util = new Util();
    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastname VARCHAR(50) NOT NULL, " +
                "age SMALLINT NOT NULL" +
                "PRIMARY KEY (id))";
        session = util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(query).addEntity(User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("User table was dropped");
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXIST user;";
        session = util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(query).addEntity(User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("User table was dropped");
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Save user exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        session = util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User под номером – " + id + " удален из базы данных {" + user + "}");
        } catch (Exception e) {
            System.out.println("User by ID is unavailable");
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = util.getSessionFactory().openSession();
        List<User> usersDBList = null;
        try {
            session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            usersDBList = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.out.println("Remove user by ID exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
            if (usersDBList.size() == 0) {
                return null;
            } else {
                return usersDBList;
            }
        }
    }
    @Override
    public void cleanUsersTable() {
        session = util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            final List<User> usersDBList = session.createQuery(criteriaQuery).getResultList();
            for (Object obj : usersDBList) {
                session.delete(obj);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Remove user by ID exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
            util.closeSessionFactory();
        }
    }
}
