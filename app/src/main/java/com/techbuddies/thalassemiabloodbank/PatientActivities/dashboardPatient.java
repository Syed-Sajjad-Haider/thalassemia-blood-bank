package com.techbuddies.thalassemiabloodbank.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.donorActivities.aboutThalasemia;
import com.techbuddies.thalassemiabloodbank.donorActivities.showBloodBank;
import com.techbuddies.thalassemiabloodbank.models.patientHistory;
import com.techbuddies.thalassemiabloodbank.models.patientProfileStatus;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class dashboardPatient extends AppCompatActivity {

    CardView userProfileCardP , CardShowBloodBankP , aboutThalassemiacardviewP , cardInvitePateints , postbloodReq , cardPatientHistory;
    ImageView imgLogout;
    mysharedprefrence mysharedprefrence;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pateints);

        mysharedprefrence = new mysharedprefrence(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("PatientProfileStatus");

        userProfileCardP = findViewById(R.id.userProfileCardP);
        CardShowBloodBankP = findViewById(R.id.CardShowBloodBankP);
        aboutThalassemiacardviewP = findViewById(R.id.aboutThalassemiacardviewP);
        cardInvitePateints = findViewById(R.id.cardInvitePateints);
        cardPatientHistory = findViewById(R.id.cardPatientHistory);
        postbloodReq = findViewById(R.id.postbloodReq);
        imgLogout = findViewById(R.id.imgLogout);

        checkUserInDB();

        cardInvitePateints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareApp();
            }
        });

        aboutThalassemiacardviewP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboardPatient.this , aboutThalasemia.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        CardShowBloodBankP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboardPatient.this , showBloodBank.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        postbloodReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboardPatient.this , postBloodRequest.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        userProfileCardP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboardPatient.this , PatientProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardPatientHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboardPatient.this , historyofPatient.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //logout functionality
                                startActivity(new Intent(dashboardPatient.this , patientLogin.class));
                                finish();
                                mysharedprefrence.setCheckStatusPregs("0");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(dashboardPatient.this);
                builder.setMessage("Are you sure you want to Logout ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
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
                    patientProfileStatus profileStatus = statusDB.getValue(patientProfileStatus.class);
                    if (currentuser.equals(profileStatus.getCurrentUserID()))
                    {
                        if (profileStatus.getStatus().equals("1"))
                        {
                            startActivity(new Intent(dashboardPatient.this , patientLogin.class));
                            mysharedprefrence.setCheckStatusPregs("0");
                            finish();
                            Toast.makeText(dashboardPatient.this, "Sorry! your account deleted by admin", Toast.LENGTH_LONG).show();
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

    private void shareApp()
    {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        startActivity(Intent.createChooser(sendInt, "Share"));
    }


}