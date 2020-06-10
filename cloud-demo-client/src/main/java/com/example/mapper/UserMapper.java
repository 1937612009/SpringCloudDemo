package com.example.mapper;

import com.example.entity.PageBean;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User login(String username);

    int addUser(User user);

    int updateUser(User user);

    int delUser(User user);

    List<User> findAll();

    List<User> findLimitUser(PageBean page);
}
