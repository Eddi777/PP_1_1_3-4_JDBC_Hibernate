package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /**        В методе main класса Main должны происходить следующие операции:
         Создание таблицы User(ов)
         Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
         Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
         Очистка таблицы User(ов)
         Удаление таблицы
         */

        User[] users = new User[4];
        users[0] = new User("Eduard 15", "Sharipov 1", (byte) 45);
        users[1] = new User("Eduard 17", "Sharipov 2", (byte) 46);
        users[2] = new User("Eduard 19", "Sharipov 3", (byte) 47);
        users[3] = new User("Eduard 20", "Sharipov 4", (byte) 48);

        final UserService userService = new UserServiceImpl();
        //final UserDaoJDBCImpl userService = new UserDaoJDBCImpl();


        userService.dropUsersTable();
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            userService.saveUser(users[i].getName(), users[i].getLastName(), users[i].getAge());
        }
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.removeUserById(1);
        userService.removeUserById(3);
        List<User> lst = userService.getAllUsers();
        for (User usr: lst) {
            System.out.println(usr.toString());
        }
        userService.cleanUsersTable();
        System.out.println("<<< App is finished >>>");
    }
}
