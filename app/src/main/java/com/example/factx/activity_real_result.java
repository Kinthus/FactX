package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_real_result extends AppCompatActivity {

    Button btnAgain, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_result);

        btnAgain = findViewById(R.id.btnAgain);
        btnHome = findViewById(R.id.btnHome);

        // Analyze Again
        btnAgain.setOnClickListener(v -> {
            Intent intent = new Intent(
                    activity_real_result.this,
                    activity_news_type.class);

            startActivity(intent);
            finish();

        });

        // Back to Home
        btnHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_real_result.this,
                    activity_news_type.class);

            startActivity(intent);
            finish();

        });

    }
}