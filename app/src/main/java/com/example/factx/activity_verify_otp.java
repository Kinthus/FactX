package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.VerifyOTPRequest;
import com.example.factx.model.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_verify_otp extends AppCompatActivity {

    EditText etEmail, etOTP;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        etEmail = findViewById(R.id.etEmail);
        etOTP = findViewById(R.id.etOTP);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP();
            }
        });
    }

    private void verifyOTP() {

        String email = etEmail.getText().toString().trim();
        String otp = etOTP.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Enter Email");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
            return;
        }

        if (otp.isEmpty()) {
            etOTP.setError("Enter OTP");
            etOTP.requestFocus();
            return;
        }

        VerifyOTPRequest request = new VerifyOTPRequest(email, otp);

        ApiService apiService =
                RetrofitClient.getClient().create(ApiService.class);

        Call<VerifyOTPResponse> call =
                apiService.verifyOtp(request);

        call.enqueue(new Callback<VerifyOTPResponse>() {

            @Override
            public void onResponse(Call<VerifyOTPResponse> call,
                                   Response<VerifyOTPResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    VerifyOTPResponse result = response.body();

                    Toast.makeText(activity_verify_otp.this,
                            result.getMessage(),
                            Toast.LENGTH_LONG).show();

                    if (result.isSuccess()) {

                        Intent intent =
                                new Intent(activity_verify_otp.this,
                                        activity_login.class);

                        startActivity(intent);
                        finish();
                    }

                } else {

                    Toast.makeText(activity_verify_otp.this,
                            "OTP Verification Failed",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call,
                                  Throwable t) {

                Toast.makeText(activity_verify_otp.this,
                        "Connection Error : " + t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }

}