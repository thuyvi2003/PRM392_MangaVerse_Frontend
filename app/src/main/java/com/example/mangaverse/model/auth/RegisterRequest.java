package com.example.mangaverse.model.auth;

public class RegisterRequest {
    private String name;
    private String phone;
    private String username;
    private String email;
    private String password;

    public RegisterRequest(String name, String phone, String username, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}