package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

        String userText = usernameRgtr.getText().toString().trim();
        String emailText = emailRgtr.getText().toString().trim();
        String passwordText = passwordRgtr.getText().toString().trim();
        String repasswordText = repasswordRgtr.getText().toString().trim();

        confirmCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean allTextFilled = false;

                if(passwordRgtr.equals(repasswordRgtr) && !TextUtils.isEmpty(userText) && !TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(repasswordText))
                {
                    allTextFilled = true;
                }

                nextPageBtn.setClickable(isChecked || allTextFilled);
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CreateAccountPage.this, CreateAccountPage2.class);
                intent.putExtra("username", usernameRgtr.getText().toString().trim());
                intent.putExtra("email", emailRgtr.getText().toString().trim());
                intent.putExtra("password", passwordRgtr.getText().toString().trim());
                startActivity(intent);

            }
        });
    }
}