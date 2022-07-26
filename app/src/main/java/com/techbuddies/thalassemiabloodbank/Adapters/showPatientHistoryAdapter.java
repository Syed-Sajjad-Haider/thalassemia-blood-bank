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
import com.squareup.picasso.Picasso;
import com.techbuddies.thalassemiabloodbank.PatientActivities.historyofPatient;
import com.techbuddies.thalassemiabloodbank.PatientActivities.postBloodRequest;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;
import com.techbuddies.thalassemiabloodbank.models.patientHistory;

import java.util.ArrayList;

public class showPatientHistoryAdapter extends RecyclerView.Adapter<showPatientHistoryAdapter.Myviewholder>{

    Context context;
    ArrayList<patientHistory> patientHistoryArrayList;
    DatabaseReference databaseReference;


    public showPatientHistoryAdapter(Context context, ArrayList<patientHistory> patientHistories) {
        this.context = context;
        this.patientHistoryArrayList = patientHistories;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new showPatientHistoryAdapter.Myviewholder(LayoutInflater.from(context).inflate(R.layout.patienthistoryitem_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

        holder.txtDonorName.setText("Donor name: "+patientHistoryArrayList.get(position).getNameOfDonor());
        holder.txtDateofTrans.setText("Date of transfusion: "+patientHistoryArrayList.get(position).getDataoftransfusion());
        holder.txtPlaceOfTrans.setText("Place of transfusion: "+patientHistoryArrayList.get(position).getPlaceoftransfusion());

        //set image using picaso
        Picasso.with(context)
                .load(patientHistoryArrayList.get(position).getImgURl())
                .into(holder.imgPictureofHistory);
        
        holder.imgDeletePhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Patient_History");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                databaseReference.child(patientHistoryArrayList.get(position).getId()).removeValue();
                                Toast.makeText(v.getContext(),   patientHistoryArrayList.get(position).getNameOfDonor() + " History has been deleted!", Toast.LENGTH_LONG).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to delete this History ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        holder.imgeditPHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), historyofPatient.class);
                i.putExtra("id", patientHistoryArrayList.get(position).getId());
                i.putExtra("currentid", patientHistoryArrayList.get(position).getCurrentuID());
                i.putExtra("donorName" , patientHistoryArrayList.get(position).getNameOfDonor());
                i.putExtra("dateoftrans" , patientHistoryArrayList.get(position).getDataoftransfusion());
                i.putExtra("placeoftrans" , patientHistoryArrayList.get(position).getPlaceoftransfusion());
                i.putExtra("imgURL" , patientHistoryArrayList.get(position).getImgURl());
                historyofPatient.addHistoryStatus = "1";
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientHistoryArrayList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView txtDonorName, txtDateofTrans , txtPlaceOfTrans;
        ImageView imgeditPHistory , imgDeletePhistory , imgPictureofHistory;

        public Myviewholder(View itemview) {
            super(itemview);

            txtDonorName = (TextView) itemview.findViewById(R.id.txtDonorName);
            txtDateofTrans = (TextView) itemview.findViewById(R.id.txtDateofTrans);
            txtPlaceOfTrans = (TextView) itemview.findViewById(R.id.txtPlaceOfTrans);

            imgeditPHistory = (ImageView) itemview.findViewById(R.id.imgeditPHistory);
            imgDeletePhistory = (ImageView) itemview.findViewById(R.id.imgDeletePhistory);
            imgPictureofHistory = (ImageView) itemview.findViewById(R.id.imgPictureofHistory);

        }
    }

}
