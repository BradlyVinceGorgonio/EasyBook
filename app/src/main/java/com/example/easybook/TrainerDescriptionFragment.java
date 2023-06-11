package com.example.easybook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainerDescriptionFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_description, container, false);

        // Retrieve the trainer ID from arguments
        String trainerId = getArguments().getString("trainerId");


        fetchTrainerDataFromFirestore(trainerId);



        //Book now Button
        Button bookNowBtn = (Button) view.findViewById(R.id.booknowBtn);
        bookNowBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String trainerId = getArguments().getString("trainerId");

                BookNowFragment bookNowFragment = new BookNowFragment();
                Bundle args = new Bundle();
                args.putString("trainerId", trainerId);
                bookNowFragment.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, bookNowFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return view;
    }





    private void fetchTrainerDataFromFirestore(String trainerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trainer").document(trainerId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve the user's fields from the document
                            String name = document.getString("name");
                            String description = document.getString("description");
                            String category = document.getString("category");
                            String uid = document.getString("uid");
                            long price = 0; // Default value if field is missing or null
                            Object value = document.get("price");
                            if (value instanceof Long) {
                                price = (long) value;
                            }
                            long satisfied = 0;
                            Object value1 = document.get("satisfied_users");
                            if (value instanceof Long) {
                                satisfied = (long) value1;
                            }
                            String satisfiedString = Long.toString(satisfied);
                            String priceString = Long.toString(price);
                            String profilePictureUrl = document.getString("profilePictureUrl");
                            //Display array of category fields
                            List<String> categoryFields = (List<String>) document.get("category_field");
                            String categoryFieldsString = TextUtils.join(", ", categoryFields);
                            // Display the retrieved data in your UI elements
                            // For example, set the text of TextViews

                            TextView nameTextView = getView().findViewById(R.id.userName);
                            TextView descriptionTextView = getView().findViewById(R.id.description);
                            TextView categoryTV = getView().findViewById(R.id.category);
                            TextView category_fieldsTV= getView().findViewById(R.id.category_fieldsTV);
                            TextView priceTV = getView().findViewById(R.id.userPrice);
                            TextView satisfiedTV = getView().findViewById(R.id.satisfiedclients);
                            Button booknow = getView().findViewById(R.id.booknowBtn);



                            booknow.setText("Book now for " + priceString+"php");
                            nameTextView.setText(name);
                            descriptionTextView.setText(description);
                            categoryTV.setText(category);
                            //Category Fix
                            category_fieldsTV.setText(categoryFieldsString);
                            priceTV.setText(priceString + "php");
                            satisfiedTV.setText("Satisfied Clients: "+satisfiedString);

                            Log.d("Checker", "uid = " + uid);
                            Log.d("Checker", "image url = " + profilePictureUrl);
                            //Display Image
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("applying_trainer_images/" + uid + "/" +profilePictureUrl);

                            final String[] tempProfilePictureUrl = {profilePictureUrl};  // Declare a final temporary variable

                            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                tempProfilePictureUrl[0] = uri.toString();  // Assign the value to the temporary variable

                                // Display the retrieved data in your UI elements
                                // For example, set the image using a library like Picasso or Glide

                                // Example using Picasso:
                                ImageView profileImageView = getView().findViewById(R.id.userImg);
                                Glide.with(requireContext())
                                        .load(tempProfilePictureUrl[0])
                                        .into(profileImageView);



                                // Update other UI elements with the retrieved data
                                // ...
                            }).addOnFailureListener(e -> {
                                // Handle any errors that occur while fetching the profile picture URL
                            });




                        } else {
                            // The document does not exist
                        }
                    } else {
                        // Handle the failure scenario
                    }
                });
    }



    public static TrainerDescriptionFragment newInstance(String trainerId) {
        TrainerDescriptionFragment fragment = new TrainerDescriptionFragment();
        Bundle args = new Bundle();
        args.putString("trainerId", trainerId);
        fragment.setArguments(args);
        return fragment;
    }

}