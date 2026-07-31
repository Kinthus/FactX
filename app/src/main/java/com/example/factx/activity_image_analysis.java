package com.example.factx;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class activity_image_analysis extends AppCompatActivity {

    ImageView imgPreview;
    Button btnGallery, btnCamera, btnAnalyzeImage, btnClearImage;

    Uri imageUri;

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_analysis);

        imgPreview = findViewById(R.id.imgPreview);
        btnGallery = findViewById(R.id.btnGallery);
        btnAnalyzeImage = findViewById(R.id.btnAnalyzeImage);
        btnClearImage = findViewById(R.id.btnClearImage);
        btnCamera = findViewById(R.id.btnCamera);

        imagePickerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if(result.getResultCode() == RESULT_OK &&
                                    result.getData()!=null){

                                imageUri = result.getData().getData();

                                imgPreview.setImageURI(imageUri);
                            }

                        });

        btnGallery.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            imagePickerLauncher.launch(intent);

        });

        btnCamera.setOnClickListener(v -> {

            Toast.makeText(
                    activity_image_analysis.this,
                    "Camera Feature - Implementation 2",
                    Toast.LENGTH_SHORT
            ).show();

        });

        btnAnalyzeImage.setOnClickListener(v -> {

            if(imageUri == null){

                Toast.makeText(this,
                        "Please Select Image",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            Toast.makeText(this,
                    "Analyzing Image...",
                    Toast.LENGTH_SHORT).show();

        });

        btnClearImage.setOnClickListener(v -> {

            imgPreview.setImageResource(android.R.color.transparent);

            imageUri = null;

        });

    }

}