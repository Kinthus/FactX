package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ResetPasswordRequest;
import com.example.factx.model.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_reset_password extends AppCompatActivity {

    EditText etPassword;
    EditText etConfirmPassword;

    Button btnReset;
    TextView txtBackLogin;

    String resetToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);

        // Find views
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnReset = findViewById(R.id.btnReset);
        txtBackLogin = findViewById(R.id.txtBackLogin);

        // Get reset token from email link
        getResetToken();

        // Reset password
        btnReset.setOnClickListener(v -> {

            String password =
                    etPassword.getText().toString().trim();

            String confirmPassword =
                    etConfirmPassword.getText().toString().trim();

            // Check token
            if (resetToken == null || resetToken.isEmpty()) {

                Toast.makeText(
                        activity_reset_password.this,
                        "Invalid or missing reset link.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            // Check password
            if (password.isEmpty()) {

                etPassword.setError("Enter new password");
                etPassword.requestFocus();
                return;
            }

            // Minimum 8 characters
            if (password.length() < 8) {

                etPassword.setError(
                        "Password must contain at least 8 characters"
                );

                etPassword.requestFocus();
                return;
            }

            // Check confirmation
            if (confirmPassword.isEmpty()) {

                etConfirmPassword.setError(
                        "Confirm your password"
                );

                etConfirmPassword.requestFocus();
                return;
            }

            // Password mismatch
            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match"
                );

                etConfirmPassword.requestFocus();
                return;
            }

            // Create request
            ResetPasswordRequest request =
                    new ResetPasswordRequest(
                            resetToken,
                            password
                    );

            // Retrofit
            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);

            Call<ResetPasswordResponse> call =
                    apiService.resetPassword(request);

            call.enqueue(
                    new Callback<ResetPasswordResponse>() {

                        @Override
                        public void onResponse(
                                Call<ResetPasswordResponse> call,
                                Response<ResetPasswordResponse> response) {

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                ResetPasswordResponse result =
                                        response.body();

                                Toast.makeText(
                                        activity_reset_password.this,
                                        result.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();

                                if (result.isSuccess()) {

                                    // Go to Login
                                    Intent intent =
                                            new Intent(
                                                    activity_reset_password.this,
                                                    activity_login.class
                                            );

                                    intent.addFlags(
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    );

                                    startActivity(intent);

                                    finish();
                                }

                            } else {

                                Toast.makeText(
                                        activity_reset_password.this,
                                        "Password reset failed.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<ResetPasswordResponse> call,
                                Throwable t) {

                            Toast.makeText(
                                    activity_reset_password.this,
                                    "Connection error: "
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        });

        // Back to Login
        txtBackLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_reset_password.this,
                            activity_login.class
                    );

            startActivity(intent);

            finish();
        });
    }


    // =====================================================
    // Get Reset Token
    // =====================================================

    private void getResetToken() {

        Intent intent = getIntent();

        Uri data = intent.getData();

        if (data != null) {

            resetToken = data.getQueryParameter("token");
        }

        // Also check Intent extra
        if (resetToken == null || resetToken.isEmpty()) {

            resetToken = intent.getStringExtra("token");
        }

        // Debug
        android.util.Log.d(
                "RESET_TOKEN",
                "Intent Data = " + data
        );

        android.util.Log.d(
                "RESET_TOKEN",
                "Received Token = " + resetToken
        );
    }
}