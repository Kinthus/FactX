package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class activity_news_type extends AppCompatActivity {

    // ==========================================
    // VIEWS
    // ==========================================

    LinearLayout cardText;
    LinearLayout cardImage;
    LinearLayout cardMulti;

    Button btnLogout;
    Button btnHistory;


    // ==========================================
    // USER EMAIL
    // ==========================================

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_type);


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email =
                getIntent().getStringExtra("email");


        // ==========================================
        // FIND VIEWS
        // ==========================================

        cardText =
                findViewById(R.id.cardText);

        cardImage =
                findViewById(R.id.cardImage);

        cardMulti =
                findViewById(R.id.cardMulti);

        btnLogout =
                findViewById(R.id.btnLogout);

        btnHistory =
                findViewById(R.id.btnHistory);


        // ==========================================
        // TEXT NEWS
        // ==========================================

        cardText.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_news_type.this,
                            activity_text_analysis.class
                    );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);
        });


        // ==========================================
        // IMAGE NEWS
        // ==========================================

        cardImage.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_news_type.this,
                            activity_image_analysis.class
                    );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);
        });


        // ==========================================
        // MULTIMODAL NEWS
        // ==========================================

        cardMulti.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_news_type.this,
                            activity_multimodal.class
                    );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);
        });


        // ==========================================
        // VIEW RESULTS HISTORY
        // ==========================================

        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_news_type.this,
                            activity_History.class
                    );

            intent.putExtra(
                    "email",
                    email
            );

            startActivity(intent);
        });


        // ==========================================
        // LOGOUT BUTTON
        // ==========================================

        btnLogout.setOnClickListener(v -> {

            showLogoutDialog();

        });


        // ==========================================
        // ANDROID BACK BUTTON
        // ==========================================

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        showLogoutDialog();
                    }
                }
        );
    }


    // ==========================================
    // LOGOUT CONFIRMATION DIALOG
    // ==========================================

    private void showLogoutDialog() {

        new AlertDialog.Builder(this)

                .setTitle("Logout")

                .setMessage(
                        "Do you want to logout?"
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .setPositiveButton(
                        "Yes",
                        (dialog, which) -> {

                            logoutUser();

                        }
                )

                .show();
    }


    // ==========================================
    // LOGOUT USER
    // ==========================================

    private void logoutUser() {

        Intent intent =
                new Intent(
                        activity_news_type.this,
                        activity_login.class
                );


        // ==========================================
        // CLEAR PREVIOUS ACTIVITIES
        // ==========================================

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );


        // ==========================================
        // OPEN LOGIN PAGE
        // ==========================================

        startActivity(intent);

        finish();
    }
}