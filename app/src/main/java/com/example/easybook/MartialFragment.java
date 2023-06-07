package com.example.easybook;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class MartialFragment extends Fragment {

    private List<TrainerClass> trainerList;
    private UserAdapter trainerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_martial, container, false);

        CardView judo = view.findViewById(R.id.JudoIns);
        CardView karate = view.findViewById(R.id.KarateIns);
        CardView taekwondo = view.findViewById(R.id.TaekwondoIns);
        CardView boxing = view.findViewById(R.id.BoxingIns);

        judo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AllTrainersFragment
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "judo";
                Bundle args = new Bundle();
                args.putString("selectedSport", selectedSport);
                fragment2.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        karate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "karate";
                Bundle args = new Bundle();
                args.putString("selectedSport", selectedSport);
                fragment2.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        taekwondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "taekwondo";
                Bundle args = new Bundle();
                args.putString("selectedSport", selectedSport);
                fragment2.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




        boxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "boxing";
                Bundle args = new Bundle();
                args.putString("selectedSport", selectedSport);
                fragment2.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




        return view;
    }


}