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
import com.example.factx.model.VerifyResetOTPRequest;
import com.example.factx.model.VerifyResetOTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_reset_otp extends AppCompatActivity {

    EditText etOtp;
    Button btnVerify;
    TextView txtBackLogin;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_otp);

        // =====================================================
        // FIND VIEWS
        // =====================================================

        etOtp = findViewById(R.id.etOtp);

        btnVerify = findViewById(R.id.btnVerify);

        txtBackLogin = findViewById(R.id.txtBackLogin);


        // =====================================================
        // GET EMAIL
        // =====================================================

        email = getIntent().getStringExtra("email");


        // =====================================================
        // CHECK EMAIL
        // =====================================================

        if (email == null || email.isEmpty()) {

            Toast.makeText(
                    activity_reset_otp.this,
                    "Email not found. Please try again.",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }


        // =====================================================
        // VERIFY OTP
        // =====================================================

        btnVerify.setOnClickListener(v -> {

            String otp =
                    etOtp.getText()
                            .toString()
                            .trim();


            // =================================================
            // OTP EMPTY
            // =================================================

            if (otp.isEmpty()) {

                etOtp.setError("Enter OTP");

                etOtp.requestFocus();

                return;
            }


            // =================================================
            // OTP LENGTH
            // =================================================

            if (otp.length() != 6) {

                etOtp.setError(
                        "OTP must contain 6 digits"
                );

                etOtp.requestFocus();

                return;
            }


            // =================================================
            // DISABLE BUTTON
            // =================================================

            btnVerify.setEnabled(false);


            // =================================================
            // CREATE REQUEST
            // =================================================

            VerifyResetOTPRequest request =
                    new VerifyResetOTPRequest(
                            email,
                            otp
                    );


            // =================================================
            // CREATE API SERVICE
            // =================================================

            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            // =================================================
            // VERIFY RESET OTP
            // =================================================

            Call<VerifyResetOTPResponse> call =
                    apiService.verifyResetOtp(
                            request
                    );


            // =================================================
            // API RESPONSE
            // =================================================

            call.enqueue(
                    new Callback<VerifyResetOTPResponse>() {

                        @Override
                        public void onResponse(
                                Call<VerifyResetOTPResponse> call,
                                Response<VerifyResetOTPResponse> response
                        ) {

                            btnVerify.setEnabled(true);


                            if (response.isSuccessful()
                                    && response.body() != null) {

                                VerifyResetOTPResponse result =
                                        response.body();


                                Toast.makeText(
                                        activity_reset_otp.this,
                                        result.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();


                                // =================================
                                // OTP VERIFIED
                                // =================================

                                if (result.isSuccess()) {

                                    Intent intent =
                                            new Intent(
                                                    activity_reset_otp.this,
                                                    activity_reset_password.class
                                            );


                                    // Send email
                                    intent.putExtra(
                                            "email",
                                            email
                                    );


                                    // Send OTP
                                    intent.putExtra(
                                            "otp",
                                            otp
                                    );


                                    startActivity(intent);

                                    finish();
                                }


                            } else {

                                Toast.makeText(
                                        activity_reset_otp.this,
                                        "OTP verification failed. Server Error: "
                                                + response.code(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }


                        @Override
                        public void onFailure(
                                Call<VerifyResetOTPResponse> call,
                                Throwable t
                        ) {

                            btnVerify.setEnabled(true);


                            Toast.makeText(
                                    activity_reset_otp.this,
                                    "Connection failed: "
                                            + t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        });


        // =====================================================
        // BACK TO LOGIN
        // =====================================================

        txtBackLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            activity_reset_otp.this,
                            activity_login.class
                    );

            startActivity(intent);

            finish();
        });
    }
}