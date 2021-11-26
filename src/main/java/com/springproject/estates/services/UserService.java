package com.springproject.estates.services;

import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String RoleName);
    User getUser(String username);
    List<User>getUsers();
}
