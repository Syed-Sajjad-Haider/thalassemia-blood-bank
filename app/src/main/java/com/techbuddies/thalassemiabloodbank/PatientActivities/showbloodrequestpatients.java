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
import com.techbuddies.thalassemiabloodbank.Adapters.showbloodReqDonorAdapter;
import com.techbuddies.thalassemiabloodbank.Adapters.showbloodReqPatientAdapter;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.donorActivities.showBloodRequest;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import java.util.ArrayList;

public class showbloodrequestpatients extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<bloodRequest> bloodRequestArrayList;
    showbloodReqPatientAdapter adapter;
    ShimmerFrameLayout ShimmerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbloodrequestpatients);

        databaseReference = FirebaseDatabase.getInstance().getReference("Blood_Request");

        ShimmerLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        ShimmerLayout.startShimmer(); // If auto-start is set to false

        recyclerView = findViewById(R.id.bloodRequestListP);
        recyclerView.setLayoutManager( new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        readdata();
    }

    private void readdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodRequestArrayList = new ArrayList<bloodRequest>();
                for (DataSnapshot bloodreData : dataSnapshot.getChildren()) {
                    bloodRequest bloodRequest = bloodreData.getValue(bloodRequest.class);
                    //get firebase otp unique id
                    final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (bloodRequest.getCurrentuserID().equals(currentuser)) {
                        bloodRequestArrayList.add(bloodRequest);
                    }
                }

                ShimmerLayout.stopShimmer();
                ShimmerLayout.setVisibility(View.GONE);
                adapter = new showbloodReqPatientAdapter(showbloodrequestpatients.this, bloodRequestArrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(showbloodrequestpatients.this,"Oopps Error!",Toast.LENGTH_SHORT).show();
                Log.d("--", "onCancelled: " + databaseError);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}