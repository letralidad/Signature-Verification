package com.tnightmares.signatureverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UserSignature extends AppCompatActivity implements View.OnClickListener {

    private CardView clear, save, next;
    private SignatureView signatureCanvas;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signature);

        clear = (CardView) findViewById(R.id.clear);
        save = (CardView) findViewById(R.id.save);
        next = (CardView) findViewById(R.id.next);
        signatureCanvas = findViewById(R.id.signatureCanvas);

        clear.setOnClickListener(this);
        save.setOnClickListener(this);
        next.setOnClickListener(this);
        counter = 0;

    }

    @Override
    public void onClick(View view) {

        Bitmap bitmap = signatureCanvas.getSignatureBitmap();
        Intent intent;
        switch (view.getId()){
            case R.id.clear:
                signatureCanvas.clearCanvas();
                break;
            case R.id.next:
                if(!signatureCanvas.isBitmapEmpty()){
                    getStringImage(bitmap);
                    signatureCanvas.clearCanvas();
                    if(counter == 1){
                        next.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);
                    } else {
                        counter++;
                    }
                    break;
                } else {
                    break;
                }
            case R.id.save:
                intent = new Intent(this, NewSigDetails.class);
                intent.putStringArrayListExtra("list", arrayList);
                startActivity(intent);
                if(!signatureCanvas.isBitmapEmpty()) {
                    getStringImage(bitmap);
//                    Intent intent = getIntent();
//
//                    setResult(RESULT_OK, intent);
//                    Log.d("TAG", "onClick: no error");
//                    finish();
                } else {
                    break;
                }

                break;
            default:
                break;
        }


    }

    public void getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String encodeImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        arrayList.add(encodeImage);

    }
}