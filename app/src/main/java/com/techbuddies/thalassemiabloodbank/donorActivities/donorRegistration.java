package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techbuddies.thalassemiabloodbank.PatientActivities.pateintsregistration;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.donorProfileStatus;
import com.techbuddies.thalassemiabloodbank.models.donorRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.models.patientProfileStatus;

import java.util.Calendar;

public class donorRegistration extends AppCompatActivity {


    public static String SstatusForUpdateData = "0";
    //variable declaration
    TextView gotosignin;
    EditText edittxtName , edittxtfatherName,  edittxtPhoneNo , edittxtPassword , edittxtAddress , edittxtDOB , edittextHBLevel;
    Spinner bloodgroupDropdown , genderDropdown;

    CheckBox showpasscheck;
    Button btnRegisterationSubmit;
    private int mYear, mMonth, mDay;

    DatabaseReference databaseReference;
    DatabaseReference dRef;

    String donorid , donorName , donorFname , donorGender , donorDOB , donorPhoneNo , donorPassword , donorAddress , donorBloodGrp , donorHBLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Donor_Details");

        dRef = FirebaseDatabase.getInstance().getReference().child("DonorProfileStatus");

        //text view
        gotosignin = findViewById(R.id.gotosignin);
        //edit text
        edittxtName = findViewById(R.id.edittxtName);
        edittxtfatherName = findViewById(R.id.edittxtfatherName);
        edittxtPhoneNo = findViewById(R.id.edittxtPhoneNo);
        edittxtPassword = findViewById(R.id.edittxtPassword);
        edittxtAddress = findViewById(R.id.edittxtAddress);
        edittxtDOB = findViewById(R.id.edittxtDOB);
        edittextHBLevel = findViewById(R.id.edittextHBLevel);
        showpasscheck = findViewById(R.id.showpasscheck);

        showpassword();

        //Spinner
        bloodgroupDropdown = findViewById(R.id.bloodgroupDropdown);
        genderDropdown = findViewById(R.id.genderDropdown);

        /*//checkbox
        chckAvaiabletoDonate = findViewById(R.id.chckAvaiabletoDonate);*/

        //button
        btnRegisterationSubmit = findViewById(R.id.btnRegisterationSubmit);

        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(donorRegistration.this , donorLogin.class));

            }
        });

        //blood group drowpdown
        String[] bloodGroupDropdwon = getResources().getStringArray(R.array.Blood_Group);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spineritems, bloodGroupDropdwon);
        arrayAdapter.setDropDownViewResource(R.layout.spineritems);
        bloodgroupDropdown.setAdapter(arrayAdapter);
        //end

        //blood group drowpdown
        String[] genderDropdown1 = getResources().getStringArray(R.array.Gender);
        ArrayAdapter arrayAdaptergndr = new ArrayAdapter(this, R.layout.spineritems, genderDropdown1);
        arrayAdapter.setDropDownViewResource(R.layout.spineritems);
        genderDropdown.setAdapter(arrayAdaptergndr);
        //end

        btnRegisterationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrationDataInsertintoDatabase();
            }
        });

        edittxtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseDate();
            }
        });

        if (SstatusForUpdateData.equals("0"))
        {
            Log.d("--", "onCreate: " + "status 0");
        }
        if (SstatusForUpdateData.equals("1"))
        {
            getDataforupdate();
            btnRegisterationSubmit.setText("Update");
            gotosignin.setVisibility(View.GONE);
        }
    }

    private void registrationDataInsertintoDatabase()
    {
        String donorName = edittxtName.getText().toString().trim();
        String donorfName = edittxtfatherName.getText().toString().trim();
        String donorgndr = genderDropdown.getSelectedItem().toString().trim();
        String donorDOB = edittxtDOB.getText().toString().trim();
        String donorPhone = edittxtPhoneNo.getText().toString().trim();
        String donorPassword = edittxtPassword.getText().toString().trim();
        String donorAddress = edittxtAddress.getText().toString().trim();
        String donorBloodgrp = bloodgroupDropdown.getSelectedItem().toString().trim();
        String HBLevel = edittextHBLevel.getText().toString().trim();

        if (donorName.isEmpty())
        {
            edittxtName.setError("Please enter name");
            edittxtName.requestFocus();
        }
        else if (donorfName.isEmpty())
        {
            edittxtfatherName.setError("Please your enter father name");
            edittxtfatherName.requestFocus();
        }
        else if (donorDOB.isEmpty())
        {
            edittxtDOB.setError("Please select Date of birth");
            edittxtDOB.requestFocus();
        }
        else if (donorPhone.isEmpty())
        {
            edittxtPhoneNo.setError("Please enter phone number");
            edittxtPhoneNo.requestFocus();
        }
        else if (donorPassword.isEmpty())
        {
            edittxtPassword.setError("Please enter password");
            edittxtPassword.requestFocus();
        }
        else if (donorAddress.isEmpty())
        {
            edittxtAddress.setError("Please enter address");
            edittxtAddress.requestFocus();
        }
        else if (HBLevel.isEmpty())
        {
            edittextHBLevel.setError("Please enter HB Level");
            edittextHBLevel.requestFocus();
        }
        if (!donorName.isEmpty() && !donorfName.isEmpty() && !donorDOB.isEmpty() && !donorPhone.isEmpty()
                && !donorPassword.isEmpty() && !donorAddress.isEmpty() && !HBLevel.isEmpty()) {

            //get firebase otp unique id
            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (SstatusForUpdateData.equals("0")) {

                //Record insert into Database
                donorRegistrationmodel donorRegistration = new donorRegistrationmodel(currentuser, donorName, donorfName, donorgndr
                        , donorDOB, donorPhone, donorPassword, donorAddress, donorBloodgrp, HBLevel);

                databaseReference.child(currentuser).setValue(donorRegistration).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            //add profile status
                            AddStatusProfile();
                            //end
                            Toast.makeText(donorRegistration.this, "Registration Successfull!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(donorRegistration.this, donorLogin.class));
                            finish();
                        } else {
                            Toast.makeText(donorRegistration.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else if (SstatusForUpdateData.equals("1"))
            {
                //Record insert into Database
                donorRegistrationmodel donorRegistration = new donorRegistrationmodel(currentuser, donorName, donorfName, donorgndr
                        , donorDOB, donorPhone, donorPassword, donorAddress, donorBloodgrp, HBLevel);
                databaseReference.child(currentuser).setValue(donorRegistration).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(donorRegistration.this, "Profile Update Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(donorRegistration.this, donorProfile.class));
                            finish();
                            SstatusForUpdateData = "0";
                        } else {
                            Toast.makeText(donorRegistration.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    //method for open calender dialog
    public  void chooseDate()
    {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edittxtDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showpassword()
    {
        showpasscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    //show password
                    edittxtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //hide password
                    edittxtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void getDataforupdate() {

        Bundle bundle = getIntent().getExtras();
        donorid = bundle.getString("id");
        donorName = bundle.getString("donorName");
        donorFname = bundle.getString("donorFname");
        donorGender = bundle.getString("donorGender");
        donorDOB = bundle.getString("donorDOB");
        donorPhoneNo = bundle.getString("donorPhoneNo");
        donorPassword = bundle.getString("donorPassword");
        donorAddress = bundle.getString("donorAddress");
        donorBloodGrp = bundle.getString("donorBloodGrp");
        donorHBLevel = bundle.getString("donorHBLevel");

        edittxtName.setText(donorName);
        edittxtfatherName.setText(donorFname);
        edittxtDOB.setText(donorDOB);
        edittxtPhoneNo.setText(donorPhoneNo);
        edittxtPassword.setText(donorPassword);
        edittxtAddress.setText(donorAddress);
        edittextHBLevel.setText(donorHBLevel);

    }

    public void AddStatusProfile()
    {
        //get firebase otp unique id
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        donorProfileStatus profileStatus = new donorProfileStatus(currentuser , "0");
        dRef.child(currentuser).setValue(profileStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    //Toast.makeText(donorRegistration.this, "Update", Toast.LENGTH_SHORT).show();
                    Log.d("--", "onComplete: " + "Update");
                }
            }
        });
    }
}