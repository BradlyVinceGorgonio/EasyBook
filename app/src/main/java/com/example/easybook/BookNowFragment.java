package com.example.easybook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.concurrent.atomic.AtomicReference;

public class BookNowFragment extends Fragment {

    Button confirmBook;

    EditText medicalconditionET,  goalET, facilityadd;
    CheckBox bookTrainer, bookFacility;
    private RadioGroup radioGroupSchedule;


    private String trainerId;
    private String selectedLevel;
    private String selectedSchedule;
    private String scheduleFunction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_book_now, container, false);

        confirmBook = (Button) view.findViewById(R.id.confirmBook);
        //Initialize mo muna
        radioGroupSchedule = view.findViewById(R.id.radioGroup);



        RadioGroup radioGroupLevel = view.findViewById(R.id.radio_group);
        final String[] selectedOption = {""};

        medicalconditionET = (EditText) view.findViewById(R.id.medicalconditionET);
        bookTrainer = (CheckBox) view.findViewById(R.id.booktrainer);
        bookFacility = (CheckBox) view.findViewById(R.id.bookfacility);
        medicalconditionET = (EditText) view.findViewById(R.id.medicalconditionET);
        facilityadd = (EditText) view.findViewById(R.id.facilityadd);
        radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                selectedOption[0] = radioButton.getText().toString();
                Log.d("Hello", "Im the selected option " + selectedOption[0]);
            }
        });

        Log.d("Hello", "Im the selected option " + selectedOption[0]);
        String selectedLevel = selectedOption[0];
        Log.d("Hello", "Im the selected option " + selectedLevel);



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
                            scheduleFunction = DisplayRadioButton(scheduleDays, radioGroupSchedule, requireContext());


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
            public void onClick(View v) {
                // Pass the trainerID again to the fragment
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

                                    // Getting the Trainer's information
                                    if (getArguments() != null) {
                                        trainerId = getArguments().getString("trainerId");
                                    }

                                    String name = documentSnapshot.getString("name");
                                    String uid = documentSnapshot.getString("uid");
                                    String selectedLevel = selectedOption[0];
                                    String age = documentSnapshot.getString("age");
                                    String gender = documentSnapshot.getString("gender");
                                    String contact = documentSnapshot.getString("contact number");



                                    addToBookingRequestToTrainer(trainerId, name, uid, scheduleFunction, selectedLevel, age, gender, contact);

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



    public String DisplayRadioButton(List<String> scheduleDays, RadioGroup radioGroup, Context context) {
        final AtomicReference<String> selectedSchedule = new AtomicReference<>(scheduleDays.get(0)); // Set default selected schedule

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
                String schedule = selectedRadioButton.getText().toString();

                selectedSchedule.set(schedule); // Update the AtomicReference

                Log.d("Hello", "selected Option in the Schedule: " + schedule);
                // Implement the logic to store the value in your chosen database
            }
        });

        String finalSelectedSchedule = selectedSchedule.get(); // Retrieve the selected schedule from AtomicReference
        Log.d("Hello", "Returned schedule: " + finalSelectedSchedule);
        return finalSelectedSchedule;
    }




    public void addToBookingRequestToTrainer(String trainerDocumentId, String name, String uid, String schedule, String level, String age, String gender, String contact)
    {
        String medicalCondition = medicalconditionET.getText().toString();
        String facilityAddress = facilityadd.getText().toString();

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
        bookingData.put("schedule", schedule);
        bookingData.put("level", level);
        bookingData.put("age", age);
        bookingData.put("gender", gender);
        bookingData.put("contact number", contact);
        //goal
        bookingData.put("gender", gender);
        //location
        bookingData.put("location", facilityAddress);
        bookingData.put("timestamp", FieldValue.serverTimestamp());

        // Add the booking data to the document
        bookingRequestRef.set(bookingData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle("Booking Successful");
                        builder.setMessage("Congratulations! Your booking request has been submitted successfully. The trainer will review your request and send you a confirmation message shortly. Please keep an eye on your inbox for updates.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle OK button click
                                dialog.dismiss();
                                Intent intent = new Intent(requireContext(), HomeFragmentsActivity.class);
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

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