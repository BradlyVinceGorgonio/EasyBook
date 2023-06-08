package com.example.easybook;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FitnessFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        CardView calisthenics = view.findViewById(R.id.CalisthenicsIns);
        CardView athleticIns = view.findViewById(R.id.AthleticIns);
        CardView gymIns = view.findViewById(R.id.GymIns);
        CardView gymnasticsIns = view.findViewById(R.id.GymnasticsIns);

        calisthenics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AllTrainersFragment
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "Calisthenics";
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


        athleticIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "Athletic";
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

        gymIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "Gym";
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

        gymnasticsIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "Gymnastics";
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