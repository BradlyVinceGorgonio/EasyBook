package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


public class HomeFragmentsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragments);



    }
}


















/*
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

