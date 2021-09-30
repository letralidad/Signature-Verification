package com.tnightmares.signatureverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kyanogen.signatureview.SignatureView;

public class EnterSignature extends AppCompatActivity implements View.OnClickListener {

    CardView next, save, clear;
    LinearLayout title;
    ConstraintLayout forSignature;
    SignatureView signatureCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_signature);

        next = (CardView) findViewById(R.id.next);
        save = (CardView) findViewById(R.id.save);
        clear = (CardView) findViewById(R.id.clear);

        title = findViewById(R.id.title);

        forSignature = findViewById(R.id.forSignature);

        signatureCanvas = findViewById(R.id.signatureCanvas);


        next.setOnClickListener(this);
        save.setOnClickListener(this);
        clear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.next:
                title.setVisibility(View.GONE);
                forSignature.setVisibility(View.VISIBLE);
                break;
            case R.id.save:
                Log.d("TAG", "onClick: save");
                break;
            case R.id.clear:
                signatureCanvas.clearCanvas();
                break;
            default:
                Log.d("TAG", "onClick: this is default");
                break;
        }
    }
}