package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.BitmapFactory;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_fake_result extends AppCompatActivity {

    Button btnAgain, btnReport, btnHome;

    TextView txtResult;
    TextView txtConfidence;
    TextView txtReason;
    TextView txtExplanationTitle;

    ImageView imgGradCAM;
    ImageView imgSHAP;

    TextView txtImportantWordsTitle;
    TextView txtImportantWords;
    TextView txtGradCAMTitle;
    TextView txtSHAPTitle;

    String analysisType;

    String gradcamImage;
    String shapImage;

    ProgressBar progressConfidence;

    // Logged-in user's email
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fake_result);

        TextView txtImportantWordsTitle;
        TextView txtImportantWords;
        TextView txtGradCAMTitle;
        TextView txtSHAPTitle;

        String analysisType;

        txtImportantWordsTitle =
                findViewById(R.id.txtImportantWordsTitle);

        txtImportantWords =
                findViewById(R.id.txtImportantWords);

        txtGradCAMTitle =
                findViewById(R.id.txtGradCAMTitle);

        txtSHAPTitle =
                findViewById(R.id.txtSHAPTitle);

        analysisType = getIntent().getStringExtra("analysis_type");


        // ==========================================
        // FIND VIEWS
        // ==========================================

        txtResult =
                findViewById(R.id.txtResult);

        txtConfidence =
                findViewById(R.id.txtConfidence);

        txtReason =
                findViewById(R.id.txtReason);

        progressConfidence =
                findViewById(R.id.progressConfidence);

        btnAgain =
                findViewById(R.id.btnAgain);

        btnReport =
                findViewById(R.id.btnReport);

        btnHome =
                findViewById(R.id.btnHome);

        txtExplanationTitle =
                findViewById(R.id.txtExplanationTitle);

        imgGradCAM = findViewById(R.id.imgGradCAM);
        imgSHAP = findViewById(R.id.imgSHAP);

        txtImportantWordsTitle =
                findViewById(R.id.txtImportantWordsTitle);

        txtImportantWords =
                findViewById(R.id.txtImportantWords);

        analysisType = getIntent().getStringExtra("analysis_type");

        txtGradCAMTitle = findViewById(R.id.txtGradCAMTitle);
        txtSHAPTitle = findViewById(R.id.txtSHAPTitle);

        gradcamImage = getIntent().getStringExtra("gradcam_image");
        String gradcamPath = getIntent().getStringExtra("gradcam_path");
        shapImage = getIntent().getStringExtra("shap_image");


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email =
                getIntent().getStringExtra("email");


        // ==========================================
        // GET BERT RESULT
        // ==========================================

        String prediction =
                getIntent().getStringExtra(
                        "prediction"
                );


        double confidence = getIntent().getDoubleExtra(
                "confidence",
                (double) getIntent().getFloatExtra("confidence", 0.0f)
        );

        android.util.Log.d("FAKE_RESULT",
                "Received confidence = " + confidence);

        android.util.Log.d("FAKE_RESULT",
                "Intent extras = " + getIntent().getExtras());

        android.util.Log.d(
                "FakeResult",
                "Grad-CAM path = " + gradcamPath
        );




        // Show AI Explanation
        String importantWords =
                getIntent().getStringExtra(
                        "important_words"
                );

// Show AI Explanation
        if (importantWords != null &&
                !importantWords.trim().isEmpty()) {

            txtReason.setText(
                    importantWords.replace("|", "\n")
            );

        } else {
            txtReason.setText(
                    "Explanation is not available."
            );
        }

        txtReason.setVisibility(android.view.View.VISIBLE);

        android.util.Log.d("FAKE_RESULT",
                "important_words = " + importantWords);

        android.util.Log.d("FAKE_RESULT",
                "gradcam_image = " +
                        (gradcamImage == null ? "null" : gradcamImage.length()));

        android.util.Log.d("FAKE_RESULT",
                "shap_image = " +
                        (shapImage == null ? "null" : shapImage.length()));




        // ==========================================
        // SHOW PREDICTION
        // ==========================================

        if (prediction != null) {

            if (prediction.equalsIgnoreCase("FAKE")) {

                txtResult.setText(
                        "FAKE NEWS DETECTED"
                );

            } else {

                txtResult.setText(
                        "REAL NEWS"
                );
            }
        }


        // ==========================================
// SHOW CONFIDENCE
// ==========================================

// If confidence comes as decimal (0 to 1),
// convert it into percentage.
        double confidencePercent = confidence;

        if (confidencePercent >= 0.0 && confidencePercent <= 1.0) {
            confidencePercent = confidencePercent * 100.0;
        }

// Keep confidence between 0 and 100
        confidencePercent = Math.max(
                0.0,
                Math.min(100.0, confidencePercent)
        );

// Progress bar
        progressConfidence.setMax(100);

        progressConfidence.setProgress(
                (int) Math.round(confidencePercent)
        );

// Display percentage
        txtConfidence.setText(
                String.format(
                        java.util.Locale.US,
                        "%.2f%%",
                        confidencePercent
                )
        );
        // ==========================================
// SHOW CONFIDENCE
// ==========================================
        try {
            confidencePercent = confidence;

            // If backend sends decimal probability (0 to 1)
            if (confidencePercent >= 0.0 &&
                    confidencePercent <= 1.0) {
                confidencePercent *= 100.0;
            }

            confidencePercent = Math.max(
                    0.0,
                    Math.min(100.0, confidencePercent)
            );

            progressConfidence.setMax(100);
            progressConfidence.setProgress(
                    (int) Math.round(confidencePercent)
            );

            txtConfidence.setText(
                    String.format(
                            java.util.Locale.US,
                            "%.2f%%",
                            confidencePercent
                    )
            );

        } catch (Exception e) {
            txtConfidence.setText("Confidence unavailable");

            android.util.Log.e(
                    "FakeResult",
                    "Confidence display error",
                    e
            );
        }


// ==========================================
// SHOW GRAD-CAM IMAGE
// ==========================================

        try {
            if (gradcamPath != null && !gradcamPath.trim().isEmpty()) {
                Bitmap gradBitmap = BitmapFactory.decodeFile(gradcamPath);

                if (gradBitmap != null) {
                    imgGradCAM.setImageBitmap(gradBitmap);
                } else {
                    imgGradCAM.setImageDrawable(null);
                    android.util.Log.e("FakeResult", "Grad-CAM bitmap is null");
                }
            } else {
                imgGradCAM.setImageDrawable(null);
                android.util.Log.w("FakeResult", "Grad-CAM path is missing");
            }
        } catch (Exception e) {
            imgGradCAM.setImageDrawable(null);
            android.util.Log.e("FakeResult", "Grad-CAM loading error", e);
        }



// ==========================================
// SHOW SHAP IMAGE
// ==========================================
        try {
            if (shapImage != null &&
                    !shapImage.trim().isEmpty()) {

                String imageData = shapImage;

                if (imageData.startsWith("data:") &&
                        imageData.contains(",")) {
                    imageData = imageData.substring(
                            imageData.indexOf(",") + 1
                    );
                }

                byte[] shapBytes = Base64.decode(
                        imageData,
                        Base64.DEFAULT
                );

                Bitmap shapBitmap =
                        BitmapFactory.decodeByteArray(
                                shapBytes,
                                0,
                                shapBytes.length
                        );

                if (shapBitmap != null) {
                    imgSHAP.setImageBitmap(shapBitmap);
                } else {
                    imgSHAP.setImageDrawable(null);
                }

            } else {
                imgSHAP.setImageDrawable(null);
            }

        } catch (Exception e) {
            imgSHAP.setImageDrawable(null);

            android.util.Log.e(
                    "FakeResult",
                    "SHAP image loading error",
                    e
            );
        }


        // ==========================================
        // ANALYZE AGAIN
        // ==========================================

        btnAgain.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_fake_result.this,
                            activity_news_type.class
                    );

            // Send email back
            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);

            finish();
        });


        // ==========================================
        // REPORT PROBLEM
        // ==========================================

        btnReport.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_fake_result.this,
                            activity_report_problem.class
                    );

            // Send email to Report Problem screen
            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);
        });


        // ==========================================
        // BACK TO HOME
        // ==========================================

        btnHome.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_fake_result.this,
                            activity_news_type.class
                    );

            // Send email back
            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);

            finish();
        });

    }
}