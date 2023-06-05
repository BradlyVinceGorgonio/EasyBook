package com.example.easybook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllTrainersFragment extends Fragment {

    private List<TrainerClass> trainerList;
    private UserAdapter trainerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_trainers, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trainerList = new ArrayList<>();
        trainerAdapter = new UserAdapter(getContext(), trainerList);
        recyclerView.setAdapter(trainerAdapter);

        // Fetch data from Firestore
        fetchDataFromFirestore();

        return view;
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trainer")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        trainerList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String description = document.getString("description");

                            TrainerClass trainer = new TrainerClass(name, description);
                            trainerList.add(trainer);
                        }
                        trainerAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the failure scenario
                    }
                });
    }
}
