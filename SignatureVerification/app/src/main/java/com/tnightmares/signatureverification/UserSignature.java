package com.tnightmares.signatureverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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

    private String bitmap1, bitmap2, bitmap3;

    private String[] bitmaps = new String[3];
    private mySqlDbHandler sqlDbHandler;
    private SQLiteDatabase sqLiteDatabase;


//    private bitmapGetSet bitmapGetSet = new bitmapGetSet();
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signature);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        clear = (CardView) findViewById(R.id.clear);
        save = (CardView) findViewById(R.id.save);
        next = (CardView) findViewById(R.id.next);
        signatureCanvas = findViewById(R.id.signatureCanvas);

        clear.setOnClickListener(this);
        save.setOnClickListener(this);
        next.setOnClickListener(this);
        counter = 0;

        try{
            sqlDbHandler = new mySqlDbHandler(this, "SignatureDatabase", null, 1);
            sqLiteDatabase = sqlDbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE ImageTable(Name TEXT, Image TEXT)");
        } catch (Exception e){
            e.printStackTrace();
        }
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
                    bitmaps[counter] = getStringImage(bitmap);
                    Log.d("TAG", "onClick: " + bitmaps[counter]);
                    if(counter == 0){
                        signatureCanvas.clearCanvas();
                        counter++;
                    } else if(counter == 1){
                        signatureCanvas.clearCanvas();
                        next.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);
                        counter++;
                    }
//                    bitmapGetSet.getStringImage(bitmap, counter);
////                    getStringImage(bitmap);
//                    signatureCanvas.clearCanvas();
//                    if(counter == 1){
//                        next.setVisibility(View.GONE);
//                        save.setVisibility(View.VISIBLE);
//                    } else {
//                        counter++;
//                    }
                    break;
                } else {
                    break;
                }
            case R.id.save:

                if(!signatureCanvas.isBitmapEmpty()) {
                    bitmaps[counter] = getStringImage(bitmap);
                    Log.d("TAG", "onClick: " + bitmaps[counter] + "\n\n");
                    signatureCanvas.clearCanvas();

                    for(int i = 0; i < 3; i++){
                        Log.d("TAG", "onClick: " + bitmaps[i] + "\n");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("Name","bitmap"+i);
                        contentValues.put("Image", bitmaps[i]);

                        sqLiteDatabase.insert("ImageTable", null, contentValues);
                    }


//                    bitmapGetSet.getStringImage(bitmap, counter);
//                    getStringImage(bitmap);
//                    Intent intent = getIntent();
//
//                    setResult(RESULT_OK, intent);
//                    Log.d("TAG", "onClick: no error");
//                    finish();
                } else {
                    break;
                }


                intent = new Intent(this, NewSigDetails.class);
                startActivity(intent);
                finish();
//                intent.putStringArrayListExtra("list", arrayList);
                break;
            default:
                break;
        }


    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodeImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;

    }
}