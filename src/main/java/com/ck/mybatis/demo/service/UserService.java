package com.ck.mybatis.demo.service;


import com.ck.mybatis.demo.entity.User;

import java.util.Map;

/**
 *
 */
public interface UserService {

    public int insert(User user);

    public int deleteById(Long id);

    Map<String, Object> login(String userName, String password);
}