package com.example.easybook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllTrainersFragment extends Fragment implements UserAdapter.OnItemClickListener {
//v
    private List<TrainerClass> trainerList;
    private UserAdapter trainerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trainers, container, false);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trainerList = new ArrayList<>();
        trainerAdapter = new UserAdapter(getContext(), trainerList, this);
        recyclerView.setAdapter(trainerAdapter);

        Bundle args = getArguments();
        if (args != null) {
            String selectedSport = args.getString("selectedSport");
            // Use the selectedSport string as needed
            fetchDataFromFirestore(selectedSport);
        }

        // Fetch data from Firestore


        return view;
    }
    @Override
    public void onItemClick(TrainerClass trainer) {
        String trainerId = trainer.getUid(); // Assuming TrainerClass has a getId() method to retrieve the document ID
        TrainerDescriptionFragment trainerDescriptionFragment = TrainerDescriptionFragment.newInstance(trainerId);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, trainerDescriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void fetchDataFromFirestore(String field) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trainer")
                .whereArrayContains("category_field", field)
                //.orderBy("satisfied_users", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        trainerList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String description = document.getString("description");
                            String uid = document.getString("uid");
                            String profilePictureUrl = document.getString("profilePictureUrl");

                            // Check if "satisfied_users" field exists and has a valid value
                            long price = 0; // Default value if field is missing or null
                            if (document.contains("price")) {
                                Object value = document.get("price");
                                if (value instanceof Long) {
                                    price = (long) value;
                                }
                            }

                            // Fetch the profile picture URL from Firebase Storage
                            fetchProfilePictureUrl(name, "Price per session: P"+ price, description, uid, "/"+profilePictureUrl);


                        }
                    } else {
                        // Handle the failure scenario
                    }
                });
    }

    private void fetchProfilePictureUrl(String name, String price, String description, String uid, String profilePictureUrl) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("applying_trainer_images/" + uid + profilePictureUrl);

        final String[] tempProfilePictureUrl = {profilePictureUrl};  // Declare a final temporary variable

        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            tempProfilePictureUrl[0] = uri.toString();  // Assign the value to the temporary variable

            // Create a TrainerClass instance with the retrieved data
            TrainerClass trainer = new TrainerClass(name, price, description, uid, tempProfilePictureUrl[0]);
            trainerList.add(trainer);

            // Notify the adapter that data has changed
            trainerAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors that occur while fetching the profile picture URL
        });
    }







}
