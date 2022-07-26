package com.techbuddies.thalassemiabloodbank.donorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.techbuddies.thalassemiabloodbank.R;

public class phoneLogin extends AppCompatActivity {

    Button btnGotoVerify;
    EditText editText_carrierNumber;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        //hooks
        mAuth = FirebaseAuth.getInstance();

        editText_carrierNumber = findViewById(R.id.editText_carrierNumber);
        btnGotoVerify =  findViewById(R.id.btnGotoVerify);
    }

    public void gotoVerify(View view) {


        String phoneNo = editText_carrierNumber.getEditableText().toString();

        if(TextUtils.isEmpty(editText_carrierNumber.getText().toString())){
            editText_carrierNumber.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            editText_carrierNumber.setError("Enter Phone Number");
        }
        else if(editText_carrierNumber.getText().toString().length()!=11){
            editText_carrierNumber.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            editText_carrierNumber.setError("Enter Correct Phone Number");
        }
        else {
            Intent intent = new Intent(phoneLogin.this, VerificationActivity.class);
            intent.putExtra("phoneNo", phoneNo);
            startActivity(intent);
            finish();
        }
    }
}