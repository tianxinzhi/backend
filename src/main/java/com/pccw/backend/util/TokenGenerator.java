package com.pccw.backend.util;

import com.pccw.backend.bean.system.LoginBean;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * token generator
 */
public class TokenGenerator {

    private static MessageDigest md = null;
    private static BASE64Encoder encoder = new BASE64Encoder();

    public static String  makeToken (LoginBean bean) {
        String token = (bean.getAccountName() + ":"+bean.getPassword()) ;
//        System.out.println("old:"+token);
        try {
            md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());

            token =  encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return token;
    }
}
