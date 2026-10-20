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

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.ImageAnalysisResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Base64;
import java.io.File;
import java.io.FileOutputStream;

public class activity_image_analysis extends AppCompatActivity {

    ImageView imgPreview;

    Button btnGallery;
    Button btnAnalyzeImage;
    Button btnClearImage;

    Uri imageUri = null;

    Bitmap cameraBitmap = null;

    boolean imageSelected = false;

    // Logged-in user's email
    String email;


    // ==========================================
    // GALLERY LAUNCHER
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
    // CAMERA LAUNCHER
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


    private String saveGradcamToCache(String base64Image) throws IOException {
        if (base64Image == null || base64Image.trim().isEmpty()) {
            return null;
        }

        String imageData = base64Image.trim();

        if (imageData.startsWith("data:") && imageData.contains(",")) {
            imageData = imageData.substring(imageData.indexOf(",") + 1);
        }

        byte[] imageBytes = Base64.decode(imageData, Base64.DEFAULT);

        File imageFile = new File(getCacheDir(), "factx_image_gradcam.png");

        try (FileOutputStream output = new FileOutputStream(imageFile)) {
            output.write(imageBytes);
        }

        return imageFile.getAbsolutePath();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_image_analysis
        );


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email = getIntent().getStringExtra("email");


        // ==========================================
        // FIND VIEWS
        // ==========================================

        imgPreview =
                findViewById(R.id.imgPreview);

        btnGallery =
                findViewById(R.id.btnGallery);



        btnAnalyzeImage =
                findViewById(R.id.btnAnalyzeImage);

        btnClearImage =
                findViewById(R.id.btnClearImage);


        // ==========================================
        // GALLERY BUTTON
        // ==========================================

        btnGallery.setOnClickListener(v -> {

            galleryLauncher.launch("image/*");

        });


        // ==========================================
        // CAMERA BUTTON
        // ==========================================




        // ==========================================
        // ANALYZE IMAGE BUTTON
        // ==========================================


        btnAnalyzeImage.setOnClickListener(v -> {

            if (!imageSelected) {
                Toast.makeText(
                        activity_image_analysis.this,
                        "Please select or capture an image first.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            byte[] imageBytes;

            try {
                imageBytes = getImageBytes();
            } catch (IOException e) {
                Toast.makeText(
                        activity_image_analysis.this,
                        "Unable to read image: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            if (imageBytes == null || imageBytes.length == 0) {
                Toast.makeText(
                        activity_image_analysis.this,
                        "Unable to read selected image.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            btnAnalyzeImage.setEnabled(false);

            Toast.makeText(
                    activity_image_analysis.this,
                    "Analyzing image...",
                    Toast.LENGTH_SHORT
            ).show();

            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("image/jpeg"),
                    imageBytes
            );

            MultipartBody.Part imagePart =
                    MultipartBody.Part.createFormData(
                            "file",
                            "image.jpg",
                            requestBody
                    );

            ApiService apiService = RetrofitClient
                    .getClient()
                    .create(ApiService.class);

            apiService.analyzeImage(imagePart).enqueue(
                    new Callback<ImageAnalysisResponse>() {

                        @Override
                        public void onResponse(
                                Call<ImageAnalysisResponse> call,
                                Response<ImageAnalysisResponse> response) {

                            btnAnalyzeImage.setEnabled(true);

                            if (!response.isSuccessful()
                                    || response.body() == null) {
                                Toast.makeText(
                                        activity_image_analysis.this,
                                        "Image analysis failed. HTTP "
                                                + response.code(),
                                        Toast.LENGTH_LONG
                                ).show();
                                return;
                            }

                            ImageAnalysisResponse result = response.body();
                            String prediction = result.getPrediction();

                            if (prediction == null
                                    || (!prediction.equalsIgnoreCase("REAL")
                                    && !prediction.equalsIgnoreCase("FAKE"))) {
                                Toast.makeText(
                                        activity_image_analysis.this,
                                        "Invalid prediction received.",
                                        Toast.LENGTH_LONG
                                ).show();
                                return;
                            }

                            Intent intent = new Intent(
                                    activity_image_analysis.this,
                                    activity_loading.class
                            );

                            intent.putExtra(
                                    "resultType",
                                    prediction.toLowerCase(
                                            java.util.Locale.ROOT
                                    )
                            );
                            intent.putExtra("analysis_type", "image");
                            intent.putExtra("email", email);
                            intent.putExtra(
                                    "confidence",
                                    (double) result.getConfidence()
                            );


                            if (prediction.equalsIgnoreCase("FAKE")) {
                                try {
                                    String gradcamPath = saveGradcamToCache(
                                            result.getGradcam_image()
                                    );

                                    android.util.Log.d(
                                            "IMAGE_ANALYSIS",
                                            "Saved Grad-CAM path = " + gradcamPath
                                    );

                                    if (gradcamPath != null) {
                                        intent.putExtra("gradcam_path", gradcamPath);
                                    }

                                } catch (IOException e) {
                                    android.util.Log.e(
                                            "IMAGE_ANALYSIS",
                                            "Failed to save Grad-CAM",
                                            e
                                    );
                                }

                                intent.putExtra(
                                        "shap_image",
                                        result.getShap_image()
                                );
                            }


                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(
                                Call<ImageAnalysisResponse> call,
                                Throwable t) {

                            btnAnalyzeImage.setEnabled(true);

                            Toast.makeText(
                                    activity_image_analysis.this,
                                    "Connection failed: " + t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        });


        // ==========================================
        // CLEAR IMAGE BUTTON
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


    private byte[] getImageBytes() throws IOException {

        if (imageUri != null) {
            try (InputStream inputStream =
                         getContentResolver().openInputStream(imageUri);
                 ByteArrayOutputStream outputStream =
                         new ByteArrayOutputStream()) {

                if (inputStream == null) {
                    throw new IOException("Cannot open selected image.");
                }

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return outputStream.toByteArray();
            }
        }

        if (cameraBitmap != null) {
            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            cameraBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    90,
                    outputStream
            );

            return outputStream.toByteArray();
        }

        return null;
    }




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