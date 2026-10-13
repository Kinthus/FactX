
package com.example.factx.model;

import java.util.List;

public class TextAnalysisResponse {

    private String prediction;
    private double confidence;
    private double text_score;
    private int analysis_id;
    private List<String> important_words;
    private String explanation;

    public String getPrediction() {
        return prediction;
    }

    public double getConfidence() {
        return confidence;
    }

    public double getText_score() {
        return text_score;
    }

    public int getAnalysis_id() {
        return analysis_id;
    }

    public List<String> getImportant_words() {
        return important_words;
    }

    public String getExplanation() {
        return explanation;
    }
}
