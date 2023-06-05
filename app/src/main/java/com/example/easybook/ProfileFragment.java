package com.example.easybook;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.easybook.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private LinearLayout llButtonsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        llButtonsContainer = view.findViewById(R.id.llButtonsContainer);

        String[] buttonNames = {
                "User Settings",
                "Membership",
                "Terms and Conditions",
                "Contact Us",
                "Apply as a Customer Trainer",
                "Change Password",
                "Logout"
        };

        for (String name : buttonNames) {
            Button button = new Button(requireContext()); // Use requireContext() instead of 'this'
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            button.setText(name);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(v -> {
                // Button click event handling
            });

            llButtonsContainer.addView(button);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.setMargins(10, 10, 10, 10); // Add margin between buttons
            button.setLayoutParams(layoutParams);
        }

        return view;
    }

    private void handleButtonClick(String buttonName) {
        Fragment fragment = null; // Initialize with a default value

        // Create the appropriate fragment based on the button clicked
        switch (buttonName) {
            case "User Settings":
                //fragment = new UserSettingsFragment();
                break;
            case "Membership":
                //fragment = new MembershipFragment();
                break;
            case "Terms and Conditions":
                //fragment = new TermsAndConditionsFragment();
                break;
            case "Contact Us":
                //fragment = new ContactUsFragment();
                break;
            case "Apply as a Customer Trainer":
                fragment = new ApplyAsCostumerTrainerFragment();
                break;
            case "Change Password":
                //fragment = new ChangePasswordFragment();
                break;
            case "Logout":
                // Perform logout action
                logout();
                return;
            default:
                // Handle the case where no fragment is selected
                return;
        }

        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null); // Add to back stack if needed
        transaction.commit();
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
