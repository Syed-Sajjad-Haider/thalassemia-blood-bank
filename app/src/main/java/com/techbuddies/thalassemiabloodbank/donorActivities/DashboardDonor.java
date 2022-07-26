package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techbuddies.thalassemiabloodbank.PatientActivities.dashboardPatient;
import com.techbuddies.thalassemiabloodbank.PatientActivities.patientLogin;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.donorProfileStatus;
import com.techbuddies.thalassemiabloodbank.models.donorRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.models.patientProfileStatus;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class DashboardDonor extends AppCompatActivity {


    CardView aboutThalassemiacardview , userProfileCard , cardInviteDonor , CardShowBloodBank , cardlogoutD , cardBloodRequestDonor;
    mysharedprefrence mysharedprefrence;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mysharedprefrence = new mysharedprefrence(this);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DonorProfileStatus");


        aboutThalassemiacardview= findViewById(R.id.aboutThalassemiacardview);
        userProfileCard= findViewById(R.id.userProfileCard);
        cardInviteDonor= findViewById(R.id.cardInviteDonor);
        CardShowBloodBank= findViewById(R.id.CardShowBloodBank);
        cardlogoutD= findViewById(R.id.cardlogoutD);
        cardBloodRequestDonor= findViewById(R.id.cardBloodRequestDonor);

        //check user exists in Database
        checkUserInDB();

        aboutThalassemiacardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardDonor.this , aboutThalasemia.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        CardShowBloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardDonor.this , showBloodBank.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardBloodRequestDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardDonor.this ,showBloodRequest.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardlogoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //logout functionality
                                startActivity(new Intent(DashboardDonor.this , donorLogin.class));
                                finish();
                                mysharedprefrence.setSigninStatus("0");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardDonor.this);
                builder.setMessage("Are you sure you want to Logout ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        userProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardDonor.this , donorProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardInviteDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
    }

    private void shareApp()
    {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        startActivity(Intent.createChooser(sendInt, "Share"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkUserInDB()
    {
        //get firebase otp unique id
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot statusDB: snapshot.getChildren())
                {
                    donorProfileStatus profileStatus = statusDB.getValue(donorProfileStatus.class);
                    if (currentuser.equals(profileStatus.getCurrentUserID()))
                    {
                        if (profileStatus.getStatus().equals("1"))
                        {
                            startActivity(new Intent(DashboardDonor.this , patientLogin.class));
                            mysharedprefrence.setSigninStatus("0");
                            finish();
                            Toast.makeText(DashboardDonor.this, "Sorry! your account deleted by admin", Toast.LENGTH_LONG).show();
                        }
                        else if (profileStatus.getStatus().equals("0"))
                        {
                            Log.d("--", "onDataChange: " + "Data Found in Database");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}