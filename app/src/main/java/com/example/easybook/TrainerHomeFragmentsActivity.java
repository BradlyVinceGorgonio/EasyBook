package com.example.easybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class TrainerHomeFragmentsActivity extends AppCompatActivity
{

    BottomNavigationView bottomNavigationView;

    trainerHomeFragment trainerHomeFragment = new trainerHomeFragment();
    trainerChatFragment trainerChatFragment = new trainerChatFragment();
    MailFragment mailFragment = new MailFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home_fragments);

        bottomNavigationView = findViewById(R.id.trainerBottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame_layout, trainerHomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homeButtonTrainer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame_layout, trainerHomeFragment).commit();
                    return true;
                } else if (itemId == R.id.messageButtonTrainer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame_layout, trainerChatFragment).commit();
                    return true;
                } else if (itemId == R.id.emailButtonTrainer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame_layout, mailFragment).commit();
                    return true;
                } else if (itemId == R.id.profileButtonTrainer) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.trainer_frame_layout, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });


        //FirebaseAuth.getInstance().signOut();
    }
}