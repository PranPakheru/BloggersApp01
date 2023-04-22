package com.myblog.blogapp.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//this class has no use but for my understanding of encoding.
public class EncodePassword {
    public static void main(String[] args){
        PasswordEncoder encodePassword = new BCryptPasswordEncoder();
        System.out.println(encodePassword.encode("pranav123"));
        System.out.println(encodePassword.encode("admin123"));
        //can give anything to check, run only this class.

    }
}
