package com.example.easybook;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);

        CardView basketball = view.findViewById(R.id.BasketIns);
        CardView badminton = view.findViewById(R.id.BadminIns);
        CardView swimming = view.findViewById(R.id.SwimmingIns);
        CardView volleyball = view.findViewById(R.id.VolleyIns);

        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AllTrainersFragment
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "basketball";
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


        badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "badminton";
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

        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
                String selectedSport = "swimming";
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




        volleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrainersFragment fragment2 = new AllTrainersFragment();
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