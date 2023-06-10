package com.example.easybook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookNowFragment extends Fragment {

    Button confirmBook;
    CheckBox Beginner,Experienced,Intermediate,Advanced;
    private RadioGroup radioGroup;


    private String trainerId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_book_now, container, false);

        confirmBook = (Button) view.findViewById(R.id.confirmBook);
        //Initialize mo muna
        radioGroup = view.findViewById(R.id.radioGroup);






        // Get Information from trainer
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            trainerId = getArguments().getString("trainerId");
        }
        Log.d("Taggers", "trainer id " + trainerId);

        db.collection("trainer").document(trainerId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Object scheduleDaysObj = documentSnapshot.get("schedule_day");
                        if (scheduleDaysObj instanceof List) {
                            List<String> scheduleDays = (List<String>) scheduleDaysObj;
                            // Do something with the scheduleDays list
                            Log.d("Schedule", scheduleDays.toString());
                            DisplayRadioButton(scheduleDays, radioGroup, requireContext());
                        } else {
                            // The schedule_day field is null or not found, or it's not of type List<String>
                        }




                    } else {
                        // The document does not exist
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occur while fetching the document
                });





        confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //pass the trainerID again to the fragment
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference customerCollectionRef = db.collection("costumer");
                String currentUserId = firebaseAuth.getCurrentUser().getUid();

                customerCollectionRef.document(currentUserId).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Log.d("IDcheck", "field found");

                                    //Getting the Trainers information

                                    if (getArguments() != null) {
                                        trainerId = getArguments().getString("trainerId");
                                    }


                                    String name = documentSnapshot.getString("name");
                                    String uid = documentSnapshot.getString("uid");
                                    Log.d("IDcheck", "name: " + name);
                                    addToBookingRequestToTrainer(trainerId, name, uid);
                                } else {
                                    Log.d("IDcheck", "document not existent" + currentUserId);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error retrieving the document
                            }
                        });
            }
        });

        return view;
    }



    public void DisplayRadioButton(List<String> scheduleDays, RadioGroup radioGroup, Context context) {
        // Create radio buttons dynamically based on the number of strings in the list
        for (int i = 0; i < scheduleDays.size(); i++) {
            String option = scheduleDays.get(i);

            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(option);
            radioButton.setId(i); // Set a unique ID for each radio button

            radioGroup.addView(radioButton);
        }

        // Set the OnCheckedChangeListener for the RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Retrieve the selected radio button
                RadioButton selectedRadioButton = group.findViewById(checkedId);

                // Get the text value of the selected radio button
                String selectedOption = selectedRadioButton.getText().toString();

                // Store the selectedOption in the chosen database
                // Implement the logic to store the value in your chosen database
            }
        });
    }








    public void addToBookingRequestToTrainer(String trainerDocumentId, String name, String uid)
    {
        Log.d("IDcheck", "name inside function: " + name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trainerCollectionRef = db.collection("trainer");

        DocumentReference trainerDocumentRef = trainerCollectionRef.document(trainerDocumentId);
        Log.d("IDcheck", "addToBookingRequestToTrainer: " + trainerDocumentId + " " + name + " " + uid);


        CollectionReference bookingRequestCollectionRef = trainerDocumentRef.collection("booking_request");


        DocumentReference bookingRequestRef = bookingRequestCollectionRef.document(uid);

        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("name", name);
        bookingData.put("uid", uid);
        bookingData.put("status", "pending");
        //fitness
        //age
        //goal
        //chosen schedule
        //location
        bookingData.put("timestamp", FieldValue.serverTimestamp());

        // Add the booking data to the document
        bookingRequestRef.set(bookingData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document successfully added
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error adding the document
                    }
                });



    }

    public static BookNowFragment newInstance(String trainerId) {
        BookNowFragment fragment = new BookNowFragment();
        Bundle args = new Bundle();
        args.putString("trainerId", trainerId);
        fragment.setArguments(args);
        return fragment;
    }
}