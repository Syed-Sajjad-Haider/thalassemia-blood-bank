package com.techbuddies.thalassemiabloodbank.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.techbuddies.thalassemiabloodbank.models.patientRegistrationmodel;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class patientLogin extends AppCompatActivity {


    TextView txtgotoreg;
    EditText edittxtpateintPhone, edittxtpateintPass;
    Button btnpateintLogin;
    CheckBox showpasscheck;

    DatabaseReference databaseReference;
    mysharedprefrence mysharedprefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_login);

        mysharedprefrence = new mysharedprefrence(this);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Pateints_Details");

        txtgotoreg = findViewById(R.id.txtgotoreg);
        edittxtpateintPhone = findViewById(R.id.edittxtpateintPhone);
        edittxtpateintPass = findViewById(R.id.edittxtpateintPass);
        btnpateintLogin = findViewById(R.id.btnpateintLogin);
        showpasscheck = findViewById(R.id.showpasscheck);
        txtgotoreg = findViewById(R.id.txtgotoreg);

        btnpateintLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

        showpasscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showpassword();
            }
        });

        txtgotoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(patientLogin.this, pateintsregistration.class));
            }
        });
    }

    private void login() {
        String pateintphone = edittxtpateintPhone.getText().toString().trim();
        String pateintpassword = edittxtpateintPass.getText().toString().trim();

        if (pateintphone.isEmpty()) {
            edittxtpateintPhone.setError("Please enter phone number");
            edittxtpateintPhone.requestFocus();
        } else if (pateintpassword.isEmpty()) {
            edittxtpateintPass.setError("Please enter password");
            edittxtpateintPass.requestFocus();
        } else if (!pateintphone.isEmpty() && !pateintpassword.isEmpty()) {


            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot datapateint : snapshot.getChildren()) {

                        patientRegistrationmodel registrationmodel = datapateint.getValue(patientRegistrationmodel.class);

                        String phoneFromDB = "";
                        String passfromDB = "";
                        //match value Sharedprefrence or Database
                        if (currentuser.equals(registrationmodel.getPateintID())) {
                            phoneFromDB = registrationmodel.getPhoneNumber();
                            passfromDB = registrationmodel.getPassword();

                            if (phoneFromDB.equals(pateintphone) && passfromDB.equals(pateintpassword)) {
                                mysharedprefrence.setCheckStatusPregs("1");
                                startActivity(new Intent(patientLogin.this, dashboardPatient.class));
                                patientLogin.this.finish();
                                Toast.makeText(patientLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(patientLogin.this, "Login Failed!", Toast.LENGTH_SHORT).show();
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

    private void showpassword()
    {
        showpasscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    //show password
                    edittxtpateintPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    //hide password
                    edittxtpateintPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}