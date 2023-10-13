package com.ck.mybatis.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String createId;
    private Date createTime;
    private String updateId;
    private Date updateTime;
    private Integer del;
    private static final long serialVersionUID = 1L;
}