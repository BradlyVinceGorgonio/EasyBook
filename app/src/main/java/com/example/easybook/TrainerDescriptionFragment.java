package com.example.easybook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrainerDescriptionFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_description, container, false);

        // Retrieve the trainer ID from arguments
        String trainerId = getArguments().getString("trainerId");


        fetchTrainerDataFromFirestore(trainerId);



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
                            // Display the retrieved data in your UI elements
                            // For example, set the text of TextViews

                            // Example:
                            TextView nameTextView = getView().findViewById(R.id.userName);
                            nameTextView.setText(name);

                            // Repeat the above step for other fields

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