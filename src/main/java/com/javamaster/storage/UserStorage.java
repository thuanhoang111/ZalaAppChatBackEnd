package com.javamaster.storage;

import java.util.HashSet;
import java.util.Set;

import com.javamaster.entity.User;

public class UserStorage {

    private static UserStorage instance;
    private Set<User> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUser(User user) throws Exception {
        if (users.contains(user)) {
            throw new Exception("User aready exists with userName: " + user);
        }
        users.add(user);
    }
}
