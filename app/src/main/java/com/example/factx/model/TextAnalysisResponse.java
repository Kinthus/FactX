package com.example.factx.model;

import java.util.List;

public class TextAnalysisResponse {

    private String prediction;
    private double confidence;
    private double fake_probability;
    private double real_probability;
    private List<ImportantWord> important_words;


    public String getPrediction() {
        return prediction;
    }


    public double getConfidence() {
        return confidence;
    }


    public double getFake_probability() {
        return fake_probability;
    }


    public double getReal_probability() {
        return real_probability;
    }


    public List<ImportantWord> getImportant_words() {
        return important_words;
    }


    public static class ImportantWord {

        private String word;
        private double importance;


        public String getWord() {
            return word;
        }


        public double getImportance() {
            return importance;
        }
    }
}