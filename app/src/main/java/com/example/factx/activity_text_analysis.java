package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.RetrofitClient;
import com.example.factx.api.ApiService;
import com.example.factx.model.TextAnalysisRequest;
import com.example.factx.model.TextAnalysisResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_text_analysis extends AppCompatActivity {

    EditText etTitle;
    EditText etNews;
    EditText etUrl;

    Button btnAnalyze;
    Button btnClear;

    ApiService apiService;

    // Logged-in user's email
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_analysis);


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email = getIntent().getStringExtra("email");


        // ==========================================
        // FIND VIEWS
        // ==========================================

        etTitle = findViewById(R.id.etTitle);
        etNews = findViewById(R.id.etNews);
        etUrl = findViewById(R.id.etUrl);

        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);


        // ==========================================
        // RETROFIT
        // ==========================================

        apiService = RetrofitClient.getClient()
                .create(ApiService.class);


        // ==========================================
        // ANALYZE BUTTON
        // ==========================================

        btnAnalyze.setOnClickListener(v -> {

            // Get title
            String title =
                    etTitle.getText()
                            .toString()
                            .trim();


            // Get news content
            String news =
                    etNews.getText()
                            .toString()
                            .trim();


            // Get URL
            String url =
                    etUrl.getText()
                            .toString()
                            .trim();


            // ==========================================
            // VALIDATION
            // ==========================================

            if (news.isEmpty() && url.isEmpty()) {

                Toast.makeText(
                        activity_text_analysis.this,
                        "Please enter News Content or News URL",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }


            // ==========================================
            // CREATE REQUEST
            // ==========================================

            String newsText = (title + " " + news).trim();

            if (email == null || email.trim().isEmpty()) {
                Toast.makeText(
                        activity_text_analysis.this,
                        "User email is missing. Please login again.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            TextAnalysisRequest request =
                    new TextAnalysisRequest(email, newsText);


            // ==========================================
            // SEND TO FASTAPI
            // ==========================================

            apiService.analyzeText(request)
                    .enqueue(
                            new Callback<TextAnalysisResponse>() {

                                @Override
                                public void onResponse(
                                        Call<TextAnalysisResponse> call,
                                        Response<TextAnalysisResponse> response
                                ) {

                                    if (
                                            response.isSuccessful()
                                                    && response.body() != null
                                    ) {

                                        TextAnalysisResponse result =
                                                response.body();


                                        // ==================================
                                        // GET BERT RESULT
                                        // ==================================

                                        String prediction =
                                                result.getPrediction();

                                        double confidence =
                                                result.getConfidence();


                                        // ==================================
                                        // GET SHAP IMPORTANT WORDS
                                        // ==================================



                                        StringBuilder importantWords = new StringBuilder();

                                        if (result.getImportant_words() != null) {
                                            for (String word : result.getImportant_words()) {
                                                if (word != null && !word.trim().isEmpty()) {
                                                    if (importantWords.length() > 0) {
                                                        importantWords.append(" | ");
                                                    }
                                                    importantWords.append(word);
                                                }
                                            }
                                        }

                                        String explanation = result.getExplanation();

                                        if (explanation == null || explanation.trim().isEmpty()) {
                                            if (importantWords.length() > 0) {
                                                explanation = "Important words: " + importantWords;
                                            } else {
                                                explanation = "No explanation available.";
                                            }
                                        }


                                        // ==================================
                                        // DATE AND TIME
                                        // ==================================

                                        String historyDate =
                                                new SimpleDateFormat(
                                                        "dd MMM yyyy, hh:mm a",
                                                        Locale.getDefault()
                                                ).format(new Date());


                                        // ==================================
                                        // SAVE TEXT RESULT TO HISTORY
                                        // ==================================

                                        HistoryStorage.saveHistory(
                                                activity_text_analysis.this,
                                                email,
                                                "Text News",
                                                (title + " " + news).trim(),
                                                explanation,
                                                prediction,
                                                String.format(
                                                        Locale.getDefault(),
                                                        "%.2f%%",
                                                        confidence
                                                ),
                                                historyDate
                                        );


                                        // ==================================
                                        // OPEN RESULT SCREEN
                                        // ==================================

                                        Intent intent;


                                        if (
                                                prediction.equalsIgnoreCase(
                                                        "FAKE"
                                                )
                                        ) {

                                            // BERT says FAKE

                                            intent = new Intent(
                                                    activity_text_analysis.this,
                                                    activity_fake_result.class
                                            );


                                            // Send SHAP important words
                                            // to Fake Result screen

                                            intent.putExtra(
                                                    "important_words",
                                                    importantWords.toString()
                                            );

                                        } else {

                                            // BERT says REAL

                                            intent = new Intent(
                                                    activity_text_analysis.this,
                                                    activity_real_result.class
                                            );
                                        }


                                        // ==================================
                                        // SEND COMMON RESULT DATA
                                        // ==================================

                                        intent.putExtra(
                                                "prediction",
                                                prediction
                                        );


                                        intent.putExtra(
                                                "confidence",
                                                confidence
                                        );


                                        intent.putExtra(
                                                "analysis_type",
                                                "text"
                                        );


                                        // ==================================
                                        // SEND LOGGED-IN USER EMAIL
                                        // ==================================

                                        intent.putExtra(
                                                "email",
                                                email
                                        );


                                        // ==================================
                                        // OPEN RESULT ACTIVITY
                                        // ==================================

                                        startActivity(intent);

                                    } else {

                                        Toast.makeText(
                                                activity_text_analysis.this,
                                                "Server Error: "
                                                        + response.code(),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }


                                // ==========================================
                                // API FAILURE
                                // ==========================================

                                @Override
                                public void onFailure(
                                        Call<TextAnalysisResponse> call,
                                        Throwable t
                                ) {

                                    t.printStackTrace();

                                    Toast.makeText(
                                            activity_text_analysis.this,
                                            "ERROR: "
                                                    + t.getClass()
                                                    .getSimpleName()
                                                    + "\n"
                                                    + t.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                    );
        });


        // ==========================================
        // CLEAR BUTTON
        // ==========================================

        btnClear.setOnClickListener(v -> {

            etTitle.setText("");

            etNews.setText("");

            etUrl.setText("");

            etTitle.requestFocus();
        });
    }
}