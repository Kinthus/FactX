package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_fake_result extends AppCompatActivity {

    Button btnAgain, btnReport, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_result);

        // Initialize Buttons
        btnAgain = findViewById(R.id.btnAgain);
        btnReport = findViewById(R.id.btnReport);
        btnHome = findViewById(R.id.btnHome);

        // Analyze Again
        btnAgain.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_fake_result.this,
                    activity_news_type.class);

            startActivity(intent);
            finish();

        });

        // Report Problem
        btnReport.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_fake_result.this,
                    activity_report_problem.class);

            startActivity(intent);

        });

        // Back to Home
        btnHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_fake_result.this,
                    activity_news_type.class);

            startActivity(intent);
            finish();

        });

    }
}