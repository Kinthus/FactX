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

public class activity_register extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Name
                if(name.isEmpty()){
                    etName.setError("Enter Full Name");
                    etName.requestFocus();
                    return;
                }

                // Email
                if(email.isEmpty()){
                    etEmail.setError("Enter Email");
                    etEmail.requestFocus();
                    return;
                }

                // Email Format
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Invalid Email Address");
                    etEmail.requestFocus();
                    return;
                }

                // Password
                if(password.isEmpty()){
                    etPassword.setError("Enter Password");
                    etPassword.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    etPassword.setError("Password must be at least 6 characters");
                    etPassword.requestFocus();
                    return;
                }

                // Confirm Password
                if(confirmPassword.isEmpty()){
                    etConfirmPassword.setError("Confirm Password");
                    etConfirmPassword.requestFocus();
                    return;
                }

                // Password Match
                if(!password.equals(confirmPassword)){
                    etConfirmPassword.setError("Password does not match");
                    etConfirmPassword.requestFocus();
                    return;
                }

                /*
                 * Next Step
                 * Here we will call FastAPI Register API
                 * Backend will:
                 * 1. Check email already exists
                 * 2. Send verification email
                 * 3. Save user in MySQL
                 */

                Toast.makeText(activity_register.this,
                        "Validation Successful",
                        Toast.LENGTH_SHORT).show();

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity_register.this,
                        activity_login.class);

                startActivity(intent);
                finish();

            }
        });

    }
}