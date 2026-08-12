package com.example.factx;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class activity_image_analysis extends AppCompatActivity {

    // ==========================================
    // VIEWS
    // ==========================================

    ImageView imgPreview;

    Button btnGallery;
    Button btnCamera;
    Button btnAnalyzeImage;
    Button btnClearImage;
    Button btnLogout;


    // ==========================================
    // IMAGE DATA
    // ==========================================

    Uri imageUri = null;

    Bitmap cameraBitmap = null;

    boolean imageSelected = false;


    // ==========================================
    // GALLERY PICKER
    // ==========================================

    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            imageUri = uri;

                            cameraBitmap = null;

                            imageSelected = true;

                            imgPreview.setImageURI(imageUri);

                            Toast.makeText(
                                    activity_image_analysis.this,
                                    "Image selected successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );


    // ==========================================
    // CAMERA
    // ==========================================

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            Intent data = result.getData();

                            if (data.getExtras() != null) {

                                Bitmap bitmap =
                                        (Bitmap) data
                                                .getExtras()
                                                .get("data");

                                if (bitmap != null) {

                                    cameraBitmap = bitmap;

                                    imageUri = null;

                                    imageSelected = true;

                                    imgPreview.setImageBitmap(
                                            cameraBitmap
                                    );

                                    Toast.makeText(
                                            activity_image_analysis.this,
                                            "Photo captured successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        }
                    }
            );


    // ==========================================
    // ON CREATE
    // ==========================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_image_analysis
        );


        // ==========================================
        // FIND VIEWS
        // ==========================================

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

        btnLogout = findViewById(R.id.btnLogout);

        // ==========================================
        // GALLERY BUTTON
        // ==========================================

        btnGallery.setOnClickListener(v -> {

            galleryLauncher.launch("image/*");

        });


        // ==========================================
        // CAMERA BUTTON
        // ==========================================

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


        // ==========================================
        // ANALYZE IMAGE BUTTON
        // ==========================================

        btnAnalyzeImage.setOnClickListener(v -> {

            // --------------------------------------
            // CHECK IMAGE SELECTED
            // --------------------------------------

            if (!imageSelected) {

                Toast.makeText(
                        activity_image_analysis.this,
                        "Please select or capture an image first.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }


            // --------------------------------------
            // IMAGE EXISTS
            // --------------------------------------

            Toast.makeText(
                    activity_image_analysis.this,
                    "Analyzing image...",
                    Toast.LENGTH_SHORT
            ).show();


            // --------------------------------------
            // TEMPORARY RESULT
            // --------------------------------------
            //
            // NOTE:
            // This is your existing dummy result logic.
            // Later this can be replaced with your
            // trained CNN/MobileNetV2 API.
            // --------------------------------------

            String resultType;

            if (System.currentTimeMillis() % 2 == 0) {

                resultType = "real";

            } else {

                resultType = "fake";
            }


            // --------------------------------------
            // OPEN LOADING PAGE
            // --------------------------------------

            Intent intent =
                    new Intent(
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


            // Send gallery image URI if available

            if (imageUri != null) {

                intent.putExtra(
                        "imageUri",
                        imageUri.toString()
                );
            }


            startActivity(intent);

        });


        // ==========================================
        // CLEAR IMAGE
        // ==========================================

        btnClearImage.setOnClickListener(v -> {

            // Clear URI

            imageUri = null;


            // Clear camera bitmap

            cameraBitmap = null;


            // Mark image as not selected

            imageSelected = false;


            // Clear preview

            imgPreview.setImageDrawable(null);


            Toast.makeText(
                    activity_image_analysis.this,
                    "Image cleared",
                    Toast.LENGTH_SHORT
            ).show();

        });

    }


    // ==========================================
    // OPEN CAMERA
    // ==========================================

    private void openCamera() {

        Intent intent =
                new Intent(
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
}