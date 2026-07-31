package com.example.factx.model;

public class TextAnalysisRequest {

    private String title;
    private String news;

    public TextAnalysisRequest(String title, String news) {
        this.title = title;
        this.news = news;
    }
}