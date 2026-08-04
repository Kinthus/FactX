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
    Button btnSend, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);

        // Send Reset Link
        btnSend.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Enter Email");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter Valid Email");
                return;
            }

            ForgotPasswordRequest request =
                    new ForgotPasswordRequest(email);

            ApiService apiService =
                    RetrofitClient.getClient().create(ApiService.class);

            Call<ForgotPasswordResponse> call =
                    apiService.forgotPassword(request);

            call.enqueue(new Callback<ForgotPasswordResponse>() {

                @Override
                public void onResponse(Call<ForgotPasswordResponse> call,
                                       Response<ForgotPasswordResponse> response) {

                    if (response.isSuccessful() &&
                            response.body() != null) {

                        Toast.makeText(
                                activity_forgot_password.this,
                                response.body().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    } else {

                        Toast.makeText(
                                activity_forgot_password.this,
                                "Failed to send reset link",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                }

                @Override
                public void onFailure(Call<ForgotPasswordResponse> call,
                                      Throwable t) {

                    Toast.makeText(
                            activity_forgot_password.this,
                            t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                }

            });

        });

        // Back to Login
        btnBack.setOnClickListener(v -> {

            finish();

        });

    }
}