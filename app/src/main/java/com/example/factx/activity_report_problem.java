package com.example.factx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ReportRequest;
import com.example.factx.model.ReportResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_report_problem extends AppCompatActivity {

    EditText etName, etEmail, etSubject, etMessage;

    Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_problem);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);


        // ==========================================
        // SUBMIT REPORT
        // ==========================================

        btnSubmit.setOnClickListener(v -> {

            String name =
                    etName.getText().toString().trim();

            String email =
                    etEmail.getText().toString().trim();

            String subject =
                    etSubject.getText().toString().trim();

            String message =
                    etMessage.getText().toString().trim();


            // ======================================
            // VALIDATION
            // ======================================

            if (name.isEmpty()) {

                etName.setError("Enter Name");
                etName.requestFocus();
                return;
            }

            if (email.isEmpty()) {

                etEmail.setError("Enter Email");
                etEmail.requestFocus();
                return;
            }

            if (subject.isEmpty()) {

                etSubject.setError("Enter Subject");
                etSubject.requestFocus();
                return;
            }

            if (message.isEmpty()) {

                etMessage.setError("Enter Message");
                etMessage.requestFocus();
                return;
            }


            // ======================================
            // CREATE REPORT REQUEST
            // ======================================

            ReportRequest request =
                    new ReportRequest(
                            email,
                            subject,
                            message
                    );


            // ======================================
            // API SERVICE
            // ======================================

            ApiService apiService =
                    RetrofitClient
                            .getClient()
                            .create(ApiService.class);


            // ======================================
            // SEND TO BACKEND
            // ======================================

            Call<ReportResponse> call =
                    apiService.submitReport(request);


            call.enqueue(new Callback<ReportResponse>() {

                @Override
                public void onResponse(
                        Call<ReportResponse> call,
                        Response<ReportResponse> response) {


                    if (response.isSuccessful()
                            && response.body() != null) {

                        ReportResponse result =
                                response.body();


                        if (result.isSuccess()) {

                            Toast.makeText(
                                    activity_report_problem.this,
                                    result.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    activity_report_problem.this,
                                    result.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    } else {

                        Toast.makeText(
                                activity_report_problem.this,
                                "Server Error: "
                                        + response.code(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }


                @Override
                public void onFailure(
                        Call<ReportResponse> call,
                        Throwable t) {

                    Toast.makeText(
                            activity_report_problem.this,
                            "Connection Error: "
                                    + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        });


        // ==========================================
        // CANCEL
        // ==========================================

        btnCancel.setOnClickListener(v -> {

            finish();

        });

    }
}