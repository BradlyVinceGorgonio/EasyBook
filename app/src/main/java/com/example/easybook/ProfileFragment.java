package com.example.easybook;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.easybook.MainActivity;
import com.example.easybook.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private LinearLayout llButtonsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            Button button = new Button(requireContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            button.setText(name);
            button.setTextSize(20);
            button.setTextColor(Color.BLACK);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right_arrow, 0);
            button.setCompoundDrawablePadding(10);
            button.setBackgroundColor(Color.WHITE);
            button.setOnClickListener(v -> handleButtonClick(name));

            llButtonsContainer.addView(button);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.setMargins(0, 10, 0, 10);
            button.setLayoutParams(layoutParams);
        }

        return view;
    }

    private void handleButtonClick(String buttonName) {
        Fragment fragment = null;

        switch (buttonName) {
            case "User Settings":
                // fragment = new UserSettingsFragment();
                break;
            case "Membership":
                // fragment = new MembershipFragment();
                break;
            case "Terms and Conditions":
                // fragment = new TermsAndConditionsFragment();
                break;
            case "Contact Us":
                // fragment = new ContactUsFragment();
                break;
            case "Apply as a Customer Trainer":
                fragment = new ApplyAsCostumerTrainerFragment();
                break;
            case "Change Password":
                // fragment = new ChangePasswordFragment();
                break;
            case "Logout":
                logout();
                return;
            default:
                return;
        }

        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
