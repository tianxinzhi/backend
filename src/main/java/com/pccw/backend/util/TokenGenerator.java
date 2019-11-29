package com.pccw.backend.util;

import com.pccw.backend.bean.system.LoginBean;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * token generator
 */
public class TokenGenerator {

    private static MessageDigest md = null;
    private static BASE64Encoder encoder = new BASE64Encoder();

    public static String  makeToken (LoginBean bean) {
        String token = (bean.getUsername() + ":"+bean.getPassword()) ;
//        System.out.println("old:"+token);
        try {
            md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(new String(token.getBytes(),"UTF-8").getBytes());
            token =  encoder.encode(md5);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return token;
    }
}
