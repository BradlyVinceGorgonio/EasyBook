package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountPage extends AppCompatActivity
{


    EditText usernameRgtr, emailRgtr, passwordRgtr,repasswordRgtr;
    CheckBox confirmCB;
    Button nextPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);



        usernameRgtr = (EditText) findViewById(R.id.usernameRgtr);
        emailRgtr = (EditText) findViewById(R.id.emailRgtr);
        passwordRgtr = (EditText) findViewById(R.id.passwordRgtr);
        repasswordRgtr = (EditText) findViewById(R.id.repasswordRgtr);
        confirmCB = (CheckBox) findViewById(R.id.confirmCB);
        nextPageBtn = (Button) findViewById(R.id.nextPageBtn);

        confirmCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nextPageBtn.setClickable(!isChecked);
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}