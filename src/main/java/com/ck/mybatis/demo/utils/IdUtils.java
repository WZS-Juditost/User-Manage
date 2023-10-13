package com.ck.mybatis.demo.utils;

import cn.hutool.core.lang.UUID;

public class IdUtils
{
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

}
