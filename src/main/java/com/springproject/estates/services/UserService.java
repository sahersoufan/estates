package com.springproject.estates.services;

import com.springproject.estates.domain.Role;
import com.springproject.estates.domain.User;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String RoleName);
    User getUser(String username);
    User getUser(HttpServletRequest request );
    List<User>getUsers();
}
