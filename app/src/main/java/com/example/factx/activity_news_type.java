package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class activity_news_type extends AppCompatActivity {

    LinearLayout cardText, cardImage, cardMulti;
    Button btnLogout, btnHistory;

    // Logged-in user's email
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_type);

        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email = getIntent().getStringExtra("email");


        // ==========================================
        // INITIALIZE VIEWS
        // ==========================================

        cardText = findViewById(R.id.cardText);
        cardImage = findViewById(R.id.cardImage);
        cardMulti = findViewById(R.id.cardMulti);

        btnLogout = findViewById(R.id.btnLogout);
        btnHistory = findViewById(R.id.btnHistory);


        // ==========================================
        // OPEN TEXT ANALYSIS
        // ==========================================

        cardText.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_text_analysis.class
            );

            // Send user's email
            intent.putExtra("email", email);

            startActivity(intent);
        });


        // ==========================================
        // OPEN IMAGE ANALYSIS
        // ==========================================

        cardImage.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_image_analysis.class
            );

            // Send user's email
            intent.putExtra("email", email);

            startActivity(intent);
        });


        // ==========================================
        // OPEN MULTIMODAL ANALYSIS
        // ==========================================

        cardMulti.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_multimodal.class
            );

            // Send user's email
            intent.putExtra("email", email);

            startActivity(intent);
        });


        // ==========================================
        // OPEN RESULTS HISTORY
        // ==========================================

        btnHistory.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_History.class
            );

            // Send user's email to History
            intent.putExtra("email", email);

            startActivity(intent);
        });


        // ==========================================
        // LOGOUT
        // ==========================================

        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_news_type.this,
                    activity_logout.class
            );

            startActivity(intent);
        });

    }
}