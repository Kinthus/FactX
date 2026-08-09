package com.example.factx.model;

public class ForgotPasswordResponse {

    private boolean success;
    private String message;

    public ForgotPasswordResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}