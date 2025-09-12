package com.eccomerce.userEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordxd = "1234";
        String password = passwordEncoder.encode(passwordxd);

        System.out.println(password);

}}
