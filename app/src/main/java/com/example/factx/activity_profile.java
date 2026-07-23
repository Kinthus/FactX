package com.example.factx;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        btnSave.setOnClickListener(view -> {

            Toast.makeText(
                    activity_profile.this,
                    "Profile Saved Successfully",
                    Toast.LENGTH_SHORT
            ).show();

        });

    }
}