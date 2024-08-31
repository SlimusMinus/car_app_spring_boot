package com.y_lab.car_shop_spring_boot.dataTest;

import com.y_lab.car_shop_spring_boot.model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Класс {@code Users} содержит предопределённые объекты пользователей и связанные с ними данные.
 * <p>
 * Этот класс используется для хранения и управления коллекцией пользователей, таких как список пользователей,
 * фильтры по различным критериям, а также для работы с ролями пользователей.
 * </p>
 */
public class Users {
    @Getter
    private static int UserId = 1;
    public static final int NOT_EXIST_ID = 800;
    public static final int USER_GET_ID = 2;
    public static final User administrator = new User(UserId++, "admin", "admin", "Alexandr", 33, "Moscow", new HashSet<>());
    public static final User manager1 = new User(UserId++, "manager1", "manager1", "John", 36, "New-York", new HashSet<>());
    public static final User manager2 = new User(UserId++, "manager2", "manager2", "Alexandr", 34, "Moscow", new HashSet<>());
    public static final User client1 = new User(UserId++, "client1", "client1", "Tanya", 25, "London", new HashSet<>());
    public static final User client2 = new User(UserId++, "client2", "client2", "Valera", 45, "Milan", new HashSet<>());
    private static final User client3 = new User(UserId++, "client3", "client3", "Robert", 33, "Moscow", new HashSet<>());
    public static final User editClient2 = new User(client2.getUserId(), "client222", "client222", "Pavel", 45, "Ivanovo", new HashSet<>());
    public static final List<User> USER_LIST = List.of(administrator, manager1, manager2, client1, client2, client3);
    public static final List<User> NAME_PHILTER = List.of(administrator, manager2);
    public static final List<User> CITY_PHILTER = List.of(administrator, manager2, client3);
    public static final List<User> AGE_PHILTER = List.of(administrator, client3);
    public static final List<User> USERS_NAME_SORT = List.of(administrator, manager2, manager1, client3, client1, client2);
    public static final List<User> USERS_AGE_SORT = List.of(client1, administrator, client3, manager2, manager1, client2);
    public static Map<Integer, User> testUsers = new HashMap<>();

    static {
        testUsers.put(administrator.getUserId(), administrator);
        testUsers.put(manager1.getUserId(), manager1);
        testUsers.put(manager2.getUserId(), manager2);
        testUsers.put(client1.getUserId(), client1);
        testUsers.put(client2.getUserId(), client2);
        testUsers.put(client3.getUserId(), client3);
    }
}