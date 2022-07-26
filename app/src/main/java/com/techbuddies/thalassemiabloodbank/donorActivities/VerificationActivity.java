package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.sharedprefrences.mysharedprefrence;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    Button verify_btn;
    ProgressBar progressBar;
    EditText phoneNoEnteredByTheUser;
    String verificationCodeBySystem;
    Button resend_verifycode_btn;
    mysharedprefrence sharedPreferences;

    private String phoneNo;
    private Handler handler;
    private TextView timer;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        RelativeLayout btnlay = findViewById(R.id.idlayoutk);

        Animation animation2 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.shake);
        btnlay.startAnimation(animation2);

        verify_btn = findViewById(R.id.verify_btn);
        resend_verifycode_btn = findViewById(R.id.resend_verifycode_btn);
        phoneNoEnteredByTheUser = findViewById(R.id.verification_code_entered_by_user);
        timer = (TextView) findViewById(R.id.timer);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


        phoneNo = getIntent().getStringExtra("phoneNo");
        // Toast.makeText(this, phoneNo, Toast.LENGTH_SHORT).show();

        sendVerificationCodeToUser(phoneNo);
        runTimer();

        verify_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){

                progressBar.setVisibility(View.VISIBLE);

                String code = phoneNoEnteredByTheUser.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    phoneNoEnteredByTheUser.setError("Wrong OTP...");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                verifycode(code);
            }
        });

        resend_verifycode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCodeToUser(phoneNo);
                resend_verifycode_btn.setVisibility(View.GONE);
                verify_btn.setVisibility(View.VISIBLE);
                runTimer();
            }
        });
    }


    public void runTimer() {
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Resend code in " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                //timer.setText("finished");
                verify_btn.setVisibility(View.GONE);
                resend_verifycode_btn.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    private void sendVerificationCodeToUser (String phoneNo){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92" + phoneNo, //phone number to be verified
                60, // validity of the OTP
                TimeUnit.SECONDS,
                this,
                mCallbacks // onVerificationStateChangedCallback
        );
/*
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);*/
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    verificationCodeBySystem = s;

                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        verifycode(code);
                    }
//                    else{
//
//                        verify_btn.setVisibility(View.GONE);
//                        resend_verifycode_btn.setVisibility(View.VISIBLE);
//
//
//                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(VerificationActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

    public void verifycode(String codebyuser)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,codebyuser );
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        mysharedprefrence mysharedprefrence = new mysharedprefrence(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final Task<AuthResult> authResultTask = firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerificationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(VerificationActivity.this , MainActivity.class));
                    mysharedprefrence.setOTPStatus("1");
                    Toast.makeText(VerificationActivity.this, "Your phone number verified successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(VerificationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}