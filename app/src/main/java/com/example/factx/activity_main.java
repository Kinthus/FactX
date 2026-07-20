package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_main extends AppCompatActivity {

    Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(v -> {

            Intent intent = new Intent(activity_main.this, activity_register.class);
            startActivity(intent);

        });
    }
}