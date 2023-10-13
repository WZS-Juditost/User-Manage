package com.ck.mybatis.demo.utils;


import cn.hutool.core.convert.Convert;
import com.ck.mybatis.demo.constant.SecurityConstants;
import com.ck.mybatis.demo.constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;


public class JwtUtils
{
    public static String secret = TokenConstants.SECRET;

    public static String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    public static Claims parseToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static String getUserName(String token)
    {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    public static String getValue(Claims claims, String key)
    {
        return Convert.toStr(claims.get(key), "");
    }
}
