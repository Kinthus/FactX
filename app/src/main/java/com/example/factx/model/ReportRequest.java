package com.example.factx.model;

public class ReportRequest {

    private String email;
    private String subject;
    private String description;

    public ReportRequest(
            String email,
            String subject,
            String description
    ) {

        this.email = email;
        this.subject = subject;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }
}