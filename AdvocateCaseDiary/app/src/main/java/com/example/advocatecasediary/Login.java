package com.example.advocatecasediary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText  emaIl , Password ;
    Button Loginbtn ;
    TextView Link ;
    FirebaseAuth fAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaIl = findViewById(R.id.txtemailone);
        Password = findViewById(R.id.txtpassone);
        Loginbtn = findViewById(R.id.loginbtn);
        Link = findViewById(R.id.txtlinkone);
        fAuth = FirebaseAuth.getInstance();
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String  usereMail = emaIl.getText().toString().trim();
                String  userPassword = Password.getText().toString().trim();

                if (TextUtils.isEmpty(usereMail))
                {
                    emaIl.setError("Please Enter Email here");
                    return;
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(usereMail).matches()))
                {
                    emaIl.setError("Please Enter Valid Email");
                    return;
                }
                if (TextUtils.isEmpty(userPassword))
                {
                    Password.setError("Please Enter Password here");
                    return;
                }
                if (userPassword.length() < 6)
                {
                    Password.setError("Please Enter Password more than 6 digits");
                }
                fAuth.signInWithEmailAndPassword(usereMail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Login Successfully :)", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                        }
                         else
                             {
                                 Toast.makeText(Login.this, "Login Failed :(", Toast.LENGTH_SHORT).show();
                             }
                    }
                });
            }
        });

        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}