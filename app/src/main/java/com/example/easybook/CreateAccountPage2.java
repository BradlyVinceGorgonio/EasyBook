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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountPage2 extends AppCompatActivity {
    EditText  name, lastName, age, gender, contactNumber, address, city, Zipcode;


    private static final String TAG = "CreateAccountPage2";
    private FirebaseAuth mAuth;

    Button registerBtn;

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
        registerBtn = (Button) findViewById(R.id.registerBtn);

        String nameText = name.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String fullname = nameText + " " + lastNameText;
        String ageText = age.getText().toString().trim();
        String genderText = gender.getText().toString().trim();
        String contactText = contactNumber.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String cityText = city.getText().toString().trim();
        String zipcodeText = Zipcode.getText().toString().trim();


        boolean allTextFilled = false;

        if(!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(lastNameText) && !TextUtils.isEmpty(ageText) && !TextUtils.isEmpty(genderText) && !TextUtils.isEmpty(contactText) && !TextUtils.isEmpty(addressText) && !TextUtils.isEmpty(cityText) && !TextUtils.isEmpty(zipcodeText))
        {
            allTextFilled = true;
        }

        registerBtn.setClickable(allTextFilled);


        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {



                Intent intent = getIntent();
                if (intent != null) {
                    String username = intent.getStringExtra("username");
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");

                    String nameText = name.getText().toString().trim();
                    String lastNameText = lastName.getText().toString().trim();
                    String fullname = nameText + " " + lastNameText;
                    String ageText = age.getText().toString().trim();
                    String genderText = gender.getText().toString().trim();
                    String contactText = contactNumber.getText().toString().trim();
                    String addressText = address.getText().toString().trim();
                    String cityText = city.getText().toString().trim();
                    String zipcodeText = Zipcode.getText().toString().trim();

                    createAccount(email, password, username, nameText, lastNameText, fullname, ageText, genderText, contactText,
                            addressText, cityText, zipcodeText);
                    //Lagay mo sa DB lahat ng user credentials

                }



            }
        });
    }

    public void addCollection(String uid, String username,String name, String lastName, String fullname, String age, String gender, String contactNum, String Address, String city, String zipcode)
    {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);  // Include the UID
        user.put("username", username);
        user.put("first name", name);
        user.put("last name", lastName);
        user.put("name", fullname);
        user.put("age", age);
        user.put("currentTrainer", null);
        user.put("gender", gender);
        user.put("contact number", contactNum);
        user.put("Address", Address);
        user.put("City", city);
        user.put("Zipcode", zipcode);



        // Add a new document with a generated ID
        db.collection("costumer")
                .document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added for UID: " + uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document for UID: " + uid, e);
                    }
                });

    }

    private void createAccount(String email, String password, String username, String Fname, String lastName, String fullname, String age, String gender, String contactNum, String address, String city, String zipcode) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            addCollection(uid, username, Fname, lastName, fullname, age, gender, contactNum, address, city, zipcode);

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
        Intent intent = new Intent(CreateAccountPage2.this, HomeFragmentsActivity.class);
        intent.putExtra("user", user);
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
        Intent intent = new Intent(CreateAccountPage2.this, HomeFragmentsActivity.class);
        startActivity(intent);
    }
}