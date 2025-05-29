package com.example.project_donate_flood.service;

import com.example.project_donate_flood.model.User;

import java.util.List;

public interface IUserService {
    void Register(String email, String password, String confirmPassword);
    User Login(String email, String password);
    List<User> getAllUsers();
}
