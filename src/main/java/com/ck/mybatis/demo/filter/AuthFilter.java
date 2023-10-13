package com.ck.mybatis.demo.filter;

import cn.hutool.core.util.StrUtil;
import com.ck.mybatis.demo.common.Result;
import com.ck.mybatis.demo.config.IgnoreWhiteProperties;
import com.ck.mybatis.demo.constant.TokenConstants;
import com.ck.mybatis.demo.utils.JwtUtils;
import com.ck.mybatis.demo.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class AuthFilter implements Filter, Ordered {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        logger.info("---WebFilter--init--");
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
        logger.info("---WebFilter--destroy--");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String url = httpServletRequest.getRequestURI();

        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = getToken(httpServletRequest);
            if (StrUtil.isBlank(token)) {
                throw new RuntimeException("令牌验证失败");
            }
            String userName = JwtUtils.getUserName(token);
            if (StrUtil.isBlank(userName)) {
                throw new RuntimeException("令牌验证失败");
            }
        } catch (Exception ignored) {
            Result result = Result.fail("401", "令牌验证失败");

            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = convertObjectToJson(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
            return;
        }
        chain.doFilter(request, response);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    /**
     * 获取请求token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        if (StringUtils.isEmpty(token)) {
            //尝试从拼接参数中获取token，这步是为了websocket鉴权
            Object authorization = request.getParameter(TokenConstants.AUTHENTICATION);
            if (authorization != null) {
                token = ((List<?>) authorization).get(0).toString();
            }
        }
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

}
