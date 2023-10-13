package com.ck.mybatis.demo.mapper;


import com.ck.mybatis.demo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User row);

    User getUserInfo(@Param("userName") String userName, @Param("password") String password);
}