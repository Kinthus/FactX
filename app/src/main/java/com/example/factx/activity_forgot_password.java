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

        setContentView(
                R.layout.activity_forgot_password
        );




        etEmail = findViewById(
                R.id.etEmail
        );

        btnSend = findViewById(
                R.id.btnSend
        );

        btnBack = findViewById(
                R.id.btnBack
        );




        btnSend.setOnClickListener(v -> {

            String email =
                    etEmail.getText()
                            .toString()
                            .trim();




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




            btnSend.setEnabled(false);




            ForgotPasswordRequest request =
                    new ForgotPasswordRequest(
                            email
                    );




            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            Call<ForgotPasswordResponse> call =
                    apiService.forgotPassword(
                            request
                    );




            call.enqueue(
                    new Callback<ForgotPasswordResponse>() {

                        @Override
                        public void onResponse(
                                Call<ForgotPasswordResponse> call,
                                Response<ForgotPasswordResponse> response
                        ) {

                            // Enable button again
                            btnSend.setEnabled(true);




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



                                    Toast.makeText(
                                            activity_forgot_password.this,
                                            result.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }


                            } else {



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




        btnBack.setOnClickListener(v -> {

            finish();

        });
    }
}