
package com.example.factx.model;

public class TextAnalysisRequest {

    private String email;
    private String news_text;

    public TextAnalysisRequest(String email, String news_text) {
        this.email = email;
        this.news_text = news_text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNews_text() {
        return news_text;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }
}
