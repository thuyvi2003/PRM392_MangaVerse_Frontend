package com.example.mangaverse.model.auth;

public class UserModel {
    private String id;
    private String name;
    private String phone;
    private String username;
    private String email;

    // Getters and Setters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}