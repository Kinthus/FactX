package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class activity_text_analysis extends AppCompatActivity {

    EditText etTitle;
    EditText etNews;
    EditText etUrl;

    Button btnAnalyze;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_analysis);

        // Find views
        etTitle = findViewById(R.id.etTitle);
        etNews = findViewById(R.id.etNews);
        etUrl = findViewById(R.id.etUrl);

        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);


        // Analyze Button
        btnAnalyze.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String news = etNews.getText().toString().trim();
            String url = etUrl.getText().toString().trim();

            // Title validation
            if (title.isEmpty()) {

                etTitle.setError("Enter News Title");
                etTitle.requestFocus();
                return;
            }

            // Content validation
            if (news.isEmpty()) {

                etNews.setError("Enter News Content");
                etNews.requestFocus();
                return;
            }

            // URL is OPTIONAL
            // So we do not show an error if URL is empty.

            // -----------------------------
            // DEMO RESULT
            // AI MODEL NOT CONNECTED YET
            // -----------------------------

            String textToCheck =
                    (title + " " + news + " " + url).toLowerCase();

            String resultType;

            if (textToCheck.contains("fake")
                    || textToCheck.contains("hoax")
                    || textToCheck.contains("rumor")
                    || textToCheck.contains("rumour")
                    || textToCheck.contains("shocking")
                    || textToCheck.contains("miracle cure")
                    || textToCheck.contains("secret cure")) {

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
            etUrl.setText("");

            etTitle.requestFocus();
        });
    }
}