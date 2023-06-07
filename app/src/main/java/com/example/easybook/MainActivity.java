package com.example.easybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import static com.google.firebase.inappmessaging.internal.Logging.TAG;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";
    EditText emailLgn, passLgn;
    Button loginBtn, createAcsc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        emailLgn = (EditText) findViewById(R.id.emailLgn);
        passLgn = (EditText) findViewById(R.id.passwordLgn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        createAcsc = (Button) findViewById(R.id.createAccBtnR);


        createAcsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Semail = emailLgn.getText().toString().trim();
                String Spass = passLgn.getText().toString().trim();
                signIn(Semail, Spass);
            }
        });



    }



    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkIfUserIsTrainer(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkIfUserIsTrainer(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trainersCollection = db.collection("trainer");

        trainersCollection.whereEqualTo("uid", user.getUid())
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            boolean isTrainer = !task.getResult().isEmpty();
                            if (isTrainer) {
                                Intent intent = new Intent(MainActivity.this, TrainerHomeFragmentsActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(MainActivity.this, HomeFragmentsActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            // Error occurred while checking if user is a trainer
                            Log.w(TAG, "Error checking if user is a trainer", task.getException());
                        }
                    }
                });
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
        Intent intent = new Intent(MainActivity.this,HomeFragmentsActivity.class);
        startActivity(intent);
    }
}