package com.example.factx.model;

public class RegisterRequest {

    private String fullname;
    private String email;
    private String password;

    public RegisterRequest(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}