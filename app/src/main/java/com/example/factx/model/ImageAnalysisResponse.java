

package com.example.factx.model;

public class ImageAnalysisResponse {
    private String prediction;
    private float confidence;
    private float fake_probability;
    private float real_probability;
    private String gradcam_image;
    private String shap_image;

    public String getPrediction() {
        return prediction;
    }

    public float getConfidence() {
        return confidence;
    }

    public float getFake_probability() {
        return fake_probability;
    }

    public float getReal_probability() {
        return real_probability;
    }

    public String getGradcam_image() {
        return gradcam_image;
    }

    public String getShap_image() {
        return shap_image;
    }
}
