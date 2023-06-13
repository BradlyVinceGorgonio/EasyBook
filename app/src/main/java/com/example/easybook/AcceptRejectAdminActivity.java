package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AcceptRejectAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject_admin);
        Intent intent = getIntent();
        String adminId = intent.getStringExtra("adminId");
        Log.d("taeka", adminId);

        fetchDataFromFirestore(adminId);




    }
    private void fetchDataFromFirestore(String id) {
        String adminAccount = "abcdefghijklmnopqrstuvwxyz";
        String documentId = id;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin")
                .document(adminAccount)
                .collection("trainer_request")
                .document(documentId)  // Add this line to specify the document ID
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            String profilePictureUrl = document.getString("profilePictureUrl");
                            String validIdFileName = document.getString("validIdFileName");
                            String documentFileName = document.getString("documentFileName");
                            String uid = document.getString("uid");

                            String name = document.getString("name");
                            String age = document.getString("age");
                            String category = document.getString("category");
                            List<String> field = (List<String>) document.get("category_field");
                            List<String> schedule = (List<String>) document.get("schedule_day");

                            String Address = document.getString("Address");
                            String idNum = document.getString("id_number");

                            String facility = document.getString("trainer facility");
                            String price = document.getString("price");


                            String reason = document.getString("reason");


                            String fieldString = String.join(System.lineSeparator(), field);
                            String scheduleString = String.join(System.lineSeparator(), schedule);


                            TextView Textname = (TextView) findViewById(R.id.trainersMame);
                            TextView TextAge = (TextView) findViewById(R.id.trainersAge);
                            TextView TextCat = (TextView) findViewById(R.id.trainersCategory);
                            TextView TextField = (TextView) findViewById(R.id.trainersField);
                            TextView TextFacility = (TextView) findViewById(R.id.trainersFacility);
                            TextView TextPrice = (TextView) findViewById(R.id.trainersPrice);
                            TextView TextAddress = (TextView) findViewById(R.id.trainersAddress);
                            TextView IDnum = (TextView) findViewById(R.id.trainerIDNumber);
                            TextView TextSched = (TextView) findViewById(R.id.scheduleAdmin);

                            Textname.setText(name);
                            TextAge.setText(age);
                            TextCat.setText(category);
                            TextField.setText(fieldString);
                            TextFacility.setText(facility);
                            TextPrice.setText(price);
                            TextAddress.setText(Address);
                            IDnum.setText(idNum);
                            TextSched.setText(scheduleString);








                            // Fetch the profile picture URL from Firebase Storage
                            String imagePath = "applying_trainer_images/" + uid + "/" + profilePictureUrl;
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imagePath);

                            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                // Load the image into an ImageView using Glide
                                ImageView imageView = findViewById(R.id.trainersImage); // Replace with your ImageView ID
                                Glide.with(this).load(imageUrl).into(imageView);

                            }).addOnFailureListener(exception -> {
                                // Handle the failure scenario
                            });

                            // Fetch the validID URL from Firebase Storage
                            String imagePathID = "applying_trainer_images/" + uid + "/" + validIdFileName;
                            StorageReference storageRefID = FirebaseStorage.getInstance().getReference().child(imagePathID);

                            storageRefID.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                // Load the image into an ImageView using Glide
                                ImageView imageView = findViewById(R.id.trainerValidID); // Replace with your ImageView ID
                                Glide.with(this).load(imageUrl).into(imageView);

                            }).addOnFailureListener(exception -> {
                                // Handle the failure scenario
                            });

                            // Fetch the Document URL from Firebase Storage
                            String imagePathDoc = "applying_trainer_images/" + uid + "/" + documentFileName;
                            StorageReference storageRefDoc = FirebaseStorage.getInstance().getReference().child(imagePathDoc);

                            storageRefDoc.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                // Load the image into an ImageView using Glide
                                ImageView imageView = findViewById(R.id.trainerDocument); // Replace with your ImageView ID
                                Glide.with(this).load(imageUrl).into(imageView);

                            }).addOnFailureListener(exception -> {
                                // Handle the failure scenario
                            });

                        } else {
                            // Document doesn't exist
                        }
                    } else {
                        // Handle the failure scenario
                    }
                });
    }

}