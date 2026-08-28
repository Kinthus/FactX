package com.example.factx.model;

public class TextAnalysisRequest {

    private String text;

    public TextAnalysisRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}