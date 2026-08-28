package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_fake_result extends AppCompatActivity {

    Button btnAgain, btnReport, btnHome;

    TextView txtResult;
    TextView txtConfidence;
    TextView txtReason;
    TextView txtExplanationTitle;

    ProgressBar progressConfidence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fake_result);


        // ==========================================
        // FIND VIEWS
        // ==========================================

        txtResult = findViewById(R.id.txtResult);

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

            startActivity(intent);

            finish();
        });

    }
}