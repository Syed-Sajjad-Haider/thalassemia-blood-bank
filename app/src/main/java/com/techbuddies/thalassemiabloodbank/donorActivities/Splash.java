package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessaging;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

public class Splash extends AppCompatActivity {


    mysharedprefrence mysharedprefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mysharedprefrence = new mysharedprefrence(this);

        String checkOTP = mysharedprefrence.getOTPStatus();

        //subscribed topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic("bloodRequests");
        FirebaseMessaging.getInstance().subscribeToTopic("notificationFromAdminSide");
        //end

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (checkOTP.equals("1"))
                {
                    startActivity(new Intent(Splash.this , MainActivity.class));
                    Splash.this.finish();
                }
                else if (checkOTP.equals("0"))
                {
                    startActivity(new Intent(Splash.this , phoneLogin.class));
                    Splash.this.finish();
                }

            }
        }, 1000);
    }
}