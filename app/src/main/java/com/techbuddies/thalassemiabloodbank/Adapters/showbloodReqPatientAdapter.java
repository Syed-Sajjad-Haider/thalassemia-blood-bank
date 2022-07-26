package com.techbuddies.thalassemiabloodbank.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techbuddies.thalassemiabloodbank.PatientActivities.postBloodRequest;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import java.util.ArrayList;

public class showbloodReqPatientAdapter extends RecyclerView.Adapter<showbloodReqPatientAdapter.Myviewholder> {


        Context context;
        ArrayList<bloodRequest> bloodRequestArrayList;
        DatabaseReference databaseReference;


        public showbloodReqPatientAdapter(Context context, ArrayList<bloodRequest> bloodRequests) {
        this.context = context;
        this.bloodRequestArrayList = bloodRequests;
        }

        @NonNull
        @Override

        public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.showbloodreqitemfor_delete_update, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Myviewholder holder, int position)
        {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blood_Request");

        holder.txtPateintName.setText("Patient name: " + bloodRequestArrayList.get(position).getPateintName());
        holder.txtbloodgroup.setText("Blood group: " + bloodRequestArrayList.get(position).getBloodGrp());
        holder.txthospitalName.setText("Hospital name: " + bloodRequestArrayList.get(position).getHospitalName());
        holder.txtHbLevelP.setText("HB level: " + bloodRequestArrayList.get(position).getHbLevel());
        holder.txtPhoneNoP.setText("Phone no: " + bloodRequestArrayList.get(position).getPhoneNumP());

        holder.imgDeleteBloodreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                databaseReference.child(bloodRequestArrayList.get(position).getId()).removeValue();
                                Toast.makeText(v.getContext(),   bloodRequestArrayList.get(position).getPateintName() + " your Request has been deleted!", Toast.LENGTH_LONG).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to delete this request?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        holder.imgeditBloodReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), postBloodRequest.class);
                i.putExtra("id", bloodRequestArrayList.get(position).getId());
                i.putExtra("currentid", bloodRequestArrayList.get(position).getCurrentuserID());
                i.putExtra("pName" , bloodRequestArrayList.get(position).getPateintName());
                i.putExtra("pbloodgrp" , bloodRequestArrayList.get(position).getBloodGrp());
                i.putExtra("pHospitalname" , bloodRequestArrayList.get(position).getHospitalName());
                i.putExtra("pHBlevel" , bloodRequestArrayList.get(position).getHbLevel());
                i.putExtra("pPhoneNo" , bloodRequestArrayList.get(position).getPhoneNumP());
                postBloodRequest.addstatus = "1";
                v.getContext().startActivity(i);

            }
        });

 }

@Override
public int getItemCount() {
        return bloodRequestArrayList.size();
        }

public class Myviewholder extends RecyclerView.ViewHolder {

    TextView txtPateintName, txtbloodgroup , txthospitalName , txtHbLevelP , txtPhoneNoP;
    ImageView imgeditBloodReq , imgDeleteBloodreq;

    public Myviewholder(View itemview) {
        super(itemview);

        txtPateintName = (TextView) itemview.findViewById(R.id.txtPateintName);
        txtbloodgroup = (TextView) itemview.findViewById(R.id.txtbloodgroup);
        txthospitalName = (TextView) itemview.findViewById(R.id.txthospitalName);
        txtHbLevelP = (TextView) itemview.findViewById(R.id.txtHbLevelP);
        txtPhoneNoP = (TextView) itemview.findViewById(R.id.txtPhoneNoP);

        imgeditBloodReq = (ImageView) itemview.findViewById(R.id.imgeditBloodReq);
        imgDeleteBloodreq = (ImageView) itemview.findViewById(R.id.imgDeleteBloodreq);

    }
}
}
