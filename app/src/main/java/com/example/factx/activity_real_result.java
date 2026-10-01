package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_real_result extends AppCompatActivity {

    Button btnAgain, btnHome;

    TextView txtResult;
    TextView txtConfidence;

    ProgressBar progressConfidence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_real_result);


        // ==========================================
        // FIND VIEWS
        // ==========================================

        txtResult =
                findViewById(R.id.txtResult);

        txtConfidence =
                findViewById(R.id.txtConfidence);

        progressConfidence =
                findViewById(R.id.progressConfidence);

        btnAgain =
                findViewById(R.id.btnAgain);

        btnHome =
                findViewById(R.id.btnHome);


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


        // ==========================================
        // SHOW PREDICTION
        // ==========================================

        if (prediction != null) {

            if (prediction.equalsIgnoreCase("REAL")) {

                txtResult.setText(
                        "REAL NEWS"
                );

            } else {

                txtResult.setText(
                        prediction
                );
            }
        }


        // ==========================================
        // SHOW REAL BERT CONFIDENCE
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
        // ANALYZE AGAIN
        // ==========================================

        btnAgain.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_real_result.this,
                            activity_news_type.class
                    );

            startActivity(intent);

            finish();
        });


        // ==========================================
        // BACK TO HOME
        // ==========================================

        btnHome.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_real_result.this,
                            activity_news_type.class
                    );

            startActivity(intent);

            finish();
        });

    }
}