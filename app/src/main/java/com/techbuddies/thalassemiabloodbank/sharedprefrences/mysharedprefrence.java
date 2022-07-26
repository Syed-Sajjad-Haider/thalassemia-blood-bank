package com.techbuddies.thalassemiabloodbank.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;

public class mysharedprefrence {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String SigninStatus;
    String OTPStatus;
    String CheckStatusPregs;


    public mysharedprefrence(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public String getSigninStatus() {

        SigninStatus = sharedPreferences.getString("signinstatus","0");
        return SigninStatus;
    }

    public void setSigninStatus(String signinStatus) {
        editor.putString("signinstatus", signinStatus);
        editor.commit();
    }

    public String getOTPStatus() {

        OTPStatus = sharedPreferences.getString("otpStatus","0");
        return OTPStatus;
    }

    public void setOTPStatus(String otpStatus) {
        editor.putString("otpStatus", otpStatus);
        editor.commit();
    }

    public String getCheckStatusPregs() {

        CheckStatusPregs = sharedPreferences.getString("CheckStatusPregs","0");
        return CheckStatusPregs;
    }

    public void setCheckStatusPregs(String CheckStatusPregs) {
        editor.putString("CheckStatusPregs", CheckStatusPregs);
        editor.commit();
    }

}

