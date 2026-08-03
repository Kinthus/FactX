package com.example.factx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        btnSubmit.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String subject = etSubject.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Enter Name");
                return;
            }

            if (email.isEmpty()) {
                etEmail.setError("Enter Email");
                return;
            }

            if (subject.isEmpty()) {
                etSubject.setError("Enter Subject");
                return;
            }

            if (message.isEmpty()) {
                etMessage.setError("Enter Message");
                return;
            }

            Toast.makeText(
                    activity_report_problem.this,
                    "Report Submitted Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        });

        btnCancel.setOnClickListener(v -> {

            finish();

        });

    }
}