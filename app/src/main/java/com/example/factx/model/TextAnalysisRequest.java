package com.example.factx.model;

public class TextAnalysisRequest {

    private int user_id;
    private String news_text;

    public TextAnalysisRequest(int user_id, String news_text) {
        this.user_id = user_id;
        this.news_text = news_text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNews_text() {
        return news_text;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }
}