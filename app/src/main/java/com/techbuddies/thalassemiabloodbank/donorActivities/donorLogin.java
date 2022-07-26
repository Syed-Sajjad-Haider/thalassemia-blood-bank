package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.donorRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class donorLogin extends AppCompatActivity {

    TextView txtgotoreg;
    EditText edittxtdonorPhone , edittxtdonorPass;
    Button btnDonorLogin;
    CheckBox showpasscheck;

    DatabaseReference databaseReference;
    mysharedprefrence mysharedprefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        mysharedprefrence = new mysharedprefrence(this);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Donor_Details");

        txtgotoreg = findViewById(R.id.txtgotoreg);
        edittxtdonorPhone = findViewById(R.id.edittxtdonorPhone);
        edittxtdonorPass = findViewById(R.id.edittxtdonorPass);
        btnDonorLogin = findViewById(R.id.btnDonorLogin);
        showpasscheck = findViewById(R.id.showpasscheck);

        txtgotoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(donorLogin.this , donorRegistration.class));
                finish();
            }
        });

        showpasscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showpassword();
            }
        });

        btnDonorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donorlogin();
            }
        });
    }

    private void donorlogin()
    {

        String donorphone = edittxtdonorPhone.getText().toString().trim();
        String donorpassword = edittxtdonorPass.getText().toString().trim();

        if (donorphone.isEmpty())
        {
            edittxtdonorPhone.setError("Please enter phone number");
            edittxtdonorPhone.requestFocus();
        }
        else if (donorpassword.isEmpty())
        {
            edittxtdonorPass.setError("Please enter password");
            edittxtdonorPass.requestFocus();
        }
        else if (!donorphone.isEmpty() && !donorpassword.isEmpty()) {


            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot stdData : dataSnapshot.getChildren()) {

                        donorRegistrationmodel registrationmodel = stdData.getValue(donorRegistrationmodel.class);

                        String phoneFromDB = "";
                        String passfromDB = "";
                        //match value Sharedprefrence or Database
                        if (currentuser.equals(registrationmodel.getDonorid())) {
                            phoneFromDB = registrationmodel.getDonorPhoneNo();
                            passfromDB = registrationmodel.getDonorPassword();

                            if (phoneFromDB.equals(donorphone) && passfromDB.equals(donorpassword)) {
                                startActivity(new Intent(donorLogin.this, DashboardDonor.class));
                                donorLogin.this.finish();
                                Toast.makeText(donorLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                mysharedprefrence.setSigninStatus("1");
                            } else {
                                Toast.makeText(donorLogin.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(donorLogin.this, "Oopps Error!", Toast.LENGTH_SHORT).show();
                    Log.d("--", "onCancelled: " + databaseError);
                }
            });
        }
    }

    private void showpassword()
    {
        showpasscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    //show password
                    edittxtdonorPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //hide password
                    edittxtdonorPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}