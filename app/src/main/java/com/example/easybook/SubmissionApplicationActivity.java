package com.example.easybook;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;

public class SubmissionApplicationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_PROFILE = 1;
    private static final int PICK_IMAGE_REQUEST_VALID_ID = 2;
    private static final int PICK_IMAGE_REQUEST_DOCUMENT = 3;

    private Button profilePictureBtn, validIdBtn, documentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_application);

        profilePictureBtn = findViewById(R.id.btnProfilePicture);
        validIdBtn = findViewById(R.id.btnValidID);
        documentBtn = findViewById(R.id.btnProofOfDocument);

        profilePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooserProfilePicture();
            }
        });

        validIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooserValidID();
            }
        });

        documentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooserDocument();
            }
        });

        Button submitButton = findViewById(R.id.submitApplicationBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void openFileChooserProfilePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST_PROFILE);
    }

    private void openFileChooserValidID() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST_VALID_ID);
    }

    private void openFileChooserDocument() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST_DOCUMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            String fileName = getFileName(imageUri);

            switch (requestCode) {
                case PICK_IMAGE_REQUEST_PROFILE:
                    profilePictureBtn.setText(fileName);
                    break;
                case PICK_IMAGE_REQUEST_VALID_ID:
                    validIdBtn.setText(fileName);
                    break;
                case PICK_IMAGE_REQUEST_DOCUMENT:
                    documentBtn.setText(fileName);
                    break;
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(nameIndex);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Application Submitted");
        builder.setMessage("Your account is pending. Please wait for an inbox message. Keep an eye on the inbox to be notified if your request is accepted.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle OK button click
                dialog.dismiss();
                Intent intent = new Intent(SubmissionApplicationActivity.this, HomeFragmentsActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
