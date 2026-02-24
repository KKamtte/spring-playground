package com.example.springhexagonal.domain;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
