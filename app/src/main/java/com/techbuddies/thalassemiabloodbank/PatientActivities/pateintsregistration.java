package com.techbuddies.thalassemiabloodbank.PatientActivities;

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
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.patientProfileStatus;
import com.techbuddies.thalassemiabloodbank.models.patientRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

import java.util.Calendar;

public class pateintsregistration extends AppCompatActivity {

    Spinner pbloodgroupDropdown,pgenderDropdown;

    EditText edittxtchildNameP , edittxtpguardianName , edittxtpDiseaseType , edittxtpDOB
    , edittxtPphoneNo , edittxtPpassword , edittxtPAddress;

    Button btnpRegisterationSubmit;
    TextView gotosignin;
    CheckBox showpasscheck;
    DatabaseReference dRef;

    public static  String statusForUpdateData = "0";
    private int mYear, mMonth, mDay;

    String pateintID , childName , guardianName , pateintGender , DiseaseType , dob , phoneNumber ,password ,address ,bloodGroup;

    DatabaseReference databaseReference;

    mysharedprefrence mysharedprefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateintsregistration);

        mysharedprefrence = new mysharedprefrence(this);
        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Pateints_Details");

        dRef = FirebaseDatabase.getInstance().getReference().child("PatientProfileStatus");

        edittxtchildNameP = findViewById(R.id.edittxtchildNameP);
        edittxtpguardianName = findViewById(R.id.edittxtpguardianName);
        edittxtpDiseaseType = findViewById(R.id.edittxtpDiseaseType);
        edittxtpDOB = findViewById(R.id.edittxtpDOB);
        edittxtPphoneNo = findViewById(R.id.edittxtPphoneNo);
        edittxtPpassword = findViewById(R.id.edittxtPpassword);
        edittxtPAddress = findViewById(R.id.edittxtPAddress);
        showpasscheck = findViewById(R.id.showpasscheck);

        showpassword();

        pbloodgroupDropdown = findViewById(R.id.pbloodgroupDropdown);
        pgenderDropdown = findViewById(R.id.pgenderDropdown);

        btnpRegisterationSubmit = findViewById(R.id.btnpRegisterationSubmit);

        gotosignin = findViewById(R.id.gotosignin);

        //blood group drowpdown
        String[] bloodGroupDropdwon = getResources().getStringArray(R.array.Blood_Group);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spineritems, bloodGroupDropdwon);
        arrayAdapter.setDropDownViewResource(R.layout.spineritems);
        pbloodgroupDropdown.setAdapter(arrayAdapter);
        //end

        //blood group drowpdown
        String[] genderDropdown1 = getResources().getStringArray(R.array.Gender);
        ArrayAdapter arrayAdaptergndr = new ArrayAdapter(this, R.layout.spineritems, genderDropdown1);
        arrayAdapter.setDropDownViewResource(R.layout.spineritems);
        pgenderDropdown.setAdapter(arrayAdaptergndr);
        //end

        edittxtpDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseDate();
            }
        });

        btnpRegisterationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertPateintDataIntoDatabase();
            }
        });

        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(pateintsregistration.this , patientLogin.class));
            }
        });

        if (statusForUpdateData.equals("0"))
        {
            Log.d("--", "onCreate: " + "status 0");
        }
        if (statusForUpdateData.equals("1"))
        {
            getDataforupdate();
            btnpRegisterationSubmit.setText("Update");
            gotosignin.setVisibility(View.GONE);
        }
    }

    private void InsertPateintDataIntoDatabase()
    {
        //getting value from edittext
        String childName = edittxtchildNameP.getText().toString().trim();
        String guardianName = edittxtpguardianName.getText().toString();
        String pateintGender = pgenderDropdown.getSelectedItem().toString().trim();
        String DiseaseType = edittxtpDiseaseType.getText().toString().trim();
        String dob = edittxtpDOB.getText().toString().trim();
        String phoneNumber = edittxtPphoneNo.getText().toString().trim();
        String password = edittxtPpassword.getText().toString().trim();
        String address = edittxtPAddress.getText().toString().trim();
        String bloodGroup = pbloodgroupDropdown.getSelectedItem().toString().trim();

        //validations
        if (childName.isEmpty())
        {
            edittxtchildNameP.setError("Please enter child name");
            edittxtchildNameP.requestFocus();
        }
        else if (guardianName.isEmpty())
        {
            edittxtpguardianName.setError("Please enter guardian name");
            edittxtpguardianName.requestFocus();
        }
        else if (DiseaseType.isEmpty())
        {
            edittxtpDiseaseType.setError("Please enter disease type");
            edittxtpDiseaseType.requestFocus();
        }
        else if (dob.isEmpty())
        {
            edittxtpDOB.setError("Please enter date of birth");
            edittxtpDOB.requestFocus();
        }
        else if (phoneNumber.isEmpty())
        {
            edittxtPphoneNo.setError("Please enter phone number");
            edittxtPphoneNo.requestFocus();
        }
        else if (password.isEmpty())
        {
            edittxtPpassword.setError("Please enter password");
            edittxtPpassword.requestFocus();
        }
        else if (address.isEmpty())
        {
            edittxtPAddress.setError("Please enter address");
            edittxtPAddress.requestFocus();
        }
        else if (!childName.isEmpty() && !guardianName.isEmpty() && !DiseaseType.isEmpty() && !dob.isEmpty() &&
        !phoneNumber.isEmpty() && !password.isEmpty() && !address.isEmpty())
        {
            //get firebase otp unique id
            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (statusForUpdateData.equals("0")) {
                patientRegistrationmodel pateintRegistrationmodel = new patientRegistrationmodel(currentuser , childName , guardianName , pateintGender
                        , DiseaseType ,dob , phoneNumber ,  password  , address , bloodGroup);

                databaseReference.child(currentuser).setValue(pateintRegistrationmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            //add profilestatus in database
                            AddStatusProfile();
                            //end
                            startActivity(new Intent(pateintsregistration.this, patientLogin.class));
                            finish();
                            Toast.makeText(pateintsregistration.this, "Registration Successfull!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(pateintsregistration.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else if (statusForUpdateData.equals("1"))
            {
                patientRegistrationmodel pateintRegistrationmodel = new patientRegistrationmodel(currentuser , childName , guardianName , pateintGender
                        , DiseaseType ,dob , phoneNumber ,  password  , address , bloodGroup);

                databaseReference.child(currentuser).setValue(pateintRegistrationmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(pateintsregistration.this, PatientProfile.class));
                            finish();
                            statusForUpdateData = "0";
                            Toast.makeText(pateintsregistration.this, "Profile Updated Successfull!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(pateintsregistration.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
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

                        edittxtpDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
                    edittxtPpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //hide password
                    edittxtPpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void getDataforupdate() {
        Bundle bundle = getIntent().getExtras();
        pateintID = bundle.getString("id");
        childName= bundle.getString("childName");
        guardianName = bundle.getString("guardianName");
        pateintGender = bundle.getString("pateintGender");
        dob = bundle.getString("dob");
        phoneNumber = bundle.getString("phoneNumber");
        password = bundle.getString("password");
        address = bundle.getString("address");
        bloodGroup = bundle.getString("bloodGroup");
        DiseaseType = bundle.getString("DiseaseType");

        edittxtchildNameP.setText(childName);
        edittxtpguardianName.setText(guardianName);
        edittxtpDOB.setText(dob);
        edittxtPphoneNo.setText(phoneNumber);
        edittxtPpassword.setText(password);
        edittxtPAddress.setText(address);
        edittxtpDiseaseType.setText(DiseaseType);

    }


    public void AddStatusProfile()
    {
        //get firebase otp unique id
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        patientProfileStatus profileStatus = new patientProfileStatus(currentuser , "0");
        dRef.child(currentuser).setValue(profileStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    //Toast.makeText(pateintsregistration.this, "Update", Toast.LENGTH_SHORT).show();
                    Log.d("--", "onComplete: " + "Update");
                }
            }
        });
    }

}