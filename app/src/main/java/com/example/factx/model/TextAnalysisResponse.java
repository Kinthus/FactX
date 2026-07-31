package com.example.factx.model;

public class TextAnalysisResponse {

    private boolean success;
    private String prediction;
    private double confidence;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getPrediction() {
        return prediction;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getMessage() {
        return message;
    }
}