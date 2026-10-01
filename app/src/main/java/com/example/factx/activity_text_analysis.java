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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_analysis);


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

            String title =
                    etTitle.getText()
                            .toString()
                            .trim();

            String news =
                    etNews.getText()
                            .toString()
                            .trim();

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

            TextAnalysisRequest request =
                    new TextAnalysisRequest(
                            1,
                            (title + " " + news).trim()
                    );


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
                                        // GET SHAP WORDS
                                        // ==================================

                                        StringBuilder importantWords =
                                                new StringBuilder();

                                        if (
                                                result.getImportant_words()
                                                        != null
                                        ) {

                                            for (
                                                    TextAnalysisResponse.ImportantWord word
                                                    : result.getImportant_words()
                                            ) {

                                                importantWords.append(
                                                        word.getWord()
                                                );

                                                importantWords.append(
                                                        " | "
                                                );
                                            }
                                        }


                                        // ==================================
                                        // OPEN RESULT SCREEN
                                        // ==================================

                                        // ==========================================
// OPEN CORRECT RESULT PAGE
// ==========================================

                                        Intent intent;

                                        if (prediction.equalsIgnoreCase("FAKE")) {

                                            // BERT says FAKE
                                            intent = new Intent(
                                                    activity_text_analysis.this,
                                                    activity_fake_result.class
                                            );

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


// ==========================================
// SEND COMMON RESULT DATA
// ==========================================

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


                                @Override
                                public void onFailure(
                                        Call<TextAnalysisResponse> call,
                                        Throwable t
                                ) {

                                    t.printStackTrace();

                                    Toast.makeText(
                                            activity_text_analysis.this,
                                            "ERROR: "
                                                    + t.getClass().getSimpleName()
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