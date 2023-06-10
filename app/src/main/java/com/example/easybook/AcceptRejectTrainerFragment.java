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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AcceptRejectTrainerFragment extends Fragment {

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

        // Inflate the layout for this fragment
        fetchDataFromFirestore(clientId);
       return view;
    }

    private void fetchDataFromFirestore(String clientId)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference trainerRef = db.collection("trainer");
        CollectionReference bookingRequestRef = trainerRef.document(clientId).collection("booking_request");

        bookingRequestRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Retrieve fields from the document
                    String name = document.getString("name");
                    String uid = document.getString("uid");
                    String status = document.getString("status");
                    // Retrieve other fields as needed
                    Timestamp timestamp = document.getTimestamp("timestamp");
                    String schedule = document.getString("schedule");
                    String location = document.getString("location");
                    String level = document.getString("level");

                    // Do something with the retrieved fields
                    Log.d("Firestores", "Name: " + name);
                    Log.d("Firestores", "UID: " + uid);
                    Log.d("Firestores", "Status: " + status);
                    Log.d("Firestores", "timestamp: " + timestamp);
                    Log.d("Firestores", "schedule: " + schedule);
                    Log.d("Firestores", "location: " + status);
                    Log.d("Firestores", "level: " + level);
                    Log.d("Firestores", "location: " + location);

                    TextView clientName = getView().findViewById(R.id.clientName);
                    clientName.setText(name);
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