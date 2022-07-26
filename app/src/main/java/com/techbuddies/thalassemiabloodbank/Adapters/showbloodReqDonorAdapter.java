package com.techbuddies.thalassemiabloodbank.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import java.util.ArrayList;

public class showbloodReqDonorAdapter extends RecyclerView.Adapter<showbloodReqDonorAdapter.Myviewholder> {


    Context context;
    ArrayList<bloodRequest> bloodRequestArrayList;
    DatabaseReference databaseReference;


    public showbloodReqDonorAdapter(Context context, ArrayList<bloodRequest> bloodRequests) {
        this.context = context;
        this.bloodRequestArrayList = bloodRequests;
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.showbloodrequestdonoritem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {

        holder.txtPateintName.setText("Patient name: " + bloodRequestArrayList.get(position).getPateintName());
        holder.txtbloodgroup.setText("Blood group: " + bloodRequestArrayList.get(position).getBloodGrp());
        holder.txthospitalName.setText("Hospital name: " + bloodRequestArrayList.get(position).getHospitalName());
        holder.txtHbLevelP.setText("HB level: " + bloodRequestArrayList.get(position).getHbLevel());
        holder.txtPhoneNoP.setText("Phone no: " + bloodRequestArrayList.get(position).getPhoneNumP());
    }

    @Override
    public int getItemCount() {
        return bloodRequestArrayList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView txtPateintName, txtbloodgroup , txthospitalName , txtHbLevelP , txtPhoneNoP;

        public Myviewholder(View itemview) {
            super(itemview);

            txtPateintName = (TextView) itemview.findViewById(R.id.txtPateintName);
            txtbloodgroup = (TextView) itemview.findViewById(R.id.txtbloodgroup);
            txthospitalName = (TextView) itemview.findViewById(R.id.txthospitalName);
            txtHbLevelP = (TextView) itemview.findViewById(R.id.txtHbLevelP);
            txtPhoneNoP = (TextView) itemview.findViewById(R.id.txtPhoneNoP);

        }
    }
}
