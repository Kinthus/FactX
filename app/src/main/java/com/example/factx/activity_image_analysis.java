package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class activity_image_analysis extends AppCompatActivity {

    ImageView imgPreview;

    Button btnGallery;
    Button btnCamera;
    Button btnAnalyzeImage;
    Button btnClearImage;

    Uri imageUri = null;

    // Modern Android Image Picker
    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            imageUri = uri;

                            // Show selected image
                            imgPreview.setImageURI(imageUri);

                            Toast.makeText(
                                    activity_image_analysis.this,
                                    "Image selected successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_analysis);

        // Find Views
        imgPreview = findViewById(R.id.imgPreview);

        btnGallery = findViewById(R.id.btnGallery);

        btnCamera = findViewById(R.id.btnCamera);

        btnAnalyzeImage =
                findViewById(R.id.btnAnalyzeImage);

        btnClearImage =
                findViewById(R.id.btnClearImage);


        // ==========================================
        // GALLERY
        // ==========================================

        btnGallery.setOnClickListener(v -> {

            // Open phone/emulator gallery
            imagePickerLauncher.launch("image/*");

        });


        // ==========================================
        // CAMERA
        // ==========================================

        btnCamera.setOnClickListener(v -> {

            Toast.makeText(
                    activity_image_analysis.this,
                    "Camera Feature - Implementation 2",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ==========================================
        // ANALYZE IMAGE
        // ==========================================

        btnAnalyzeImage.setOnClickListener(v -> {

            if (imageUri == null) {

                Toast.makeText(
                        activity_image_analysis.this,
                        "Please Select Image",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Dummy result for now
            String resultType = "fake";

            Intent intent = new Intent(
                    activity_image_analysis.this,
                    activity_loading.class
            );

            intent.putExtra(
                    "resultType",
                    resultType
            );

            intent.putExtra(
                    "analysis_type",
                    "image"
            );

            startActivity(intent);

        });


        // ==========================================
        // CLEAR IMAGE
        // ==========================================

        btnClearImage.setOnClickListener(v -> {

            imageUri = null;

            imgPreview.setImageResource(
                    android.R.color.transparent
            );

        });

    }
}