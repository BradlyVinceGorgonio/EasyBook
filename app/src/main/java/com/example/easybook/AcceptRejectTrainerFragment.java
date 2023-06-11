package com.example.easybook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AcceptRejectTrainerFragment extends Fragment {

    private String name;
    private String uid;
    private String status;
    private Timestamp timestamp;
    private String schedule;
    private String location;
    private String level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accept_reject_trainer, container, false);

        String clientId = getArguments().getString("clientId");

        trainerHomeFragment trainerHomeFragment = new trainerHomeFragment();
        Bundle args = new Bundle();
        args.putString("trainerId", clientId);
        trainerHomeFragment.setArguments(args);

        Log.d("client", "onItemClick when next page: " + clientId);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String Traineruid = currentUser.getUid();
            // Use the UID as needed
            Log.d("Firebases", "Current UID be: " + Traineruid);
            fetchDataFromFirestore(clientId, Traineruid);
            Log.d("Firebases", "Current UID: af " + Traineruid);
        } else {
            Log.d("Firebases", "No user signed in.");
        }

        // Inflate the layout for this fragment






       return view;
    }

    private void fetchDataFromFirestore(String clientId, String trainerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trainerRef = db.collection("trainer");
        CollectionReference bookingRequestRef = trainerRef.document(trainerId).collection("booking_request");

        Log.d("what", "fetchDataFromFirestore: Im here");
        //// ERORRR ERRRRRRRRRORR ERORRRRRRRRRRRRRRRRRRRRRRRRR
        bookingRequestRef.document(clientId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Retrieve fields from the document
                    name = document.getString("name");
                    uid = document.getString("uid");
                    status = document.getString("status");
                    // Retrieve other fields as needed
                    timestamp = document.getTimestamp("timestamp");
                    schedule = document.getString("schedule");
                    location = document.getString("location");
                    level = document.getString("level");

                    // Do something with the retrieved fields
                    Log.d("what", "Name: " + name);
                    Log.d("what", "UID: " + uid);
                    Log.d("what", "Status: " + status);
                    Log.d("what", "timestamp: " + timestamp);
                    Log.d("what", "schedule: " + schedule);
                    Log.d("what", "location: " + status);
                    Log.d("what", "level: " + level);
                    Log.d("what", "location: " + location);




                    // Process other fields as needed
                }
            } else {
                Log.d("Firestore", "Error getting booking requests: " + task.getException());
            }
        });
    }



    public static AcceptRejectTrainerFragment newInstance(String clientId) {
        AcceptRejectTrainerFragment fragment = new AcceptRejectTrainerFragment();
        Bundle args = new Bundle();
        args.putString("clientId", clientId);
        fragment.setArguments(args);
        return fragment;
    }



}