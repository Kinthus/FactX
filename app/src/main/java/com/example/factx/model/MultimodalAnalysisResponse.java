
package com.example.factx.model;

import java.util.List;

public class MultimodalAnalysisResponse {

    private String analysis_type;
    private String prediction;
    private double confidence;
    private String gradcam_image;
    private List<ImportantWord> important_words;

    public String getAnalysis_type() {
        return analysis_type;
    }

    public String getPrediction() {
        return prediction;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getGradcam_image() {
        return gradcam_image;
    }

    public List<ImportantWord> getImportant_words() {
        return important_words;
    }

    public static class ImportantWord {
        private String token;
        private double importance;

        public String getToken() {
            return token;
        }

        public double getImportance() {
            return importance;
        }
    }
}
