package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techbuddies.thalassemiabloodbank.PatientActivities.PatientProfile;
import com.techbuddies.thalassemiabloodbank.PatientActivities.patientLogin;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.donorRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class donorProfile extends AppCompatActivity {


    TextView txtUserNamedonor , txtfatherNamedonor , txtGenderdonor , txtDOBDonor , txtPhoneNodonor , txtAddressDonor ,
            txtbloodGroupDonor , txtHBLevelDonor;

    String donorid , donorName , donorFname , donorGender , donorDOB , donorPhoneNo , donorPassword , donorAddress , donorBloodGrp , donorHBLevel;

    Button btneditProfile;

    DatabaseReference databaseReference;

    ShimmerFrameLayout ShimmerLayout;

    mysharedprefrence mysharedprefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        mysharedprefrence = new mysharedprefrence(this);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Donor_Details");

        txtUserNamedonor = findViewById(R.id.txtUserNamedonor);
        txtfatherNamedonor = findViewById(R.id.txtfatherNamedonor);
        txtGenderdonor = findViewById(R.id.txtGenderdonor);
        txtDOBDonor = findViewById(R.id.txtDOBDonor);
        txtPhoneNodonor = findViewById(R.id.txtPhoneNodonor);
        txtAddressDonor = findViewById(R.id.txtAddressDonor);
        txtbloodGroupDonor = findViewById(R.id.txtbloodGroupDonor);
        txtHBLevelDonor = findViewById(R.id.txtHBLevelDonor);
        btneditProfile = findViewById(R.id.btneditProfile);

        ShimmerLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        ShimmerLayout.startShimmer(); // If auto-start is set to false

        ReadDataFromDatabase();

        btneditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataRegistrationActivityforUpdate();
            }
        });
    }

    private void sendDataRegistrationActivityforUpdate()
    {
        Intent i = new Intent(donorProfile.this, donorRegistration.class);
        i.putExtra("id", donorid);
        i.putExtra("donorName", donorName);
        i.putExtra("donorFname", donorFname);
        i.putExtra("donorGender", donorGender);
        i.putExtra("donorDOB", donorDOB);
        i.putExtra("donorPhoneNo", donorPhoneNo);
        i.putExtra("donorPassword", donorPassword);
        i.putExtra("donorAddress", donorAddress);
        i.putExtra("donorBloodGrp", donorBloodGrp);
        i.putExtra("donorHBLevel", donorHBLevel);
        donorRegistration.SstatusForUpdateData = "1";
        startActivity(i);
    }

    private void ReadDataFromDatabase()
    {
        //get firebase otp unique id
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot donorData: snapshot.getChildren())
                {
                    donorRegistrationmodel donorRegistrationmodels = donorData.getValue(donorRegistrationmodel.class);
                    if (donorRegistrationmodels.getDonorid().equals(currentuser)) {
                        donorid = donorRegistrationmodels.getDonorid();
                        donorName = donorRegistrationmodels.getDonorName();
                        donorFname = donorRegistrationmodels.getDonorFname();
                        donorGender = donorRegistrationmodels.getDonorGender();
                        donorDOB = donorRegistrationmodels.getDonorDOB();
                        donorPhoneNo = donorRegistrationmodels.getDonorPhoneNo();
                        donorPassword = donorRegistrationmodels.getDonorPassword();
                        donorAddress = donorRegistrationmodels.getDonorAddress();
                        donorBloodGrp = donorRegistrationmodels.getDonorBloodGrp();
                        donorHBLevel = donorRegistrationmodels.getDonorHBLevel();
                    }
                }


                ShimmerLayout.stopShimmer();
                ShimmerLayout.setVisibility(View.GONE);
                //set text
                txtUserNamedonor.setText(donorName);
                txtfatherNamedonor.setText("Father name: "+donorFname);
                txtGenderdonor.setText("Gender: "+donorGender);
                txtDOBDonor.setText("Date of birth: "+donorDOB);
                txtPhoneNodonor.setText("Phone number: "+donorPhoneNo);
                txtAddressDonor.setText("Address: "+donorAddress);
                txtbloodGroupDonor.setText("Blood group: "+donorBloodGrp);
                txtHBLevelDonor.setText("HB level: "+donorHBLevel);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}