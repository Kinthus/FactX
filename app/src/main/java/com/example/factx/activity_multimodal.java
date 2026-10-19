
package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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



    ImageView imgPreview;
    TextView txtImagePlaceholder;

    Button btnSelectImage;
    Button btnAnalyze;
    Button btnClear;

    Uri imageUri = null;

    // Logged-in user's email
    String email;


    // ==========================================
    // IMAGE PICKER
    // ==========================================

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            // Save selected image
                            imageUri = uri;

                            // Show selected image
                            imgPreview.setImageURI(imageUri);

                            // Show image
                            imgPreview.setVisibility(ImageView.VISIBLE);

                            // Hide placeholder
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


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email = getIntent().getStringExtra("email");


        // ==========================================
        // FIND VIEWS
        // ==========================================



        imgPreview = findViewById(R.id.imgPreview);

        txtImagePlaceholder =
                findViewById(R.id.txtImagePlaceholder);

        btnSelectImage =
                findViewById(R.id.btnSelectImage);

        btnAnalyze =
                findViewById(R.id.btnAnalyze);

        btnClear =
                findViewById(R.id.btnClear);


        // ==========================================
        // INITIAL IMAGE STATE
        // ==========================================

        imageUri = null;

        imgPreview.setVisibility(ImageView.VISIBLE);

        txtImagePlaceholder.setVisibility(TextView.VISIBLE);


        // ==========================================
        // SELECT IMAGE
        // ==========================================

        btnSelectImage.setOnClickListener(v -> {

            imagePickerLauncher.launch("image/*");

        });


        // ==========================================
        // ANALYZE BUTTON
        // ==========================================


        btnAnalyze.setOnClickListener(v -> {



            if (imageUri == null) {
                Toast.makeText(this, "Please select an image",
                        Toast.LENGTH_LONG).show();
                return;
            }



            btnAnalyze.setEnabled(false);

            new Thread(() -> {
                try {
                    byte[] imageBytes;

                    try (InputStream input =
                                 getContentResolver().openInputStream(imageUri);
                         ByteArrayOutputStream output =
                                 new ByteArrayOutputStream()) {

                        if (input == null) {
                            throw new IOException("Cannot read selected image");
                        }

                        byte[] buffer = new byte[8192];
                        int count;

                        while ((count = input.read(buffer)) != -1) {
                            output.write(buffer, 0, count);
                        }

                        imageBytes = output.toByteArray();
                    }




                    RequestBody imageBody = RequestBody.create(
                            MediaType.parse("image/*"), imageBytes);

                    MultipartBody.Part imagePart =
                            MultipartBody.Part.createFormData(
                                    "file", "news_image.jpg", imageBody);

                    RequestBody textBody = RequestBody.create(
                            MediaType.parse("text/plain"), "");

                    ApiService apiService = RetrofitClient.getClient()
                            .create(ApiService.class);

                    apiService.analyzeMultimodal(imagePart, textBody)
                            .enqueue(new Callback<MultimodalAnalysisResponse>() {

                                @Override
                                public void onResponse(
                                        Call<MultimodalAnalysisResponse> call,
                                        Response<MultimodalAnalysisResponse> response) {

                                    btnAnalyze.setEnabled(true);

                                    if (!response.isSuccessful() || response.body() == null) {
                                        Toast.makeText(
                                                activity_multimodal.this,
                                                "Analysis failed. HTTP " + response.code(),
                                                Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    MultimodalAnalysisResponse data = response.body();
                                    String prediction = data.getPrediction();

                                    if (prediction == null ||
                                            (!prediction.equalsIgnoreCase("FAKE")
                                                    && !prediction.equalsIgnoreCase("REAL"))) {
                                        Toast.makeText(
                                                activity_multimodal.this,
                                                "Invalid prediction from server",
                                                Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    Intent intent = new Intent(
                                            activity_multimodal.this,
                                            activity_loading.class);

                                    intent.putExtra("resultType", prediction.toLowerCase());
                                    intent.putExtra("analysis_type", "multimodal");
                                    intent.putExtra("email", email);
                                    intent.putExtra("imageUri", imageUri.toString());

                                    intent.putExtra("confidence", data.getConfidence());


                                    if ("FAKE".equalsIgnoreCase(prediction)) {
                                        intent.putExtra(
                                                "gradcam_image",
                                                data.getGradcam_image()
                                        );

                                        if (data.getImportant_words() != null) {
                                            StringBuilder wordsJson = new StringBuilder("[");

                                            for (int i = 0; i < data.getImportant_words().size(); i++) {
                                                MultimodalAnalysisResponse.ImportantWord word =
                                                        data.getImportant_words().get(i);

                                                if (i > 0) {
                                                    wordsJson.append(",");
                                                }

                                                wordsJson.append("{\"token\":\"")
                                                        .append(word.getToken().replace("\"", "\\\""))
                                                        .append("\",\"importance\":")
                                                        .append(word.getImportance())
                                                        .append("}");
                                            }

                                            wordsJson.append("]");

                                            intent.putExtra(
                                                    "important_words",
                                                    wordsJson.toString()
                                            );
                                        }
                                    }


                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(
                                        Call<MultimodalAnalysisResponse> call,
                                        Throwable t) {

                                    btnAnalyze.setEnabled(true);

                                    Toast.makeText(
                                            activity_multimodal.this,
                                            "Connection failed: " + t.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        btnAnalyze.setEnabled(true);
                        Toast.makeText(
                                activity_multimodal.this,
                                "Could not read image: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
                }
            }).start();
        });



        // ==========================================
        // CLEAR BUTTON
        // ==========================================

        btnClear.setOnClickListener(v -> {

            // Clear title


            // Remove image URI
            imageUri = null;

            // Remove displayed image
            imgPreview.setImageDrawable(null);

            // Keep image box visible
            imgPreview.setVisibility(ImageView.VISIBLE);

            // Show placeholder
            txtImagePlaceholder.setVisibility(TextView.VISIBLE);


        });


    }
}