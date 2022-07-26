package com.techbuddies.thalassemiabloodbank.PatientActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.techbuddies.thalassemiabloodbank.R;
import com.techbuddies.thalassemiabloodbank.donorActivities.DashboardDonor;
import com.techbuddies.thalassemiabloodbank.donorActivities.showBloodRequest;
import com.techbuddies.thalassemiabloodbank.models.patientHistory;

import java.util.Calendar;

public class historyofPatient extends AppCompatActivity {

    EditText edittextDonorName , edittextDateoftransfusion , editPlaceofTransfusion;
    ImageView imgPictureofHistory;

    Button btnAddHistory , btnCheckHistory;

    private int mYear, mMonth, mDay;

    //firebase variables
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private Uri imageUri;

    String id , currentuID , nameOfDonor , dataoftransfusion , placeoftransfusion , imgURl;

    public static String addHistoryStatus = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyof_patient);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patient_History");
        //database refrence for firebase storage
        storageReference = FirebaseStorage.getInstance().getReference();

        edittextDonorName = findViewById(R.id.edittextDonorName);
        edittextDateoftransfusion = findViewById(R.id.edittextDateoftransfusion);
        editPlaceofTransfusion = findViewById(R.id.editPlaceofTransfusion);

        imgPictureofHistory = findViewById(R.id.imgPictureofHistory);

        btnAddHistory = findViewById(R.id.btnAddHistory);
        btnCheckHistory = findViewById(R.id.btnCheckHistory);

        btnAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddHistoryIntoDatabase(imageUri);
            }
        });

        btnCheckHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(historyofPatient.this , showPatientHistory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        imgPictureofHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImage();
            }
        });

        edittextDateoftransfusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        //check status
        if (addHistoryStatus.equals("0"))
        {
            Log.d("==", "onCreate: " + "status 0");
        }
        else if (addHistoryStatus.equals("1"))
        {
            btnCheckHistory.setVisibility(View.GONE);
            btnAddHistory.setText("Update History");
            getHistoryData();
        }
    }

    private void getHistoryData()
    {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        currentuID = bundle.getString("currentid");
        nameOfDonor = bundle.getString("donorName");
        dataoftransfusion = bundle.getString("dateoftrans");
        placeoftransfusion = bundle.getString("placeoftrans");
        imgURl = bundle.getString("imgURL");

        edittextDonorName.setText(nameOfDonor);
        edittextDateoftransfusion.setText(dataoftransfusion);
        editPlaceofTransfusion.setText(placeoftransfusion);

        //set image using picaso
        Picasso.with(this)
                .load(imgURl)
                .into(imgPictureofHistory);

     
    }

    private void AddHistoryIntoDatabase(Uri uri)
    {
        String nameofDonor = edittextDonorName.getText().toString().trim();
        String dateoftrans = edittextDateoftransfusion.getText().toString().trim();
        String placeoftrans = editPlaceofTransfusion.getText().toString().trim();

        if (nameofDonor.isEmpty())
        {
            edittextDonorName.setError("Please enter donor name");
            edittextDonorName.requestFocus();
        }
        else if (dateoftrans.isEmpty())
        {
            edittextDateoftransfusion.setError("Please enter date of transfusion");
            edittextDateoftransfusion.requestFocus();
        }
        else if (placeoftrans.isEmpty())
        {
            editPlaceofTransfusion.setError("Please enter place of transfusion");
            editPlaceofTransfusion.requestFocus();
        }
        else if (imageUri == null)
        {
            Toast.makeText(this, "Please select picture or history", Toast.LENGTH_SHORT).show();
        }
        else if (!nameofDonor.isEmpty() && !dateoftrans.isEmpty() && !placeoftrans.isEmpty() && imageUri !=null)
        {
            //get firebase otp unique id
            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //getuniq key
            String UniqID = databaseReference.push().getKey();

            StorageReference fileref = storageReference.child(System.currentTimeMillis()+"." + getFileExtenstion(uri));
            fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (addHistoryStatus.equals("0")) {
                                patientHistory patientHistory = new patientHistory(UniqID, currentuser, nameofDonor, dateoftrans, placeoftrans, uri.toString());
                                databaseReference.child(UniqID).setValue(patientHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(historyofPatient.this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                                        //if data insert goto next activity
                                    }
                                });

                            }
                            else if (addHistoryStatus.equals("1"))
                            {
                                patientHistory patientHistory = new patientHistory(id, currentuser, nameofDonor, dateoftrans, placeoftrans, uri.toString());
                                databaseReference.child(id).setValue(patientHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(historyofPatient.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        addHistoryStatus = "0";
                                    }
                                });
                            }

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    Toast.makeText(historyofPatient.this, "Data Uploading In Progress!", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(historyofPatient.this, "Data Uploading Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private String getFileExtenstion(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    //end


    //method for open calender dialog
    public  void chooseDate()
    {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edittextDateoftransfusion.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    //Method for pick image from gallery
    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            imgPictureofHistory.setImageURI(imageUri);
        }
    }
}