package com.example.factx.model;

public class ResetPasswordRequest {

    private String email;
    private String otp;
    private String new_password;

    public ResetPasswordRequest(
            String email,
            String otp,
            String new_password
    ) {
        this.email = email;
        this.otp = otp;
        this.new_password = new_password;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public String getNew_password() {
        return new_password;
    }
}