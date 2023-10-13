package com.ck.mybatis.demo.dto;

import com.ck.mybatis.demo.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String token;
    private Long userid;
    private String username;
    private Long loginTime;
    private Long expireTime;
    private User sysUser;

}
