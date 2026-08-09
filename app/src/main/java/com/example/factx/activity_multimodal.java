package com.example.factx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class activity_multimodal extends AppCompatActivity {

    EditText etTitle;
    EditText etNews;

    ImageView imgPreview;
    TextView txtImagePlaceholder;

    Button btnSelectImage;
    Button btnAnalyze;
    Button btnClear;

    Uri imageUri = null;


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

                            // Show ImageView
                            imgPreview.setVisibility(
                                    ImageView.VISIBLE
                            );

                            // Hide "No Image Selected"
                            txtImagePlaceholder.setVisibility(
                                    TextView.GONE
                            );

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
        // FIND VIEWS
        // ==========================================

        etTitle =
                findViewById(R.id.etTitle);

        etNews =
                findViewById(R.id.etNews);

        imgPreview =
                findViewById(R.id.imgPreview);

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

        imgPreview.setVisibility(
                ImageView.VISIBLE
        );

        txtImagePlaceholder.setVisibility(
                TextView.VISIBLE
        );


        // ==========================================
        // SELECT IMAGE
        // ==========================================

        btnSelectImage.setOnClickListener(v -> {

            imagePickerLauncher.launch(
                    "image/*"
            );

        });


        // ==========================================
        // ANALYZE MULTIMODAL
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


            // ======================================
            // VALIDATION
            // ======================================

            // Text OR Image required

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
            // DUMMY RESULT
            // AI MODEL NOT CONNECTED YET
            // ======================================

            String resultType;

            if (System.currentTimeMillis() % 2 == 0) {

                resultType = "real";

            } else {

                resultType = "fake";
            }


            // ======================================
            // OPEN LOADING SCREEN
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
        // CLEAR
        // ==========================================

        btnClear.setOnClickListener(v -> {

            // Clear text
            etTitle.setText("");

            etNews.setText("");


            // Clear image
            imageUri = null;

            imgPreview.setImageDrawable(
                    null
            );


            // Keep image box visible
            imgPreview.setVisibility(
                    ImageView.VISIBLE
            );


            // Show placeholder
            txtImagePlaceholder.setVisibility(
                    TextView.VISIBLE
            );


            etTitle.requestFocus();

        });

    }
}