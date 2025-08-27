package com.example.OLS.Repository;

import com.example.OLS.Model.User;

import java.util.List;
import java.util.Optional;

public interface userServices {
    User registerUser(User user);
    Optional<User> getUserById(Integer id);
    List<User> getAllUsers();
    User updateUser( Integer id, User updatedUser);
    void deleteUser(Integer id);
}
