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

public class activity_login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView txtForgot, txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtForgot = findViewById(R.id.txtForgot);
        txtRegister = findViewById(R.id.txtRegister);

        // Login Button

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }
        });

        // Register

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(activity_login.this,

                                activity_register.class);

                startActivity(intent);

            }
        });

        // Forgot Password

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(activity_login.this,
                                activity_reset_password.class);

                startActivity(intent);

            }
        });

    }

    // Login Validation

    private void loginUser() {

        String email = etEmail.getText().toString().trim();

        String password = etPassword.getText().toString().trim();

        // Email Empty

        if(email.isEmpty()){

            etEmail.setError("Enter Email");

            etEmail.requestFocus();

            return;

        }

        // Email Format

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            etEmail.setError("Enter Valid Email");

            etEmail.requestFocus();

            return;

        }

        // Password Empty

        if(password.isEmpty()){

            etPassword.setError("Enter Password");

            etPassword.requestFocus();

            return;

        }

        // Password Length

        if(password.length() < 6){

            etPassword.setError("Password must be at least 6 characters");

            etPassword.requestFocus();

            return;

        }

        /*
         ==========================================
         DATABASE LOGIN WILL COME HERE
         ==========================================

         FastAPI

         POST /login

         Email

         Password

         */


    }

}