package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_logout extends AppCompatActivity {

    Button btnLogout, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        btnLogout = findViewById(R.id.btnLogout);
        btnCancel = findViewById(R.id.btnCancel);

        // Logout
        btnLogout.setOnClickListener(v -> {

            Toast.makeText(
                    activity_logout.this,
                    "Logged Out Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    activity_logout.this,
                    activity_login.class);   // உங்கள் Login Activity name வேறு என்றால் அதை மாற்றுங்கள்

            startActivity(intent);
            finish();

        });

        // Cancel
        btnCancel.setOnClickListener(v -> {

            finish();

        });

    }
}