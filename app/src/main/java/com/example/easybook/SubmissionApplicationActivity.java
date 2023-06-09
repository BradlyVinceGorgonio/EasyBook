package com.example.easybook;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

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
                    uploadImageToStorage("profile_picture.jpg", imageUri);
                    break;
                case PICK_IMAGE_REQUEST_VALID_ID:
                    validIdBtn.setText(fileName);
                    uploadImageToStorage("valid_id.jpg", imageUri);
                    break;
                case PICK_IMAGE_REQUEST_DOCUMENT:
                    documentBtn.setText(fileName);
                    uploadImageToStorage("document.jpg", imageUri);
                    break;
            }
        }
    }

    private void uploadImageToStorage(String filename, Uri imageUri) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference userFolderRef = storageRef.child("applying_trainer_images/" + currentUserId);

        StorageReference fileRef = userFolderRef.child(filename);
        UploadTask uploadTask = fileRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("works", "wow it works: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error uploading the image
                Log.d("works", "wow it dont works: ");
            }
        });
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
        // ...

        // Upload the file names to Firestore
        Map<String, Object> bookingRequestData = new HashMap<>();
        bookingRequestData.put("profilePictureFileName", profilePictureBtn.getText().toString());
        bookingRequestData.put("validIdFileName", validIdBtn.getText().toString());
        bookingRequestData.put("documentFileName", documentBtn.getText().toString());

        // Create a new document in the "booking_request" subcollection of the current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String adminAccount = "abcdefghijklmnopqrstuvwxyz";
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        // Set the data to the document in the "trainer_request" subcollection of the admin account
        db.collection("admin")
                .document(adminAccount)
                .collection("trainer_request")
                .document(currentUserId)
                .set(bookingRequestData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document uploaded successfully

                        // Display the success dialog and navigate to the home activity
                        AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionApplicationActivity.this);
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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error uploading the document
                        // Handle the error or display an error message
                    }
                });
    }
}
