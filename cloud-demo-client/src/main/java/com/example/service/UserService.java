package com.example.service;

import com.example.entity.PageBean;
import com.example.entity.User;

import java.util.List;

public interface UserService {

    String login(User user);

    String addUser(User user);

    String updateUser(User user);

    String delUser(User user);

    List<User> findAll();

    User findUser(User user);

    List<User> findLimitUser(PageBean page);
}
