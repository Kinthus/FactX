package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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




        btnAnalyze.setOnClickListener(v -> {

            String title =
                    etTitle.getText().toString().trim();

            String news =
                    etNews.getText().toString().trim();

            String url =
                    etUrl.getText().toString().trim();




            if (news.isEmpty() && url.isEmpty()) {

                Toast.makeText(
                        activity_text_analysis.this,
                        "Please enter News Content or News URL",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }




            String textToCheck =
                    (title + " " + news + " " + url)
                            .toLowerCase();

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




            Intent intent = new Intent(
                    activity_text_analysis.this,
                    activity_loading.class
            );

            intent.putExtra(
                    "resultType",
                    resultType
            );

            intent.putExtra(
                    "analysis_type",
                    "text"
            );

            startActivity(intent);
        });




        btnClear.setOnClickListener(v -> {

            etTitle.setText("");
            etNews.setText("");
            etUrl.setText("");

            etTitle.requestFocus();
        });


    }
}
