package com.pccw.backend.security;


import com.pccw.backend.bean.StaticVariable;
import com.pccw.backend.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 */
public class SystemAuthFilter extends BasicAuthenticationFilter {

    private static final Logger logger = Logger.getLogger(SystemAuthFilter.class.getName());

    public SystemAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(StaticVariable.TOKEN_HEADER);
        System.out.println("token:" + token);
        // 如果请求头中没有token信息则拦截请求
        if (token == null || token == "" || !token.startsWith(StaticVariable.TOKEN_PREFIX)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            //return;
        } else {
            // 如果请求头中有token，则进行解析，并且设置授权信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
            super.doFilterInternal(request, response, chain);
        }
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        System.out.println("arrive SystemAuthFilter getAuthentication");
        String token = authorization.replace(StaticVariable.TOKEN_PREFIX, "");
        try {
            String username = JwtTokenUtil.getUsernameByToken(token);
            logger.info("checking username:" + username);
            // 通过 token 获取用户具有的角色
            List<SimpleGrantedAuthority> userRolesByToken = JwtTokenUtil.getUserRolesByToken(token);
            if (!StringUtils.isEmpty(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, userRolesByToken);
            }
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception) {
            logger.warning("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }
        return null;
    }
}
