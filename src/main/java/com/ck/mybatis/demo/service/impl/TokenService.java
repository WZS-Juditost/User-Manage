package com.ck.mybatis.demo.service.impl;

import com.ck.mybatis.demo.constant.SecurityConstants;
import com.ck.mybatis.demo.dto.LoginUser;
import com.ck.mybatis.demo.utils.IdUtils;
import com.ck.mybatis.demo.utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * token Service
 *
 * @author: Mr.DengYuanFang
 * @project: springboot-mybatis
 * @version: 1.0
 * @createDate: 2023/07/09 13:48
 */
@Component
public class TokenService {

    private final static long expireTime = 720;

    public Map<String, Object> createToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getUserid();
        String userName = loginUser.getUsername();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }
}
