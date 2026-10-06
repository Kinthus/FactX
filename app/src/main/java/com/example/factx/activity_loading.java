package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_loading extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtLoadingTitle;
    TextView txtLoadingSub;
    TextView txtStatus;
    TextView txtStep;

    String resultType;

    // Logged-in user's email
    String email;

    // Analysis type
    String analysisType;

    // Confidence
    double confidence;

    // News data
    String title;
    String news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);


        // ==========================================
        // FIND VIEWS
        // ==========================================

        progressBar = findViewById(R.id.progressBar);

        txtLoadingTitle = findViewById(R.id.txtLoadingTitle);

        txtLoadingSub = findViewById(R.id.txtLoadingSub);

        txtStatus = findViewById(R.id.txtStatus);

        txtStep = findViewById(R.id.txtStep);


        // ==========================================
        // GET DATA FROM PREVIOUS SCREEN
        // ==========================================

        resultType =
                getIntent().getStringExtra("resultType");


        email =
                getIntent().getStringExtra("email");


        analysisType =
                getIntent().getStringExtra("analysis_type");


        // ==========================================
        // GET NEWS DATA
        // ==========================================

        title =
                getIntent().getStringExtra("title");


        news =
                getIntent().getStringExtra("news");


        // ==========================================
        // DEFAULT VALUES
        // ==========================================

        if (resultType == null) {

            resultType = "real";
        }


        if (analysisType == null) {

            analysisType = "image";
        }


        if (title == null) {

            title = "";
        }


        if (news == null) {

            news = "";
        }


        // ==========================================
        // TEMPORARY CONFIDENCE
        // ==========================================

        confidence = 95.0;


        // ==========================================
        // LOADING STATUS
        // ==========================================

        txtStatus.setText("Analyzing...");

        txtStep.setText(
                "Checking Text • Image • AI Model"
        );


        // ==========================================
        // FIRST DELAY
        // ==========================================

        new Handler(
                Looper.getMainLooper()
        ).postDelayed(() -> {

            txtStatus.setText(
                    "Analysis Complete"
            );

            txtStep.setText(
                    "Preparing Result..."
            );


            // ==========================================
            // SECOND DELAY
            // ==========================================

            new Handler(
                    Looper.getMainLooper()
            ).postDelayed(() -> {


                // ==========================================
                // DETERMINE HISTORY TYPE
                // ==========================================

                String historyType;

                if (
                        analysisType.equalsIgnoreCase(
                                "multimodal"
                        )
                ) {

                    historyType =
                            "Multimodal News";

                } else {

                    historyType =
                            "Image News";
                }


                // ==========================================
                // PREPARE NEWS CONTENT
                // ==========================================

                String historyNews;


                if (
                        !title.trim().isEmpty()
                                || !news.trim().isEmpty()
                ) {

                    historyNews =
                            (title + " " + news).trim();

                } else {

                    historyNews =
                            "Image selected for analysis.";
                }


                // ==========================================
                // PREPARE EXPLANATION
                // ==========================================

                String explanation;


                if (
                        analysisType.equalsIgnoreCase(
                                "multimodal"
                        )
                ) {

                    explanation =
                            "Multimodal analysis result. "
                                    + "Text and image were analyzed together.";

                } else {

                    explanation =
                            "Image analysis was performed. "
                                    + "No text-based explanation was provided.";
                }


                // ==========================================
                // DATE AND TIME
                // ==========================================

                String historyDate =
                        new SimpleDateFormat(
                                "dd MMM yyyy, hh:mm a",
                                Locale.getDefault()
                        ).format(new Date());


                // ==========================================
                // SAVE IMAGE / MULTIMODAL HISTORY
                // ==========================================

                HistoryStorage.saveHistory(
                        activity_loading.this,
                        email,
                        historyType,
                        historyNews,
                        explanation,
                        resultType.toUpperCase(),
                        String.format(
                                Locale.getDefault(),
                                "%.2f%%",
                                confidence
                        ),
                        historyDate
                );


                // ==========================================
                // CREATE RESULT INTENT
                // ==========================================

                Intent intent;


                if (
                        resultType.equalsIgnoreCase("fake")
                ) {

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


                // ==========================================
                // SEND RESULT DATA
                // ==========================================

                intent.putExtra(
                        "prediction",
                        resultType.toUpperCase()
                );


                intent.putExtra(
                        "confidence",
                        confidence
                );


                intent.putExtra(
                        "analysis_type",
                        analysisType
                );


                // ==========================================
                // SEND NEWS DATA
                // ==========================================

                intent.putExtra(
                        "title",
                        title
                );


                intent.putExtra(
                        "news",
                        news
                );


                // ==========================================
                // SEND USER EMAIL
                // ==========================================

                intent.putExtra(
                        "email",
                        email
                );


                // ==========================================
                // OPEN RESULT
                // ==========================================

                startActivity(intent);

                finish();


            }, 500);


        }, 2000);

    }
}