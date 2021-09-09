package com.tnightmares.signatureverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    CardView verifySignature, addSignature;
    TextView needHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        verifySignature = (CardView) findViewById(R.id.verify_signature);
        addSignature = (CardView) findViewById(R.id.add_signature);
        needHelp = (TextView) findViewById(R.id.need_help);

        verifySignature.setOnClickListener(this);
        addSignature.setOnClickListener(this);
        needHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.add_signature:
                i = new Intent(this, EnterSignature.class);
                startActivity(i);
                break;
            case R.id.verify_signature:
                i = new Intent(this, SignatureVerification.class);
                startActivity(i);
                break;
            default:
                Log.d("TAG", "onClick: ");
        }

    }
}