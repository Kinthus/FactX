package com.example.factx.model;

public class ReportRequest {

    private String name;
    private String email;
    private String subject;
    private String message;

    public ReportRequest(
            String name,
            String email,
            String subject,
            String message
    ) {

        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}