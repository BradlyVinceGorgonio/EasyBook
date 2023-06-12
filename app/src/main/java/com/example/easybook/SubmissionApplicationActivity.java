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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubmissionApplicationActivity extends AppCompatActivity {



    private static final int PICK_IMAGE_REQUEST_PROFILE = 1;
    private static final int PICK_IMAGE_REQUEST_VALID_ID = 2;
    private static final int PICK_IMAGE_REQUEST_DOCUMENT = 3;

    private Button profilePictureBtn, validIdBtn, documentBtn;
    private EditText description, IDNumber, reason, fromTime, toTime, trainerFacility, trainerPrice;
    private CheckBox field1, field2, field3,field4, monday,tuesday,wednesday,thursday,friday,saturday,sunday;

    private RadioGroup categoryGroup, fromGroup, toGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_application);

        profilePictureBtn = findViewById(R.id.btnProfilePicture);
        validIdBtn = findViewById(R.id.btnValidID);
        documentBtn = findViewById(R.id.btnProofOfDocument);


        categoryGroup = findViewById(R.id.categoryGroup);
        field1 = (CheckBox) findViewById(R.id.field1);
        field2 = (CheckBox) findViewById(R.id.field2);
        field3 = (CheckBox) findViewById(R.id.field3);
        field4 = (CheckBox) findViewById(R.id.field4);
        //CheckBox
        monday= (CheckBox) findViewById(R.id.Monday);
        tuesday = (CheckBox) findViewById(R.id.Tuesday);
        wednesday = (CheckBox) findViewById(R.id.Wednesday);
        thursday = (CheckBox) findViewById(R.id.Thursday);
        friday = (CheckBox) findViewById(R.id.Friday);
        saturday = (CheckBox) findViewById(R.id.Saturday);
        sunday = (CheckBox) findViewById(R.id.Sunday);

        final String[] selectedCategory = {null};
        categoryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    // Handle the selected option
                    selectedCategory[0] = selectedRadioButton.getText().toString();
                    // Do something with the selected option
                    Log.d("Selected Option", selectedCategory[0]);


                    if(selectedCategory[0].equals("Martial Arts Instructor"))
                    {
                        field1.setText("judo");
                        field2.setText("karate");
                        field3.setText("taekwondo");
                        field4.setText("boxing");
                    }
                    else if(selectedCategory[0].equals("Fitness Instructor"))
                    {
                        //rename title in database sorry =)
                        selectedCategory[0] = "Fitness Trainer";
                        Log.d("where", selectedCategory[0]);

                        field1.setText("Calisthenics");
                        field2.setText("Athletic");
                        field3.setText("Gym");
                        field4.setText("Gymnastics");

                    }
                    else if(selectedCategory[0].equals("Sports Instructor"))
                    {
                        //rename title in database sorry =)
                        Log.d("where", selectedCategory[0]);
                        //Rename Checkboxes
                        field1.setText("basketball");
                        field2.setText("badminton");
                        field3.setText("swimming");
                        field4.setText("volleyball");
                    }
                }
            }
        });
        fromGroup = findViewById(R.id.fromTimeGroup);

        final String[] selectedFrom = {null};
        fromGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    // Handle the selected option
                    selectedFrom[0] = selectedRadioButton.getText().toString();
                    // Do something with the selected option
                    Log.d("Selected Option", selectedFrom[0]);

                }
            }
        });

        toGroup = findViewById(R.id.toTimeGroup);

        final String[] selectedTo = {null};
        toGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    // Handle the selected option
                    selectedTo[0] = selectedRadioButton.getText().toString();
                    // Do something with the selected option
                    Log.d("Selected Option", selectedTo[0]);

                }
            }
        });




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
                //EditText
                description = (EditText) findViewById(R.id.etDescription);
                IDNumber = (EditText) findViewById(R.id.etIDNumber);
                reason = (EditText) findViewById(R.id.reasonET);
                fromTime = (EditText) findViewById(R.id.fromtimeET);
                toTime = (EditText) findViewById(R.id.totimeET);
                trainerFacility = (EditText) findViewById(R.id.facilityadd);
                trainerPrice = (EditText) findViewById(R.id.trainerspriceET);
                //CheckBox


                //

                ArrayList<String> checkedFields = new ArrayList<>();

                if (field1.isChecked()) {
                    checkedFields.add(field1.getText().toString());
                }

                if (field2.isChecked()) {
                    checkedFields.add(field2.getText().toString());
                }

                if (field3.isChecked()) {
                    checkedFields.add(field3.getText().toString());
                }

                if (field4.isChecked()) {
                    checkedFields.add(field4.getText().toString());
                }

                ArrayList<String> checkedDays = new ArrayList<>();
                if (monday.isChecked()) {
                    checkedDays.add(monday.getText().toString());
                }
                if (tuesday.isChecked()) {
                    checkedDays.add(tuesday.getText().toString());
                }
                if (wednesday.isChecked()) {
                    checkedDays.add(wednesday.getText().toString());
                }
                if (thursday.isChecked()) {
                    checkedDays.add(thursday.getText().toString());
                }
                if (friday.isChecked()) {
                    checkedDays.add(friday.getText().toString());
                }
                if (saturday.isChecked()) {
                    checkedDays.add(saturday.getText().toString());
                }
                if (sunday.isChecked()) {
                    checkedDays.add(sunday.getText().toString());
                }

                //Array of Checked field  // name : checkedItems
                //Array of Checked Days
                Log.d("where", selectedFrom[0] + " Outside");
                Log.d("where", selectedTo[0] + " Outside");
                Log.d("where", selectedCategory[0] + " outside");

                String from = selectedFrom[0];
                String to = selectedTo[0];
                String category = selectedCategory[0];

                for(int x = 0; x < checkedDays.size(); x++)
                {
                    checkedDays.set(x, checkedDays.get(x) + " 8:00 " + from + " - " + "12:30 " + to);
                }
                Log.d("where", checkedDays.toString());
                Log.d("where", category);

                String Description = description.getText().toString();
                String IDNum = IDNumber.getText().toString();
                String Reason = reason.getText().toString();
                String FromTime = fromTime.getText().toString();
                String ToTime = toTime.getText().toString();
                String TrainerFacility = trainerFacility.getText().toString();
                String TrainerPrice = trainerPrice.getText().toString();

                //checkedDays array
                //checkedFields array
                //from
                //to





                //showConfimation Dialog + upload to Firebase Storage
                showConfirmationDialog(Description, IDNum, Reason, TrainerFacility, TrainerPrice, checkedDays, checkedFields, category);

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

    private void showConfirmationDialog(String Description, String IDNum, String Reason, String TrainerFacility, String TrainerPrice, ArrayList checkedDays, ArrayList checkedFields, String category) {
        // ...

        // Upload the file names to Firestore
        Map<String, Object> bookingRequestData = new HashMap<>();
        bookingRequestData.put("profilePictureFileName", profilePictureBtn.getText().toString());
        bookingRequestData.put("validIdFileName", validIdBtn.getText().toString());
        bookingRequestData.put("documentFileName", documentBtn.getText().toString());
        // Include the parameters in the document fields
        bookingRequestData.put("Description", Description);
        bookingRequestData.put("IDNum", IDNum);
        bookingRequestData.put("Reason", Reason);
        bookingRequestData.put("TrainerFacility", TrainerFacility);
        bookingRequestData.put("TrainerPrice", TrainerPrice);
        bookingRequestData.put("checkedDays", checkedDays);
        bookingRequestData.put("checkedFields", checkedFields);
        bookingRequestData.put("category", category);


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
                //Add the Checked Array here
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
