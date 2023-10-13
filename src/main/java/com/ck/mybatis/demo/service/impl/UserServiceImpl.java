package com.ck.mybatis.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ck.mybatis.demo.dto.LoginUser;
import com.ck.mybatis.demo.entity.User;
import com.ck.mybatis.demo.mapper.UserMapper;
import com.ck.mybatis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public Map<String, Object> login(String userName, String password) {
        // 用户名或密码为空 错误
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            throw new RuntimeException("用户/密码必须填写");
        }
        User user = userMapper.getUserInfo(userName, password);
        if (Objects.isNull(user)) {
            throw new RuntimeException("登录用户：" + userName + " 不存在 或密码错误");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUserid(user.getId());
        loginUser.setUsername(user.getUserName());

        Map<String, Object> token = tokenService.createToken(loginUser);

        return token;
    }

    public int insert(User user) {
        // 用户名或密码为空 错误
        if (StrUtil.isBlank(user.getUserName())) {
            throw new RuntimeException("用户名称不能为空");
        }
        //校验是否存在
        User userInfo = userMapper.getUserInfo(user.getUserName(), null);
        if (Objects.nonNull(userInfo)) {
            throw new RuntimeException("用户名:" + user.getUserName() + " 已存在");
        }
        user.setDel(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insert(user);
    }

    public int deleteById(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

}