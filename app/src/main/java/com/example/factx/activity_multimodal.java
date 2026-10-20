
package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.factx.api.ApiService;
import com.example.factx.api.RetrofitClient;
import com.example.factx.model.MultimodalAnalysisResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_multimodal extends AppCompatActivity {

    private static final String TAG = "MULTIMODAL_FLOW";

    private ImageView imgPreview;
    private TextView txtImagePlaceholder;

    private Button btnSelectImage;
    private Button btnAnalyze;
    private Button btnClear;

    private Uri imageUri = null;
    private String email;

    // IMAGE PICKER
    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            imageUri = uri;

                            imgPreview.setImageURI(imageUri);
                            imgPreview.setVisibility(ImageView.VISIBLE);
                            txtImagePlaceholder.setVisibility(TextView.GONE);

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
        setContentView(R.layout.activity_multimodal);

        // GET LOGGED-IN USER EMAIL
        email = getIntent().getStringExtra("email");

        // FIND VIEWS
        imgPreview = findViewById(R.id.imgPreview);
        txtImagePlaceholder = findViewById(R.id.txtImagePlaceholder);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAnalyze = findViewById(R.id.btnAnalyze);
        btnClear = findViewById(R.id.btnClear);

        // INITIAL IMAGE STATE
        imageUri = null;
        imgPreview.setImageDrawable(null);
        imgPreview.setVisibility(ImageView.VISIBLE);
        txtImagePlaceholder.setVisibility(TextView.VISIBLE);

        // SELECT IMAGE
        btnSelectImage.setOnClickListener(v ->
                imagePickerLauncher.launch("image/*")
        );

        // ANALYZE BUTTON
        btnAnalyze.setOnClickListener(v -> {

            if (imageUri == null) {
                Toast.makeText(
                        activity_multimodal.this,
                        "Please select an image",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            btnAnalyze.setEnabled(false);

            final Uri selectedImageUri = imageUri;

            // READ IMAGE IN BACKGROUND THREAD
            new Thread(() -> {
                try {
                    byte[] imageBytes;

                    try (
                            InputStream input =
                                    getContentResolver().openInputStream(
                                            selectedImageUri
                                    );
                            ByteArrayOutputStream output =
                                    new ByteArrayOutputStream()
                    ) {
                        if (input == null) {
                            throw new IOException(
                                    "Cannot read selected image"
                            );
                        }

                        byte[] buffer = new byte[8192];
                        int count;

                        while ((count = input.read(buffer)) != -1) {
                            output.write(buffer, 0, count);
                        }

                        imageBytes = output.toByteArray();
                    }

                    if (imageBytes.length == 0) {
                        throw new IOException("Selected image is empty");
                    }

                    Log.d(TAG,
                            "Image loaded. Size = " + imageBytes.length);

                    RequestBody imageBody = RequestBody.create(
                            MediaType.parse("image/*"),
                            imageBytes
                    );

                    MultipartBody.Part imagePart =
                            MultipartBody.Part.createFormData(
                                    "file",
                                    "news_image.jpg",
                                    imageBody
                            );

                    ApiService apiService = RetrofitClient.getClient()
                            .create(ApiService.class);

                    // SEND IMAGE TO BACKEND
                    apiService.analyzeMultimodal(imagePart)
                            .enqueue(
                                    new Callback<MultimodalAnalysisResponse>() {

                                        @Override
                                        public void onResponse(
                                                Call<MultimodalAnalysisResponse> call,
                                                Response<MultimodalAnalysisResponse> response) {

                                            btnAnalyze.setEnabled(true);

                                            // LOG API RESPONSE
                                            Log.d(TAG,
                                                    "API response received. HTTP = "
                                                            + response.code());

                                            if (!response.isSuccessful()
                                                    || response.body() == null) {

                                                Log.e(TAG,
                                                        "API request failed. HTTP = "
                                                                + response.code());

                                                Toast.makeText(
                                                        activity_multimodal.this,
                                                        "Analysis failed. HTTP "
                                                                + response.code(),
                                                        Toast.LENGTH_LONG
                                                ).show();
                                                return;
                                            }

                                            MultimodalAnalysisResponse data =
                                                    response.body();

                                            // LOG RESPONSE DATA
                                            Log.d("MULTIMODAL_API",
                                                    "Prediction = "
                                                            + data.getPrediction()
                                                            + ", Confidence = "
                                                            + data.getConfidence()
                                                            + ", GradCAM length = "
                                                            + (data.getGradcam_image() == null
                                                            ? "null"
                                                            : data.getGradcam_image().length())
                                                            + ", Important words = "
                                                            + (data.getImportant_words() == null
                                                            ? "null"
                                                            : data.getImportant_words().size())
                                            );

                                            String prediction = data.getPrediction();

                                            // VALIDATE PREDICTION
                                            if (prediction == null
                                                    || (!prediction.equalsIgnoreCase("FAKE")
                                                    && !prediction.equalsIgnoreCase("REAL"))) {

                                                Log.e(TAG,
                                                        "Invalid prediction received");

                                                Toast.makeText(
                                                        activity_multimodal.this,
                                                        "Invalid prediction from server",
                                                        Toast.LENGTH_LONG
                                                ).show();
                                                return;
                                            }

                                            // CHOOSE RESULT PAGE
                                            Class<?> targetActivity;

                                            if ("FAKE".equalsIgnoreCase(prediction)) {
                                                targetActivity =
                                                        activity_fake_result.class;
                                            } else {
                                                targetActivity =
                                                        activity_real_result.class;
                                            }

                                            // CREATE INTENT
                                            Intent intent = new Intent(
                                                    activity_multimodal.this,
                                                    targetActivity
                                            );

                                            // SEND COMMON RESULT DATA
                                            intent.putExtra(
                                                    "prediction",
                                                    prediction
                                            );

                                            intent.putExtra(
                                                    "resultType",
                                                    prediction.toLowerCase(
                                                            java.util.Locale.ROOT
                                                    )
                                            );

                                            intent.putExtra(
                                                    "analysis_type",
                                                    "multimodal"
                                            );

                                            intent.putExtra("email", email);

                                            intent.putExtra(
                                                    "imageUri",
                                                    selectedImageUri.toString()
                                            );

                                            intent.putExtra(
                                                    "confidence",
                                                    data.getConfidence()
                                            );

                                            // SEND EXPLANATIONS FOR FAKE NEWS
                                            if ("FAKE".equalsIgnoreCase(prediction)) {

                                                // Grad-CAM image
                                                intent.putExtra(
                                                        "gradcam_image",
                                                        data.getGradcam_image()
                                                );

                                                // Important words
                                                if (data.getImportant_words() != null) {

                                                    org.json.JSONArray wordsArray =
                                                            new org.json.JSONArray();

                                                    for (
                                                            MultimodalAnalysisResponse.ImportantWord word
                                                            : data.getImportant_words()
                                                    ) {
                                                        if (word == null) {
                                                            continue;
                                                        }

                                                        org.json.JSONObject wordObject =
                                                                new org.json.JSONObject();

                                                        try {
                                                            wordObject.put(
                                                                    "token",
                                                                    word.getToken()
                                                            );

                                                            wordObject.put(
                                                                    "importance",
                                                                    word.getImportance()
                                                            );

                                                            wordsArray.put(wordObject);

                                                        } catch (
                                                                org.json.JSONException e
                                                        ) {
                                                            Log.e(
                                                                    TAG,
                                                                    "Error preparing important words",
                                                                    e
                                                            );
                                                        }
                                                    }

                                                    intent.putExtra(
                                                            "important_words",
                                                            wordsArray.toString()
                                                    );
                                                }
                                            }

                                            Log.d(TAG,
                                                    "Opening result page: "
                                                            + targetActivity.getSimpleName());

                                            // OPEN RESULT PAGE ONLY AFTER
                                            // ALL RESULT DATA HAS BEEN ADDED
                                            try {
                                                startActivity(intent);


                                            } catch (Exception e) {
                                                Log.e(TAG, "Could not open result page", e);

                                                android.util.Log.e(
                                                        "RESULT_PAGE_ERROR",
                                                        "Target activity = " + targetActivity.getName(),
                                                        e
                                                );

                                                Toast.makeText(
                                                        activity_multimodal.this,
                                                        "Result page error: "
                                                                + e.getClass().getSimpleName()
                                                                + " - " + e.getMessage(),
                                                        Toast.LENGTH_LONG
                                                ).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(
                                                Call<MultimodalAnalysisResponse> call,
                                                Throwable t) {

                                            btnAnalyze.setEnabled(true);

                                            Log.e(
                                                    TAG,
                                                    "API connection failed",
                                                    t
                                            );

                                            Toast.makeText(
                                                    activity_multimodal.this,
                                                    "Connection failed: "
                                                            + t.getMessage(),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    });

                } catch (Exception e) {

                    Log.e(TAG, "Could not read selected image", e);

                    runOnUiThread(() -> {
                        btnAnalyze.setEnabled(true);

                        Toast.makeText(
                                activity_multimodal.this,
                                "Could not read image: "
                                        + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
                }
            }).start();
        });

        // CLEAR BUTTON
        btnClear.setOnClickListener(v -> {
            imageUri = null;

            imgPreview.setImageDrawable(null);
            imgPreview.setVisibility(ImageView.VISIBLE);
            txtImagePlaceholder.setVisibility(TextView.VISIBLE);

            Toast.makeText(
                    activity_multimodal.this,
                    "Image cleared",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
