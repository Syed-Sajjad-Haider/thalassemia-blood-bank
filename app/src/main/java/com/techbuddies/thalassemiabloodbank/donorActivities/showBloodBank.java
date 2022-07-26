package com.techbuddies.thalassemiabloodbank.donorActivities;

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
import com.techbuddies.thalassemiabloodbank.Adapters.showbloodBankListAdapter;
import com.techbuddies.thalassemiabloodbank.Adapters.showbloodReqDonorAdapter;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.BloodBanklistData;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import java.util.ArrayList;

public class showBloodBank extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<BloodBanklistData> bloodBanklistDataArrayList;
    showbloodBankListAdapter adapter;
    ShimmerFrameLayout ShimmerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blood_bank);

        databaseReference = FirebaseDatabase.getInstance().getReference("BloodBanksDetails");

        ShimmerLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        ShimmerLayout.startShimmer(); // If auto-start is set to false

        recyclerView = findViewById(R.id.bloodBanksList);
        recyclerView.setLayoutManager( new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        readdata();
    }

    private void readdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodBanklistDataArrayList = new ArrayList<BloodBanklistData>();
                for (DataSnapshot bankList : dataSnapshot.getChildren()) {

                    BloodBanklistData banklistData = bankList.getValue(BloodBanklistData.class);
                    //get firebase otp unique id

                    bloodBanklistDataArrayList.add(banklistData);
                }

                ShimmerLayout.stopShimmer();
                ShimmerLayout.setVisibility(View.GONE);

                adapter = new showbloodBankListAdapter(showBloodBank.this, bloodBanklistDataArrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(showBloodBank.this,"Oopps Error!",Toast.LENGTH_SHORT).show();
                Log.d("--", "onCancelled: " + databaseError);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}