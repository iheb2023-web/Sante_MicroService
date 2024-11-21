package com.santé.users.service;

import com.santé.users.entities.Role;
import com.santé.users.entities.User;
import com.santé.users.service.register.RegistrationRequest;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    List<User> findAllUsers();
    User registerUser(RegistrationRequest request);
    public void sendEmailUser(User u, String code);
    public User validateToken(String code);
}
