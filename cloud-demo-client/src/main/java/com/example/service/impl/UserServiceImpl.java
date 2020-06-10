package com.example.service.impl;

import com.example.entity.PageBean;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String login(User user) {
        User loginUser = userMapper.login(user.getUsername());
        if (null == loginUser){
            System.out.println("未注册");
            return "未注册";
        }
        if (!user.getPassword().equals(loginUser.getPassword())){
            System.out.println("密码错误");
            return "密码错误";
        }

        String token = UUID.randomUUID().toString().replace("-","");
        redisUtils.set(token,loginUser);
        return token;
    }

    @Override
    public String addUser(User user) {
        User findUser = findUser(user);
        if (null != findUser){
            return "账号已注册！";
        }
        int num = userMapper.addUser(user);
        if (1 != num){
            return "注册失败";
        }
        return "注册成功";
    }

    @Override
    public String updateUser(User user) {
        int num = userMapper.updateUser(user);
        if (1 != num){
            return "修改失败";
        }
        return "修改成功";
    }

    @Override
    public String delUser(User user) {
        int num = userMapper.delUser(user);
        if (1 != num){
            return "删除失败";
        }
        return "删除成功";
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public User findUser(User user) {
        User findUserByUsername = userMapper.login(user.getUsername());
        return findUserByUsername;
    }

    @Override
    public List<User> findLimitUser(PageBean page) {
        page.setDesc("ASC");
        return userMapper.findLimitUser(page);
    }

}
