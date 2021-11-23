package com.tnightmares.signatureverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class NewSigEnterDetails extends AppCompatActivity {

    EditText fName, lName, dept;
    Button submit;
    private ArrayList<String> intentBitmap = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sig_enter_details);

//        Intent intentGetEnd = getIntent();
//        intentBitmap = intentGetEnd.getExtras().getStringArrayList ("list");
//        Log.d("TAG", "onCreate: " + intentBitmap);
//        Log.d("TAG", "onCreate: " + intentBitmap.size());

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        dept = findViewById(R.id.dept);
        submit = findViewById(R.id.submit);

    }
}