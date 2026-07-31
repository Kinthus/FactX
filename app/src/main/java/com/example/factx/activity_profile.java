package com.example.factx;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.factx.model.ProfileUpdateRequest;
import com.example.factx.model.RegisterResponse;

public class activity_profile extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etDob;
    Spinner spGender;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);

        String email = getIntent().getStringExtra("email");

        Toast.makeText(this,
                "Logged in as: " + email,
                Toast.LENGTH_LONG).show();

        spGender = findViewById(R.id.spGender);
        btnSave = findViewById(R.id.btnSave);

        String[] gender = {
                "Male",
                "Female",
                "Other"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        gender
                );

        spGender.setAdapter(adapter);

        if (email != null) {
            loadProfile(email);
        }

        btnSave.setOnClickListener(view -> {

            String fullName = etName.getText().toString().trim();
            String userEmail = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String selectedGender = spGender.getSelectedItem().toString();

            ProfileUpdateRequest request = new ProfileUpdateRequest(
                    fullName,
                    userEmail,
                    phone,
                    dob,
                    selectedGender
            );

            ApiService apiService =
                    RetrofitClient.getClient().create(ApiService.class);

            Call<RegisterResponse> call = apiService.updateProfile(request);

            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call,
                                       Response<RegisterResponse> response) {

                    Toast.makeText(activity_profile.this,
                            "HTTP Code: " + response.code(),
                            Toast.LENGTH_LONG).show();

                    if (response.isSuccessful() && response.body() != null) {

                        Toast.makeText(activity_profile.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(activity_profile.this,
                                "Failed to update profile",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    Toast.makeText(activity_profile.this,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        });

    }

    private void loadProfile(String email) {

        ApiService apiService =
                RetrofitClient.getClient().create(ApiService.class);

        Call<ProfileResponse> call = apiService.getProfile(email);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call,
                                   Response<ProfileResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ProfileResponse profile = response.body();

                    etName.setText(profile.getFullname());
                    etEmail.setText(profile.getEmail());
                    etPhone.setText(profile.getPhone());
                    etDob.setText(profile.getDob());

                    if (profile.getGender() != null) {

                        ArrayAdapter adapter =
                                (ArrayAdapter) spGender.getAdapter();

                        int position = adapter.getPosition(profile.getGender());

                        if (position >= 0) {
                            spGender.setSelection(position);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

                Toast.makeText(activity_profile.this,
                        "Failed to load profile",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}