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

    String gradcamImage;
    String shapImage;

    ProgressBar progressConfidence;

    // Logged-in user's email
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fake_result);


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

        gradcamImage = getIntent().getStringExtra("gradcam_image");
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


        double confidence =
                getIntent().getDoubleExtra(
                        "confidence",
                        0.0
                );


        String importantWords =
                getIntent().getStringExtra(
                        "important_words"
                );


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

        int confidenceValue =
                (int) Math.round(confidence);


        progressConfidence.setProgress(
                confidenceValue
        );


        txtConfidence.setText(
                String.format(
                        "%.2f%%",
                        confidence
                )
        );

        if (gradcamImage != null && !gradcamImage.isEmpty()) {
            byte[] gradBytes = Base64.decode(gradcamImage, Base64.DEFAULT);
            Bitmap gradBitmap = BitmapFactory.decodeByteArray(
                    gradBytes, 0, gradBytes.length
            );

            if (gradBitmap != null) {
                imgGradCAM.setImageBitmap(gradBitmap);
            }
        }

        if (shapImage != null && !shapImage.isEmpty()) {
            byte[] shapBytes = Base64.decode(shapImage, Base64.DEFAULT);
            Bitmap shapBitmap = BitmapFactory.decodeByteArray(
                    shapBytes, 0, shapBytes.length
            );

            if (shapBitmap != null) {
                imgSHAP.setImageBitmap(shapBitmap);
            }
        }


        // ==========================================
        // SHOW SHAP EXPLANATION
        // ==========================================

        if (
                importantWords != null
                        && !importantWords.isEmpty()
        ) {

            String[] words =
                    importantWords.split(
                            "\\|"
                    );


            StringBuilder explanation =
                    new StringBuilder();


            explanation.append(
                    "Words that influenced the AI prediction:\n\n"
            );


            for (String word : words) {

                String cleanWord =
                        word.trim();

                if (!cleanWord.isEmpty()) {

                    explanation.append(
                            "• "
                    );

                    explanation.append(
                            cleanWord
                    );

                    explanation.append(
                            "\n"
                    );
                }
            }


            txtReason.setText(
                    explanation.toString()
            );

        } else {

            txtReason.setText(
                    "No explanation available."
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