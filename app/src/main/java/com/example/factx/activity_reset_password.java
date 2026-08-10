package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_reset_password extends AppCompatActivity {

    EditText etPassword;
    EditText etConfirmPassword;

    Button btnReset;
    TextView txtBackLogin;

    String email;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);


        // ==========================================
        // FIND VIEWS
        // ==========================================

        etPassword =
                findViewById(R.id.etPassword);

        etConfirmPassword =
                findViewById(R.id.etConfirmPassword);

        btnReset =
                findViewById(R.id.btnReset);

        txtBackLogin =
                findViewById(R.id.txtBackLogin);


        // ==========================================
        // GET EMAIL AND OTP
        // ==========================================

        email =
                getIntent().getStringExtra("email");

        otp =
                getIntent().getStringExtra("otp");


        // ==========================================
        // CHECK EMAIL
        // ==========================================

        if (email == null || email.isEmpty()) {

            Toast.makeText(
                    activity_reset_password.this,
                    "Email not found. Please try again.",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }


        // ==========================================
        // CHECK OTP
        // ==========================================

        if (otp == null || otp.isEmpty()) {

            Toast.makeText(
                    activity_reset_password.this,
                    "OTP not found. Please try again.",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }


        // ==========================================
        // RESET PASSWORD
        // ==========================================

        btnReset.setOnClickListener(v -> {

            String password =
                    etPassword.getText()
                            .toString()
                            .trim();


            String confirmPassword =
                    etConfirmPassword.getText()
                            .toString()
                            .trim();


            // ======================================
            // PASSWORD EMPTY
            // ======================================

            if (password.isEmpty()) {

                etPassword.setError(
                        "Enter new password"
                );

                etPassword.requestFocus();

                return;
            }


            // ======================================
            // PASSWORD LENGTH
            // ======================================

            if (password.length() < 8) {

                etPassword.setError(
                        "Password must contain at least 8 characters"
                );

                etPassword.requestFocus();

                return;
            }


            // ======================================
            // CONFIRM PASSWORD EMPTY
            // ======================================

            if (confirmPassword.isEmpty()) {

                etConfirmPassword.setError(
                        "Confirm your password"
                );

                etConfirmPassword.requestFocus();

                return;
            }


            // ======================================
            // PASSWORD MATCH
            // ======================================

            if (!password.equals(confirmPassword)) {

                etConfirmPassword.setError(
                        "Passwords do not match"
                );

                etConfirmPassword.requestFocus();

                return;
            }


            // ======================================
            // API SERVICE
            // ==========================================

            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            // ==========================================
            // RESET PASSWORD API
            // ==========================================

            Call<ResetPasswordResponse> call =
                    apiService.resetPassword(
                            email,
                            otp,
                            password
                    );


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


                                // ==================================
                                // SUCCESS
                                // ==================================

                                if (result.isSuccess()) {

                                    Intent intent =
                                            new Intent(
                                                    activity_reset_password.this,
                                                    activity_login.class
                                            );


                                    intent.setFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK
                                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
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


        // ==========================================
        // BACK TO LOGIN
        // ==========================================

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
}