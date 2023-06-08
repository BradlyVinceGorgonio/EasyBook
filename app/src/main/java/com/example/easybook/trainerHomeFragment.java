package com.example.easybook;

import android.os.Bundle;
import android.util.Log;
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
        String clientUid = client.getUid(); // Assuming BookRequestClass has a getId() method to retrieve the document ID
        TrainerDescriptionFragment trainerDescriptionFragment = TrainerDescriptionFragment.newInstance(clientUid);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.trainer_frame_layout, trainerDescriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fetchDataFromFirestore()
    {
        Log.d("Checker", "fetchDataFromFirestore called");
        // Clear the existing bookList
        bookList.clear();

        // Manually create the BookRequestClass instance
        BookRequestClass trainer = new BookRequestClass("Daniel Keith", "8am-10am", "asdasdasdas", "Moderate", "STI GLOBAL CITY 8th Floor");

        // Add the trainer instance to the bookList
        bookList.add(trainer);
        Log.d("Checker", "BookList size: " + bookList.size());

        // Notify the adapter of the data change
        traineruserAdapter.notifyDataSetChanged();
        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("trainer")
                //Add SubCollections
                .whereArrayContains("category_field", field)
                //.orderBy("satisfied_users", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bookList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String description = document.getString("description");
                            String uid = document.getString("uid");

                            // Check if "satisfied_users" field exists and has a valid value
                            long price = 0; // Default value if field is missing or null
                            if (document.contains("price")) {
                                Object value = document.get("price");
                                if (value instanceof Long) {
                                    price = (long) value;
                                }
                            }

                            BookRequestClass trainer = new BookRequestClass(name, goal, schedule, uid, fitnessLevel); //Fixed =)
                            bookList.add(trainer);
                        }
                        traineruserAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the failure scenario
                    }
                });

         */
    }
}