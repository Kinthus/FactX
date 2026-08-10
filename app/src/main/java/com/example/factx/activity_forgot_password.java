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

    EditText etEmail;
    Button btnSend;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);


        // ==========================================
        // SEND OTP
        // ==========================================

        btnSend.setOnClickListener(v -> {

            String email =
                    etEmail.getText()
                            .toString()
                            .trim();


            // Email empty
            if (email.isEmpty()) {

                etEmail.setError("Enter Email");
                etEmail.requestFocus();

                return;
            }


            // Email validation
            if (!Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                etEmail.setError("Enter Valid Email");
                etEmail.requestFocus();

                return;
            }


            // Create request

            ForgotPasswordRequest request =
                    new ForgotPasswordRequest(email);


            // API

            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            Call<ForgotPasswordResponse> call =
                    apiService.forgotPassword(request);


            call.enqueue(
                    new Callback<ForgotPasswordResponse>() {

                        @Override
                        public void onResponse(
                                Call<ForgotPasswordResponse> call,
                                Response<ForgotPasswordResponse> response) {

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                ForgotPasswordResponse result =
                                        response.body();


                                if (result.isSuccess()) {

                                    Toast.makeText(
                                            activity_forgot_password.this,
                                            "OTP sent to your email",
                                            Toast.LENGTH_LONG
                                    ).show();


                                    // ==================================
                                    // OPEN OTP PAGE
                                    // ==================================

                                    Intent intent =
                                            new Intent(
                                                    activity_forgot_password.this,
                                                    activity_reset_otp.class
                                            );


                                    // Send email

                                    intent.putExtra(
                                            "email",
                                            email
                                    );


                                    startActivity(intent);

                                    finish();

                                } else {

                                    Toast.makeText(
                                            activity_forgot_password.this,
                                            result.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }

                            } else {

                                Toast.makeText(
                                        activity_forgot_password.this,
                                        "Invalid email address",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }


                        @Override
                        public void onFailure(
                                Call<ForgotPasswordResponse> call,
                                Throwable t) {

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


        // Back

        btnBack.setOnClickListener(v -> finish());
    }
}