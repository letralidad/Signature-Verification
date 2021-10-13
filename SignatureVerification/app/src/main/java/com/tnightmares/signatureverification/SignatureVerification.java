package com.tnightmares.signatureverification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SignatureVerification extends AppCompatActivity implements View.OnClickListener {

    ImageView imageCaptured;
    CardView capture;
    CardView verify;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_verification);

        capture = findViewById(R.id.capture);
        verify = findViewById(R.id.verify);
        imageCaptured = findViewById(R.id.imageCaptured);

        capture.setOnClickListener(this);
        verify.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageCaptured.setImageBitmap(imageBitmap);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.capture:


                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePictureIntent.resolveActivity(this.getPackageManager()) != null) {

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();

                    } catch (IOException ex) {

                    }

                    if (photoFile != null){
                        Uri photoURI = FileProvider.getUriForFile(this, "com.tnightmares.signatureverification.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                } else {
                    Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.verify:
                Log.d("TAG", "onClick: verify");
                break;
            default:
                break;
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "photo";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}