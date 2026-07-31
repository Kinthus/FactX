package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class activity_loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Wait for 3 seconds then open Result Page
        new Handler().postDelayed(() -> {

            Intent intent = new Intent(
                    activity_loading.this,
                    activity_real_result.class);

            startActivity(intent);
            finish();

        }, 3000);

    }
}