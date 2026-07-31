package com.example.factx.model;

public class ProfileUpdateRequest {

    private String fullname;
    private String email;
    private String phone;
    private String dob;
    private String gender;

    public ProfileUpdateRequest(String fullname,
                                String email,
                                String phone,
                                String dob,
                                String gender) {

        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
    }
}