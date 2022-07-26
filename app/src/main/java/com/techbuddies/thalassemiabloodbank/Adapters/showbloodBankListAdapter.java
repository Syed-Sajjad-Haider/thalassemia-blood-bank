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
import com.techbuddies.thalassemiabloodbank.models.BloodBanklistData;
import com.techbuddies.thalassemiabloodbank.models.bloodRequest;

import java.util.ArrayList;

public class showbloodBankListAdapter extends RecyclerView.Adapter<showbloodBankListAdapter.Myviewholder> {


    Context context;
    ArrayList<BloodBanklistData> bloodBanklistDataArrayList;


    public showbloodBankListAdapter(Context context, ArrayList<BloodBanklistData> bloodBanklistData) {
        this.context = context;
        this.bloodBanklistDataArrayList = bloodBanklistData;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new showbloodBankListAdapter.Myviewholder(LayoutInflater.from(context).inflate(R.layout.bloodbank_itemslist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        holder.txtBloodBankName.setText(bloodBanklistDataArrayList.get(position).getBankName());
        holder.txtBankAddress.setText("Address: "+bloodBanklistDataArrayList.get(position).getBankAdress());
        holder.txtContactNumber.setText("Contact: "+bloodBanklistDataArrayList.get(position).getBankContact());

    }

    @Override
    public int getItemCount() {
        return bloodBanklistDataArrayList.size();
    }



    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView txtBloodBankName, txtBankAddress , txtContactNumber;

        public Myviewholder(View itemview) {
            super(itemview);

            txtBloodBankName = (TextView) itemview.findViewById(R.id.txtBloodBankName);
            txtBankAddress = (TextView) itemview.findViewById(R.id.txtBankAddress);
            txtContactNumber = (TextView) itemview.findViewById(R.id.txtContactNumber);
        }
    }
}
