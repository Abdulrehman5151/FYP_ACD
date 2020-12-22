package com.example.advocatecasedairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _txtbutton;
    EditText _txtphone, _txtcnic, _txtname, _txtemail, _txtpass, _txtaddress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper =new Databasehelper(this);
        _txtbutton =(Button)findViewById(R.id.txtbutton);
        _txtcnic=(EditText)findViewById(R.id.txtcnic);
        _txtname=(EditText)findViewById(R.id.txtname);
        _txtemail=(EditText)findViewById(R.id.txtemail);
        _txtpass=(EditText)findViewById(R.id.txtpass);
        _txtphone=(EditText)findViewById(R.id.txtphone);
        _txtaddress=(EditText)findViewById(R.id.txtaddress);
        _txtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db=openHelper.getWritableDatabase();
                String cnic     =_txtcnic.getText().toString();
                String name     =_txtname.getText().toString();
                String email    =_txtemail.getText().toString();
                String password =_txtpass.getText().toString();
                String phone    =_txtphone.getText().toString();
                String address  =_txtaddress.getText().toString();
                insertdata(cnic,name,email,password,phone,address);
                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void  insertdata(String cnic, String name, String email, String password, String phone, String address ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Databasehelper.COL_2, cnic);
        contentValues.put(Databasehelper.COL_3, name);
        contentValues.put(Databasehelper.COL_4, email);
        contentValues.put(Databasehelper.COL_5, password);
        contentValues.put(Databasehelper.COL_6, phone);
        contentValues.put(Databasehelper.COL_7, address);
        long id = db.insert(Databasehelper.TABLE_NAME, null, contentValues);

    }
}