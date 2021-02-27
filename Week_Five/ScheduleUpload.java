package com.example.advocatecasediary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadSCH extends AppCompatActivity {
    EditText editText;
    Button Btn ;
    StorageReference storageReference;
    DatabaseReference databaseReferencec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_s_c_h);

        editText= findViewById(R.id.edittext);
        Btn =findViewById(R.id.btn);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReferencec = FirebaseDatabase.getInstance().getReference("Uploadpdf");
        Btn.setEnabled(false);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectPDF();

            }
        });





    }

    private void selectPDF()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "Select PDF File"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data !=null && data.getData() !=null)
        {
            Btn.setEnabled(true);
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                 UploadFirebase(data.getData());
                }
            });
        }
    }

    private void UploadFirebase(Uri data)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();
        StorageReference Reference = storageReference.child("schedules/"+System.currentTimeMillis()+".pdf");

        Reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url  = uri.getResult();

                putPDF pdf  = new putPDF(editText.getText().toString(),url.toString());
                databaseReferencec.child(databaseReferencec.push().getKey()).setValue(pdf);
                Toast.makeText(UploadSCH.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
            {

                double progess = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded:   "+(int)progess+"%");
            }
        });
    }
}