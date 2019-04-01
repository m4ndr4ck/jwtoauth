package com.showme.service;

import com.showme.model.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);

    List<User> findAllUsers();

}
