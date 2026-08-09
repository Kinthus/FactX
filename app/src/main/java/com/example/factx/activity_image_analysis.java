package com.example.factx;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.content.pm.PackageManager;
>>>>>>> 64c0247 (analysis update 89)
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class activity_image_analysis extends AppCompatActivity {

    ImageView imgPreview;

    Button btnGallery;
    Button btnCamera;
    Button btnAnalyzeImage;
    Button btnClearImage;
<<<<<<< HEAD

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
=======

    Uri imageUri = null;


    // ==========================================
    // Gallery Picker
    // ==========================================

    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            imageUri = uri;

                            imgPreview.setImageURI(imageUri);

                            Toast.makeText(
                                    activity_image_analysis.this,
                                    "Image selected successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );




    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            // Camera thumbnail
                            android.graphics.Bitmap bitmap =
                                    (android.graphics.Bitmap)
                                            result.getData()
                                                    .getExtras()
                                                    .get("data");

                            if (bitmap != null) {

                                imgPreview.setImageBitmap(bitmap);

                                // We use bitmap as selected image
                                imageUri = null;

                                Toast.makeText(
                                        activity_image_analysis.this,
                                        "Photo captured successfully",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }
            );

>>>>>>> 64c0247 (analysis update 89)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
<<<<<<< HEAD

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
=======

        setContentView(
                R.layout.activity_image_analysis
        );




        imgPreview =
                findViewById(R.id.imgPreview);

        btnGallery =
                findViewById(R.id.btnGallery);

        btnCamera =
                findViewById(R.id.btnCamera);

        btnAnalyzeImage =
                findViewById(R.id.btnAnalyzeImage);

        btnClearImage =
                findViewById(R.id.btnClearImage);




        btnGallery.setOnClickListener(v -> {

            galleryLauncher.launch("image/*");
>>>>>>> 64c0247 (analysis update 89)

        });


<<<<<<< HEAD
        // ==========================================
        // CAMERA
        // ==========================================
=======

>>>>>>> 64c0247 (analysis update 89)

        btnCamera.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(
                    activity_image_analysis.this,
                    android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        activity_image_analysis.this,
                        new String[]{
                                android.Manifest.permission.CAMERA
                        },
                        100
                );

            } else {

                openCamera();
            }

        });


<<<<<<< HEAD
        // ==========================================
        // ANALYZE IMAGE
        // ==========================================

        btnAnalyzeImage.setOnClickListener(v -> {

            if (imageUri == null) {

                Toast.makeText(
                        activity_image_analysis.this,
                        "Please Select Image",
                        Toast.LENGTH_SHORT
=======


        btnAnalyzeImage.setOnClickListener(v -> {

            if (imageUri == null
                    && imgPreview.getDrawable() == null) {

                Toast.makeText(
                        activity_image_analysis.this,
                        "Please select or capture an image",
                        Toast.LENGTH_LONG
>>>>>>> 64c0247 (analysis update 89)
                ).show();

                return;
            }

<<<<<<< HEAD
            // Dummy result for now
            String resultType = "fake";
=======

            // Dummy result
            String resultType;

            if (System.currentTimeMillis() % 2 == 0) {
                resultType = "real";
            } else {
                resultType = "fake";
            }
>>>>>>> 64c0247 (analysis update 89)

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
<<<<<<< HEAD
        // CLEAR IMAGE
=======
        // Clear Image
>>>>>>> 64c0247 (analysis update 89)
        // ==========================================

        btnClearImage.setOnClickListener(v -> {

            imageUri = null;

            imgPreview.setImageResource(
                    android.R.color.transparent
            );

        });

    }
<<<<<<< HEAD
=======


    // ==========================================
    // Open Camera
    // ==========================================

    private void openCamera() {

        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        if (intent.resolveActivity(
                getPackageManager()
        ) != null) {

            cameraLauncher.launch(intent);

        } else {

            Toast.makeText(
                    activity_image_analysis.this,
                    "Camera is not available",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
>>>>>>> 64c0247 (analysis update 89)
}