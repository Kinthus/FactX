package com.example.factx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class activity_logout extends AppCompatActivity {

    Button btnLogout;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logout);

        btnLogout = findViewById(R.id.btnLogout);
        btnCancel = findViewById(R.id.btnCancel);



        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(
                    activity_logout.this,
                    activity_login.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

        });




        btnCancel.setOnClickListener(v -> {

            finish();

        });

    }
}