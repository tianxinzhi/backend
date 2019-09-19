package com.pccw.backend.util;

import com.pccw.backend.bean.masterfile_account.LoginBean;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * token generator
 */
public class TokenGenerator {

    private static MessageDigest md = null;
    private static BASE64Encoder encoder = new BASE64Encoder();

//      public static void main(String[] args) {
//            String old = TokenGenerator.makeToken("admin","admin");
//        System.out.println(old);
//        //encoder.
//    }

    public static String  makeToken (LoginBean bean) {
        String token = (bean.getAccountName() + ":"+bean.getPassword()) ;
        System.out.println("old:"+token);
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
