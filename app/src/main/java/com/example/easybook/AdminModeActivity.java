package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminModeActivity extends AppCompatActivity implements AdminUserAdapter.OnItemClickListener {

    private List<AdminClass> adminList;
    private AdminUserAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mode);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adminList = new ArrayList<>();
        adminAdapter = new AdminUserAdapter(this, adminList, this);
        recyclerView.setAdapter(adminAdapter);

        // Fetch data from Firestore and populate adminList
        // ...
        fetchDataFromFirestore();

    }

    @Override
    public void onItemClick(AdminClass admin) {
        String adminId = admin.getUid(); // Assuming AdminClass has a getUid() method to retrieve the admin ID
        Intent intent = new Intent(AdminModeActivity.this, AcceptRejectAdminActivity.class);
        intent.putExtra("adminId", adminId); // Pass the admin ID to the AcceptRejectAdminActivity
        startActivity(intent);
    }

    private void fetchDataFromFirestore() {
        String adminAccount = "abcdefghijklmnopqrstuvwxyz";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin")
                .document(adminAccount)
                .collection("trainer_request")
                //.orderBy("satisfied_users", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        adminList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String category = document.getString("category");
                            String uid = document.getString("uid");
                            String profilePictureUrl = document.getString("profilePictureUrl");
                            String price = document.getString("price");
                            String reason = document.getString("reason");


                            Log.d("taeka", uid + " before " + profilePictureUrl);
                            // Fetch the profile picture URL from Firebase Storage
                            fetchProfilePictureUrl(name, "Price per session: P"+ price, reason , category, uid, "/"+profilePictureUrl);



                        }
                    } else {
                        // Handle the failure scenario
                    }
                });
    }
    private void fetchProfilePictureUrl(String name, String price, String reason, String category, String uid, String profilePictureUrl) {
        Log.d("taeka", uid + " weird " + profilePictureUrl);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("applying_trainer_images/" + uid + profilePictureUrl);

        final String[] tempProfilePictureUrl = {profilePictureUrl};  // Declare a final temporary variable

        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            tempProfilePictureUrl[0] = uri.toString();  // Assign the value to the temporary variable

            // Create a TrainerClass instance with the retrieved data
            AdminClass admin = new AdminClass(tempProfilePictureUrl[0], name, uid, category, reason, price);
            adminList.add(admin);

            // Notify the adapter that data has changed
            adminAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors that occur while fetching the profile picture URL
        });
    }

}
