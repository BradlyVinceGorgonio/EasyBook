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

import java.util.ArrayList;
import java.util.List;

public class AllTrainersFragment extends Fragment implements UserAdapter.OnItemClickListener {

    private List<TrainerClass> trainerList;
    private UserAdapter trainerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trainers, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trainerList = new ArrayList<>();
        trainerAdapter = new UserAdapter(getContext(), trainerList, this);
        recyclerView.setAdapter(trainerAdapter);

        // Fetch data from Firestore
        fetchDataFromFirestore();

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


    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trainer")
                .orderBy("satisfied_users", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        trainerList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String description = document.getString("description");
                            String uid = document.getString("uid");

                            // Check if "satisfied_users" field exists and has a valid value
                            long satisfiedUsers = 0; // Default value if field is missing or null
                            if (document.contains("satisfied_users")) {
                                Object value = document.get("satisfied_users");
                                if (value instanceof Long) {
                                    satisfiedUsers = (long) value;
                                }
                            }

                            TrainerClass trainer = new TrainerClass(name, "Satisfied Clients: " + satisfiedUsers, description, uid);
                            trainerList.add(trainer);
                        }
                        trainerAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the failure scenario
                    }
                });
    }



}
