package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class activity_multimodal extends AppCompatActivity {

    EditText etTitle, etNews;
    ImageView imgPreview;

    Button btnGallery, btnCamera, btnAnalyze, btnClear;

    Uri imageUri;

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimodal);

        // Initialize Views
        etTitle = findViewById(R.id.etTitle);
        etNews = findViewById(R.id.etNews);

        imgPreview = findViewById(R.id.imgPreview);

        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);
        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);

        // Image Picker
        imagePickerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if (result.getResultCode() == RESULT_OK &&
                                    result.getData() != null) {

                                imageUri = result.getData().getData();
                                imgPreview.setImageURI(imageUri);
                            }
                        });

        // Gallery Button
        btnGallery.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            imagePickerLauncher.launch(intent);

        });

        // Camera Button (Implementation 1)
        btnCamera.setOnClickListener(v -> {

            Toast.makeText(
                    activity_multimodal.this,
                    "Camera Feature will be added in Implementation 2",
                    Toast.LENGTH_SHORT
            ).show();

        });

        // Analyze Button
        btnAnalyze.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String news = etNews.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Enter News Title");
                etTitle.requestFocus();
                return;
            }

            if (news.isEmpty()) {
                etNews.setError("Enter News Content");
                etNews.requestFocus();
                return;
            }

            if (imageUri == null) {
                Toast.makeText(
                        activity_multimodal.this,
                        "Please Select an Image",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // Open Loading Page
            Intent intent = new Intent(
                    activity_multimodal.this,
                    activity_loading.class);

            startActivity(intent);

        });

        // Clear Button
        btnClear.setOnClickListener(v -> {

            etTitle.setText("");
            etNews.setText("");

            imgPreview.setImageResource(
                    android.R.drawable.ic_menu_gallery);

            imageUri = null;

            Toast.makeText(
                    activity_multimodal.this,
                    "Cleared Successfully",
                    Toast.LENGTH_SHORT
            ).show();

        });

    }
}