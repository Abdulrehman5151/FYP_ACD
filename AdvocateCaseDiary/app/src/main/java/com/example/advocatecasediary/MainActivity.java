package com.example.advocatecasediary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText Name, Cnic, emaIl, Password, Phone, Address;
    Button submittbtn;
    TextView Link;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore ;
    Boolean valid = true;
    String UserID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.txtname);
        Cnic = findViewById(R.id.txtcnic);
        emaIl = findViewById(R.id.txtemail);
        Password = findViewById(R.id.txtpass);
        Phone = findViewById(R.id.txtphone);
        Address = findViewById(R.id.txtaddress);
        submittbtn = findViewById(R.id.txtbutton);
        Link = findViewById(R.id.txtlink);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        submittbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String  userName = Name.getText().toString().trim();
                String  userCnic = Cnic.getText().toString().trim();
                String  userEmail = emaIl.getText().toString().trim();
                String  userPass = Password.getText().toString().trim();
                String  userPhone = Phone.getText().toString().trim();
                String  useraddress = Address.getText().toString().trim();



                if (TextUtils.isEmpty(userName)) {
                    Name.setError("Please Enter Username here");
                } else if (TextUtils.isEmpty(userCnic)) {
                    Cnic.setError("Please Enter CNIC here");
                } else if (TextUtils.isEmpty(userEmail)) {
                    emaIl.setError("Please Enter Email here");
                } else if (!(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())) {
                    emaIl.setError("Please Enter Valid Email");
                } else if (TextUtils.isEmpty(userPass)) {
                    Password.setError("Please Enter Password here");
                } else if (userPass.length() < 6) {
                    Password.setError("Please Enter Password more than 6 digits"); }
                else if (TextUtils.isEmpty(userPhone)) {
                    Phone.setError("Please Enter Phone No here");
                } else if (TextUtils.isEmpty(useraddress)) {
                    Address.setError("Please Enter Address here");
                }

                    fAuth.createUserWithEmailAndPassword(emaIl.getText().toString().trim(), Password.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "User Not Created", Toast.LENGTH_SHORT).show();
                        }
                    });



                }



        });

        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                return;
            }
        });


    }
}
