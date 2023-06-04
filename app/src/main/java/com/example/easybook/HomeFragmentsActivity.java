package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.easybook.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragmentsActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private MailFragment inboxFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragments);


        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Initialize fragments
        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        inboxFragment = new MailFragment();
        profileFragment = new ProfileFragment();



        // Add the home fragment initially
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, homeFragment)
                .commit();

        // Set the listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.homeButton) {
                selectedFragment = homeFragment;
            } else if (item.getItemId() == R.id.messageButton) {
                selectedFragment = chatFragment;
            } else if (item.getItemId() == R.id.emailButton) {
                selectedFragment = inboxFragment;
            } else if (item.getItemId() == R.id.profileButton) {
                selectedFragment = profileFragment;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
                return true;
            }

            return false;
        });
    }
}
