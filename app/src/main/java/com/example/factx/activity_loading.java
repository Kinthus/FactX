package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_loading extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtLoadingTitle;
    TextView txtLoadingSub;
    TextView txtStatus;
    TextView txtStep;

    String resultType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);
        txtLoadingTitle = findViewById(R.id.txtLoadingTitle);
        txtLoadingSub = findViewById(R.id.txtLoadingSub);
        txtStatus = findViewById(R.id.txtStatus);
        txtStep = findViewById(R.id.txtStep);

        resultType = getIntent().getStringExtra("resultType");

        if (resultType == null) {
            resultType = "real";
        }

        txtStatus.setText("Analyzing...");
        txtStep.setText("Checking Text • Image • AI Model");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            txtStatus.setText("Analysis Complete");
            txtStep.setText("Preparing Result...");

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                Intent intent;

                if (resultType.equalsIgnoreCase("fake")) {

                    intent = new Intent(
                            activity_loading.this,
                            activity_fake_result.class
                    );

                } else {

                    intent = new Intent(
                            activity_loading.this,
                            activity_real_result.class
                    );
                }

                startActivity(intent);
                finish();

            }, 500);

        }, 2000);
    }
}