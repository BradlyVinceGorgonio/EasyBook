package com.example.easybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;

public class CreateAccountPage2 extends AppCompatActivity {
    EditText username,email, password, name, lastName, age, gender, contactNumber, address, city, Zipcode, birthDate;
    private static final String TAG = "CreateAccountPage2";
    Button registerBtn;

    private FirebaseAuth mAuth;

    CreateAccountPage2(EditText username, EditText email, EditText password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page2);

        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
        gender = (EditText) findViewById(R.id.gender);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        Zipcode = (EditText) findViewById(R.id.Zipcode);
        birthDate = (EditText) findViewById(R.id.birthDate);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        String nameText = name.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String ageText = age.getText().toString().trim();
        String genderText = gender.getText().toString().trim();
        String contactText = contactNumber.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String cityText = city.getText().toString().trim();
        String zipcodeText = Zipcode.getText().toString().trim();
        String birthDateText = birthDate.getText().toString().trim();


        boolean allTextFilled = false;

        if(!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(lastNameText) && !TextUtils.isEmpty(ageText) && !TextUtils.isEmpty(genderText) && !TextUtils.isEmpty(contactText) && !TextUtils.isEmpty(addressText) && !TextUtils.isEmpty(cityText) && !TextUtils.isEmpty(zipcodeText) && !TextUtils.isEmpty(birthDateText))
        {
            allTextFilled = true;
        }

        registerBtn.setClickable(allTextFilled);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Semail = email.getText().toString().trim();
                String Spass = password.getText().toString().trim();

                //Lagay mo sa DB lahat ng user credentials
                    createAccount(Semail,Spass);
            }
        });
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountPage2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user)
    {
        Intent intent = new Intent(CreateAccountPage2.this, LoginPage.class);
        //Add constructor to LoginBtn class to place infos
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload()
    {
        Intent intent = new Intent(CreateAccountPage2.this, LoginPage.class);
        startActivity(intent);
    }
}