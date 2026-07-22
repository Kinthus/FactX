package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_reset_password extends AppCompatActivity {

    EditText etEmail;
    Button btnSend;
    TextView txtBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.etEmail);
        btnSend = findViewById(R.id.btnSend);
        txtBackLogin = findViewById(R.id.txtBackLogin);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    etEmail.setError("Enter Email");
                    etEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Enter Valid Email");
                    etEmail.requestFocus();
                    return;
                }

                Toast.makeText(activity_reset_password.this,
                        "Reset Link Sent Successfully",
                        Toast.LENGTH_SHORT).show();

            }
        });

        txtBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(
                        activity_reset_password.this,
                        activity_login.class));

                finish();
            }
        });

    }
}