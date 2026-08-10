package com.example.factx.model;

public class VerifyResetOTPRequest {

    private String email;
    private String otp;

    public VerifyResetOTPRequest(
            String email,
            String otp
    ) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }
}