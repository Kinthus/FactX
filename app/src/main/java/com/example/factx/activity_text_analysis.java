package com.example.factx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_text_analysis extends AppCompatActivity {

    EditText etTitle, etNews;
    Button btnAnalyze, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_analysis);

        etTitle = findViewById(R.id.etTitle);
        etNews = findViewById(R.id.etNews);

        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);

        btnClear.setOnClickListener(v -> {
            etTitle.setText("");
            etNews.setText("");
        });

        btnAnalyze.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String news = etNews.getText().toString().trim();

            if(title.isEmpty()){
                etTitle.setError("Enter News Title");
                etTitle.requestFocus();
                return;
            }

            if(news.isEmpty()){
                etNews.setError("Enter News Content");
                etNews.requestFocus();
                return;
            }

            Toast.makeText(activity_text_analysis.this,
                    "Analyzing...",
                    Toast.LENGTH_SHORT).show();

        });

    }
}