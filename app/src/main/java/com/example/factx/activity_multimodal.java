package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class activity_multimodal extends AppCompatActivity {

    EditText etTitle;
    EditText etNews;

    ImageView imgPreview;

    Button btnSelectImage;
    Button btnAnalyze;
    Button btnClear;

    Uri imageUri = null;


    // ==========================================
    // Image Picker
    // ==========================================

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            imageUri = uri;

                            imgPreview.setImageURI(imageUri);

                            Toast.makeText(
                                    activity_multimodal.this,
                                    "Image selected successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_multimodal
        );


        // ==========================================
        // Find Views
        // ==========================================

        etTitle =
                findViewById(R.id.etTitle);

        etNews =
                findViewById(R.id.etNews);

        imgPreview =
                findViewById(R.id.imgPreview);

        btnSelectImage =
                findViewById(R.id.btnSelectImage);

        btnAnalyze =
                findViewById(R.id.btnAnalyze);

        btnClear =
                findViewById(R.id.btnClear);


        // ==========================================
        // Select Image
        // ==========================================

        btnSelectImage.setOnClickListener(v -> {

            imagePickerLauncher.launch("image/*");

        });


        // ==========================================
        // Analyze Multimodal
        // ==========================================

        btnAnalyze.setOnClickListener(v -> {

            String title =
                    etTitle.getText()
                            .toString()
                            .trim();

            String news =
                    etNews.getText()
                            .toString()
                            .trim();


            // At least text OR image
            if (news.isEmpty()
                    && imageUri == null) {

                Toast.makeText(
                        activity_multimodal.this,
                        "Please enter News Content or select an Image",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }


            // ======================================
            // Dummy Result
            // AI NOT CONNECTED YET
            // ======================================

            String resultType;

            if (System.currentTimeMillis() % 2 == 0) {

                resultType = "real";

            } else {

                resultType = "fake";
            }


            // ======================================
            // Loading Screen
            // ======================================

            Intent intent = new Intent(
                    activity_multimodal.this,
                    activity_loading.class
            );

            intent.putExtra(
                    "resultType",
                    resultType
            );

            intent.putExtra(
                    "analysis_type",
                    "multimodal"
            );

            startActivity(intent);

        });


        // ==========================================
        // Clear
        // ==========================================

        btnClear.setOnClickListener(v -> {

            etTitle.setText("");

            etNews.setText("");

            imageUri = null;

            imgPreview.setImageResource(
                    R.drawable.logo
            );

        });

    }
}