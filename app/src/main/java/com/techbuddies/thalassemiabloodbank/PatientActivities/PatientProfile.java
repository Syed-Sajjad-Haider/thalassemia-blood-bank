package com.techbuddies.thalassemiabloodbank.PatientActivities;

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
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.patientRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class PatientProfile extends AppCompatActivity {

    TextView txtUserNamechild , txtGuardianNameP , txtGenderP , txtDobP , txtPhoneNoP , txtAddressP , txtbloodP
    , txtDesTypeP;

    Button btneditProfileP;

    String pateintID , childName , guardianName , pateintGender , DiseaseType , dob , phoneNumber ,password ,address ,bloodGroup;

    DatabaseReference databaseReference;
    mysharedprefrence mysharedprefrence;

    ShimmerFrameLayout ShimmerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_profile);

        mysharedprefrence = new mysharedprefrence(this);
        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Pateints_Details");

        txtUserNamechild = findViewById(R.id.txtUserNamechild);
        txtGuardianNameP = findViewById(R.id.txtGuardianNameP);
        txtGenderP = findViewById(R.id.txtGenderP);
        txtDobP = findViewById(R.id.txtDobP);
        txtPhoneNoP = findViewById(R.id.txtPhoneNoP);
        txtAddressP = findViewById(R.id.txtAddressP);
        txtbloodP = findViewById(R.id.txtbloodP);
        txtDesTypeP = findViewById(R.id.txtDesTypeP);

        btneditProfileP = findViewById(R.id.btneditProfileP);

        ShimmerLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        ShimmerLayout.startShimmer(); // If auto-start is set to false

        ReadDataFromDatabase();

        btneditProfileP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendDataRegistrationActivityforUpdate();
            }
        });


    }

    private void ReadDataFromDatabase()
    {
        //get firebase otp unique id
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot pateintData: snapshot.getChildren()) {
                    patientRegistrationmodel pateintRegistrationmodel = pateintData.getValue(patientRegistrationmodel.class);

                    if (pateintRegistrationmodel.getPateintID().equals(currentuser))
                    {
                        pateintID = pateintRegistrationmodel.getPateintID();
                        childName = pateintRegistrationmodel.getChildName();
                        guardianName = pateintRegistrationmodel.getGuardianName();
                        pateintGender = pateintRegistrationmodel.getPateintGender();
                        DiseaseType = pateintRegistrationmodel.getDiseaseType();
                        dob = pateintRegistrationmodel.getDob();
                        phoneNumber = pateintRegistrationmodel.getPhoneNumber();
                        password = pateintRegistrationmodel.getPassword();
                        address = pateintRegistrationmodel.getAddress();
                        bloodGroup = pateintRegistrationmodel.getBloodGroup();
                    }


                    ShimmerLayout.stopShimmer();
                    ShimmerLayout.setVisibility(View.GONE);
                    //set text

                    txtUserNamechild.setText(childName);
                    txtGuardianNameP.setText("Guardian name: "+guardianName);
                    txtGenderP.setText("Gender: " + pateintGender);
                    txtDesTypeP.setText("Disease type: "+DiseaseType);
                    txtDobP.setText("Date of birth: "+dob);
                    txtPhoneNoP.setText("Phone number: "+phoneNumber);
                    txtAddressP.setText("Address: "+address);
                    txtbloodP.setText("Blood group: " +bloodGroup);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendDataRegistrationActivityforUpdate()
    {
        Intent i = new Intent(PatientProfile.this, pateintsregistration.class);
        i.putExtra("id", pateintID);
        i.putExtra("childName", childName);
        i.putExtra("guardianName", guardianName);
        i.putExtra("pateintGender", pateintGender);
        i.putExtra("dob", dob);
        i.putExtra("phoneNumber", phoneNumber);
        i.putExtra("password", password);
        i.putExtra("address", address);
        i.putExtra("bloodGroup", bloodGroup);
        i.putExtra("DiseaseType", DiseaseType);
        pateintsregistration.statusForUpdateData = "1";
        startActivity(i);
    }
}