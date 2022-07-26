package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.PatientActivities.dashboardPatient;
import com.techbuddies.thalassemiabloodbank.PatientActivities.patientLogin;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class MainActivity extends AppCompatActivity {

    Button btngotoDonor , btngotopatients;
    mysharedprefrence mysharedprefrence;
    String otpStatus, checkStatus , checkStatusPateint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mysharedprefrence = new mysharedprefrence(this);

        btngotoDonor = findViewById(R.id.btngotoDonor);
        btngotopatients = findViewById(R.id.btngotopatients);

        otpStatus = mysharedprefrence.getOTPStatus();
        checkStatus = mysharedprefrence.getSigninStatus();
        checkStatusPateint = mysharedprefrence.getCheckStatusPregs();



        btngotoDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Create an Intent that will start the Menu-Activity. */
                if(checkStatus.equals("1") && otpStatus.equals("1"))
                {
                    Intent mainIntent = new Intent(MainActivity.this, DashboardDonor.class);
                    startActivity(mainIntent);
                }
           /*     else if(checkStatus.equals("0") && otpStatus.equals("0"))
                {
                    Intent mainIntent = new Intent(MainActivity.this, phoneLogin.class);
                    startActivity(mainIntent);
                    finish();
                }*/
                else if(checkStatus.equals("0") && otpStatus.equals("1"))
                {
                    Intent mainIntent = new Intent(MainActivity.this, donorLogin.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

        btngotopatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Create an Intent that will start the Menu-Activity. */
                if(checkStatusPateint.equals("1") && otpStatus.equals("1"))
                {
                    Intent mainIntent = new Intent(MainActivity.this, dashboardPatient.class);
                    startActivity(mainIntent);
                }
                else if(checkStatusPateint.equals("0") && otpStatus.equals("1"))
                {
                    Intent mainIntent = new Intent(MainActivity.this, patientLogin.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}