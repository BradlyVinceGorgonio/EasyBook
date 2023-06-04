package com.example.easybook;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class HomeFragmentsActivity extends AppCompatActivity
{

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ChatFragment chatFragment = new ChatFragment();
    MailFragment mailFragment = new MailFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragments);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.emailButton);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(3);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

            {
                fragmentMap.put(R.id.homeButton, homeFragment);
                fragmentMap.put(R.id.messageButton, chatFragment);
                fragmentMap.put(R.id.emailButton, mailFragment);
                fragmentMap.put(R.id.profileButton, profileFragment);
            }

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = fragmentMap.get(item.getItemId());
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });



        /*
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.homeButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment).commit();
                        return true;

                    case R.id.messageButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatFragment).commit();
                        return true;
                    case R.id.emailButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,mailFragment).commit();
                        return true;
                    case R.id.profileButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,mailFragment).commit();
                        return true;
                }

                return false;
            }
        });
        */




    }
}
