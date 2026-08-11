package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ForgotPasswordRequest;
import com.example.factx.model.ForgotPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_forgot_password extends AppCompatActivity {

    // =====================================================
    // Views
    // =====================================================

    EditText etEmail;

    Button btnSend;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_forgot_password
        );


        // =================================================
        // Find Views
        // =================================================

        etEmail = findViewById(
                R.id.etEmail
        );

        btnSend = findViewById(
                R.id.btnSend
        );

        btnBack = findViewById(
                R.id.btnBack
        );


        // =================================================
        // SEND OTP BUTTON
        // =================================================

        btnSend.setOnClickListener(v -> {

            String email =
                    etEmail.getText()
                            .toString()
                            .trim();


            // =============================================
            // Validate Email
            // =============================================

            if (email.isEmpty()) {

                etEmail.setError(
                        "Enter Email"
                );

                etEmail.requestFocus();

                return;
            }


            if (!Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                etEmail.setError(
                        "Enter Valid Email"
                );

                etEmail.requestFocus();

                return;
            }


            // =============================================
            // Disable button while sending
            // =============================================

            btnSend.setEnabled(false);


            // =============================================
            // Create Request
            // =============================================

            ForgotPasswordRequest request =
                    new ForgotPasswordRequest(
                            email
                    );


            // =============================================
            // Retrofit API
            // =============================================

            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            Call<ForgotPasswordResponse> call =
                    apiService.forgotPassword(
                            request
                    );


            // =============================================
            // API Call
            // =============================================

            call.enqueue(
                    new Callback<ForgotPasswordResponse>() {

                        @Override
                        public void onResponse(
                                Call<ForgotPasswordResponse> call,
                                Response<ForgotPasswordResponse> response
                        ) {

                            // Enable button again
                            btnSend.setEnabled(true);


                            // =================================
                            // Successful Response
                            // =================================

                            if (response.isSuccessful()
                                    && response.body() != null) {


                                ForgotPasswordResponse result =
                                        response.body();


                                // =================================
                                // OTP Successfully Sent
                                // =================================

                                if (result.isSuccess()) {

                                    Toast.makeText(
                                            activity_forgot_password.this,
                                            "OTP sent to your email",
                                            Toast.LENGTH_LONG
                                    ).show();


                                    // =================================
                                    // Open OTP Page
                                    // =================================

                                    Intent intent =
                                            new Intent(
                                                    activity_forgot_password.this,
                                                    activity_reset_otp.class
                                            );


                                    // Send email to OTP page

                                    intent.putExtra(
                                            "email",
                                            email
                                    );


                                    startActivity(intent);


                                    // Close Forgot Password page

                                    finish();


                                } else {

                                    // =================================
                                    // Backend returned failure
                                    // =================================

                                    Toast.makeText(
                                            activity_forgot_password.this,
                                            result.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }


                            } else {

                                // =================================
                                // Server Response Error
                                // =================================

                                Toast.makeText(
                                        activity_forgot_password.this,
                                        "Unable to send OTP. Please try again.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }


                        @Override
                        public void onFailure(
                                Call<ForgotPasswordResponse> call,
                                Throwable t
                        ) {

                            // Enable button again

                            btnSend.setEnabled(true);


                            // =================================
                            // Connection Error
                            // =================================

                            Toast.makeText(
                                    activity_forgot_password.this,
                                    "Connection failed: "
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        });


        // =====================================================
        // BACK BUTTON
        // =====================================================

        btnBack.setOnClickListener(v -> {

            finish();

        });
    }
}