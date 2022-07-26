package com.techbuddies.thalassemiabloodbank.PatientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.donorActivities.DashboardDonor;
import com.techbuddies.thalassemiabloodbank.donorActivities.showBloodRequest;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class postBloodRequest extends AppCompatActivity {

    Spinner bloodgroupDropdown;
    Button btnpostBloodRequest , btnshowPostedRequest;
    EditText edittextPateintName , edittextHospital , edittextHBLevel , edittextContactNumber;

    DatabaseReference databaseReference;

    //Volley request queue for notifications
    RequestQueue requestQueue;
    //url for fcm notification
    String URL = "https://fcm.googleapis.com/fcm/send";

    public static String addstatus="0";

    String id , currentuserID , pateintName , bloodGrp , hospitalName , hbLevel , phoneNumP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blood_request);

        //vollery request queue
        requestQueue = Volley.newRequestQueue(this);

        //database Refrence for realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Blood_Request");

        //edittext
        edittextPateintName = findViewById(R.id.edittextPateintName);
        edittextHospital = findViewById(R.id.edittextHospital);
        edittextHBLevel = findViewById(R.id.edittextHBLevel);
        edittextContactNumber = findViewById(R.id.edittextContactNumber);
        //Spinner
        bloodgroupDropdown = findViewById(R.id.bloodgroupDropdown);
        //button
        btnpostBloodRequest = findViewById(R.id.btnpostBloodRequest);
        btnshowPostedRequest = findViewById(R.id.btnshowPostedRequest);

        //blood group drowpdown
        String[] bloodGroupDropdwon = getResources().getStringArray(R.array.Blood_Group);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spineritems, bloodGroupDropdwon);
        arrayAdapter.setDropDownViewResource(R.layout.spineritems);
        bloodgroupDropdown.setAdapter(arrayAdapter);
        //end

        btnpostBloodRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bloodRequestPost();
            }
        });

        btnshowPostedRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(postBloodRequest.this , showbloodrequestpatients.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (addstatus.equals("1"))
        {
            btnpostBloodRequest.setText("Update Request");
            btnshowPostedRequest.setVisibility(View.GONE);
            getDataForUpdate();
        }
        else  if (addstatus.equals("0"))
        {
            Log.d("--", "onCreate: " + "status 0");
        }
    }

    private void bloodRequestPost()
    {
        String pName = edittextPateintName.getText().toString().trim();
        String pbloodGrp = bloodgroupDropdown.getSelectedItem().toString().trim();
        String hospitalP = edittextHospital.getText().toString().trim();
        String hbLevel = edittextHBLevel.getText().toString().trim();
        String phoneNop = edittextContactNumber.getText().toString().trim();

        if (pName.isEmpty())
        {
            edittextPateintName.setError("Please enter pateint name");
            edittextPateintName.requestFocus();
        }
        else if (hospitalP.isEmpty())
        {
            edittextHospital.setError("Please enter hospital name");
            edittextHospital.requestFocus();
        }
        else if (hbLevel.isEmpty())
        {
            edittextHospital.setError("Please enter HB level");
            edittextHospital.requestFocus();
        }
        else if (phoneNop.isEmpty())
        {
            edittextContactNumber.setError("Please enter phone number");
            edittextContactNumber.requestFocus();
        }
        else if (!pName.isEmpty() && !hospitalP.isEmpty() && !hbLevel.isEmpty())
        {
            //get firebase otp unique id
            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //getuniq key
            String UniqID = databaseReference.push().getKey();
            if (addstatus.equals("0")) {
                bloodRequest bloodRequest = new bloodRequest(UniqID, currentuser, pName, pbloodGrp, hospitalP, hbLevel, phoneNop);
                databaseReference.child(UniqID).setValue(bloodRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            sendFCMNotification();
                            onBackPressed();
                            Toast.makeText(postBloodRequest.this, "Blood Request Posted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(postBloodRequest.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else  if (addstatus.equals("1"))
            {
                bloodRequest bloodRequest = new bloodRequest(id, currentuser, pName, pbloodGrp, hospitalP, hbLevel, phoneNop);
                databaseReference.child(id).setValue(bloodRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            //sendFCMNotification();
                            onBackPressed();
                            addstatus = "0";
                            Toast.makeText(postBloodRequest.this, "Blood Request Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(postBloodRequest.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    //send fcm notification through this method
    private void sendFCMNotification()
    {
        JSONObject mainObj  = new JSONObject();
        try {

            mainObj.put("to" , "/topics/bloodRequests");

            JSONObject notificationJsonObject = new JSONObject();
            notificationJsonObject.put("title" , "Blood Request Notification");
            notificationJsonObject.put("body" , "New Blood Request Posted!");
            mainObj.put("notification" , notificationJsonObject);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //code here will runn if success
                    // Toast.makeText(Apply_leave.this, response.toString(), Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //code here will runn if error
                    Toast.makeText(postBloodRequest.this, " Notification Error: "+"Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String , String> header = new HashMap<String, String>();
                    header.put("content-type","application/json");
                    header.put("authorization", "key=AAAAnyg4V-s:APA91bGLTkGWURLfMhZJSvsIOhR3MEfEu_VQAxy0TL9UBMMcSxLqi-_H5qMQUmAPIcr4-tB2EEJKowH1v5qk1UK7Mm7KgSCLxyxFcGD8IJYFeF5OsgHsOfJI-fuqE0wmub0L67bPdmOw");

                    return header;
                }
            };
            requestQueue.add(request);

            request.setRetryPolicy(
                    new DefaultRetryPolicy(
                            500000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
            );
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //getData for update
    private void getDataForUpdate() {

            Bundle bundle = getIntent().getExtras();
            id = bundle.getString("id");
            currentuserID = bundle.getString("currentid");
            pateintName = bundle.getString("pName");
            bloodGrp = bundle.getString("pbloodgrp");
            hospitalName = bundle.getString("pHospitalname");
            hbLevel = bundle.getString("pHBlevel");
            phoneNumP = bundle.getString("pPhoneNo");

            //set text into edittext
        edittextPateintName.setText(pateintName);
        edittextHospital.setText(hospitalName);
        edittextHBLevel.setText(hbLevel);
        edittextContactNumber.setText(phoneNumP);
    }

}