package com.example.easybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class HomeFragmentsActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ChatFragment chatFragment = new ChatFragment();
    MailFragment mailFragment = new MailFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    AllTrainersFragment allTrainersFragment = new AllTrainersFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MyApp", "Bago mag crash");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragments);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Log.d("MyApp", "getSupport");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();


        /*
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.emailButton);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(3);
        */


        Log.d("MyApp", "Na mag crash");
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homeButton) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.messageButton) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, chatFragment).commit();
                    return true;
                } else if (itemId == R.id.emailButton) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mailFragment).commit();
                    return true;
                } else if (itemId == R.id.profileButton) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });


    }
}


















/*

        */

