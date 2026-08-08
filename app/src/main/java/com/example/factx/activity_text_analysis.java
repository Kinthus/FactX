package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class activity_text_analysis extends AppCompatActivity {

    EditText etTitle;
    EditText etNews;

    Button btnAnalyze;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_analysis);

        // Find views
        etTitle = findViewById(R.id.etTitle);
        etNews = findViewById(R.id.etNews);

        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);

        // Analyze Button
        btnAnalyze.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String news = etNews.getText().toString().trim();

            // Check title
            if (title.isEmpty()) {

                etTitle.setError("Enter News Title");
                etTitle.requestFocus();

                return;
            }

            // Check news
            if (news.isEmpty()) {

                etNews.setError("Enter News Content");
                etNews.requestFocus();

                return;
            }

            // --------------------------------
            // DEMO RESULT
            // AI MODEL IS NOT CONNECTED YET
            // --------------------------------

            String lowerNews = news.toLowerCase();

            String resultType;

            if (lowerNews.contains("fake")
                    || lowerNews.contains("hoax")
                    || lowerNews.contains("rumor")
                    || lowerNews.contains("rumour")
                    || lowerNews.contains("shocking")
                    || lowerNews.contains("miracle cure")
                    || lowerNews.contains("secret cure")
                    || lowerNews.contains("100% guaranteed")) {

                resultType = "fake";

            } else {

                resultType = "real";
            }

            // Open Loading Screen
            Intent intent = new Intent(
                    activity_text_analysis.this,
                    activity_loading.class
            );

            intent.putExtra("resultType", resultType);

            startActivity(intent);
        });

        // Clear Button
        btnClear.setOnClickListener(v -> {

            etTitle.setText("");
            etNews.setText("");

            etTitle.requestFocus();
        });
    }
}