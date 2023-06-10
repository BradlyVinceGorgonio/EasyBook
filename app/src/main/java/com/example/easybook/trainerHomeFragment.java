package com.example.easybook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class trainerHomeFragment extends Fragment implements TrainerUserAdapter.OnItemClickListener {

    private List<BookRequestClass> bookList;
    private TrainerUserAdapter traineruserAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTrainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookList = new ArrayList<>();
        traineruserAdapter = new TrainerUserAdapter(getContext(), bookList, this);  // Corrected variable name
        recyclerView.setAdapter(traineruserAdapter);  // Corrected variable name
        Log.d("Checker", "Adapter set to RecyclerView");



            // Use the selectedSport string as needed
            fetchDataFromFirestore();
            Log.d("Checker", "From if else of SelectSport");


        return view;
    }

    @Override
    public void onItemClick(BookRequestClass client) {
        String clientId = client.getUid(); // Assuming BookRequestClass has a getUid() method to retrieve the user ID
        Log.d("client", "onItemClick: " + clientId);
        AcceptRejectTrainerFragment acceptRejectTrainerFragment = AcceptRejectTrainerFragment.newInstance(clientId);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.trainer_frame_layout, acceptRejectTrainerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fetchDataFromFirestore()
    {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        CollectionReference bookingRequestCollectionRef = db.collection("trainer")
                .document(currentUserId)
                .collection("booking_request");

        bookingRequestCollectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        // Clear the existing bookList
                        bookList.clear();
                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                            String bookingRequestId = documentSnapshot.getId();
                            String name = documentSnapshot.getString("name");
                            String status = documentSnapshot.getString("status");
                            Timestamp timestamp = documentSnapshot.getTimestamp("timestamp");
                            String uid = documentSnapshot.getString("uid");
                            String schedule = documentSnapshot.getString("schedule");
                            String location = documentSnapshot.getString("location");
                            String level = documentSnapshot.getString("level");


                            Log.d("Checker", "fetchDataFromFirestore called");


                            // Manually create the BookRequestClass instance
                            BookRequestClass trainer = new BookRequestClass(name, schedule, uid, level, location);

                            // Add the trainer instance to the bookList
                            bookList.add(trainer);
                            Log.d("Checker", "BookList size: " + bookList.size());

                            // Notify the adapter of the data change
                            traineruserAdapter.notifyDataSetChanged();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error retrieving the documents
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