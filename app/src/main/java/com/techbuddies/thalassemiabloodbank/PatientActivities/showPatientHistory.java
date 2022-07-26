package com.techbuddies.thalassemiabloodbank.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techbuddies.thalassemiabloodbank.Adapters.showPatientHistoryAdapter;
import com.techbuddies.thalassemiabloodbank.Adapters.showbloodReqPatientAdapter;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;
import com.techbuddies.thalassemiabloodbank.models.patientHistory;

import java.util.ArrayList;

public class showPatientHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<patientHistory> patientHistoryArrayList;
    showPatientHistoryAdapter adapter;
    ShimmerFrameLayout ShimmerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patient_history);

        databaseReference = FirebaseDatabase.getInstance().getReference("Patient_History");

        ShimmerLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        ShimmerLayout.startShimmer(); // If auto-start is set to false

        recyclerView = findViewById(R.id.patientHistroyList);
        recyclerView.setLayoutManager( new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        readdata();
    }

    private void readdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientHistoryArrayList = new ArrayList<patientHistory>();
                for (DataSnapshot patientDataHistory : dataSnapshot.getChildren()) {
                    patientHistory patientHistory = patientDataHistory.getValue(patientHistory.class);
                    //get firebase otp unique id
                    final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (patientHistory.getCurrentuID().equals(currentuser)) {
                        patientHistoryArrayList.add(patientHistory);
                    }
                }

                ShimmerLayout.stopShimmer();
                ShimmerLayout.setVisibility(View.GONE);

                adapter = new showPatientHistoryAdapter(showPatientHistory.this, patientHistoryArrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(showPatientHistory.this, "Oopps Error!", Toast.LENGTH_SHORT).show();
                Log.d("--", "onCancelled: " + databaseError);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}