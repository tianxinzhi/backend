package com.pccw.backend.util;

import com.pccw.backend.bean.StaticVariable;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sun.security.util.SecurityConstants;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {


    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(StaticVariable.JWT_SECRET_KEY));
    }

    public static String createToken(String username, List<String> roles) {
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", StaticVariable.TOKEN_TYPE)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .claim(StaticVariable.ROLE_CLAIMS, String.join(",", roles))
                //.claim(StaticVariable.ROLE_CLAIMS, String.join(",", roles))
                .setIssuer("SMP")
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + StaticVariable.EXPIRATION * 1000))
                .compact();
        return StaticVariable.TOKEN_PREFIX + tokenPrefix;
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户所有角色
     */
    public static List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token)
                .get(StaticVariable.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }



    public static void main(String[] args) {
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJyb2xlIjoiMjIiLCJpc3MiOiJTTVAiLCJpYXQiOjE1NzQ5OTMxODAsInN1YiI6IjIyIiwiZXhwIjoxNTc0OTk2NzgwfQ.glCEu2MPnY86pAzVUDmwpljDuQGUrNj5GN9Yj91s8Vo";
        System.out.println("userName:"+ JwtTokenUtil.getUsernameByToken(token));
        System.out.println("roles:"+ JwtTokenUtil.getUserRolesByToken(token));
        System.out.println("tokenBody:"+ JwtTokenUtil.getTokenBody(token));
    }
}
