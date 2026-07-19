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
import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.RegisterRequest;
import com.example.factx.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                registerUser(name, email, password);





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
    private void registerUser(String name, String email, String password) {

        ApiService apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);

        RegisterRequest request = new RegisterRequest(name, email, password);

        apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(activity_register.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(activity_register.this, activity_login.class));
                    finish();

                } else {

                    Toast.makeText(activity_register.this,
                            "Registration Failed",
                            Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Toast.makeText(activity_register.this,
                        "Error : " + t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}