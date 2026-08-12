package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;

public class activity_news_type extends AppCompatActivity {

    LinearLayout cardText, cardImage, cardMulti;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_type);

        // Initialize Views
        cardText = findViewById(R.id.cardText);
        cardImage = findViewById(R.id.cardImage);
        cardMulti = findViewById(R.id.cardMulti);

        // Open Text Analysis
        cardText.setOnClickListener(v -> {
            Intent intent = new Intent(activity_news_type.this,
                    activity_text_analysis.class);
            startActivity(intent);
        });

        // Open Image Analysis
        cardImage.setOnClickListener(v -> {
            Intent intent = new Intent(activity_news_type.this,
                    activity_image_analysis.class);
            startActivity(intent);
        });

        // Open Multimodal Analysis
        cardMulti.setOnClickListener(v -> {
            Intent intent = new Intent(activity_news_type.this,
                    activity_multimodal.class);
            startActivity(intent);
        });

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_logout.class
            );

            startActivity(intent);

        });
    }
}