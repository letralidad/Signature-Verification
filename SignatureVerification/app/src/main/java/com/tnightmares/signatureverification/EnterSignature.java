package com.tnightmares.signatureverification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EnterSignature extends AppCompatActivity {

    CardView next, intent2;
    private final int REQUEST_CODE = 1024;
    private final int REQUEST_CODE2 = 2048;
    private ArrayList<String> intentBitmap = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_signature);

        next = (CardView) findViewById(R.id.next);
        intent2 = (CardView) findViewById(R.id.intent2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EnterSignature.this, UserSignature.class);
                startActivity(intent);
//                startActivityForResult(intent, REQUEST_CODE);
//                finish();
            }
        });

//        intent2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EnterSignature.this, NewSigEnterDetails.class);
//                startActivity(intent);
//
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            intentBitmap = data.getExtras().getStringArrayList ("list");
        }
    }

}