package com.tnightmares.signatureverification;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class bitmapGetSet {

    String[] signatureBitmap = new String[3];

    public String getSignatureBitmap(int i){
        return null;
    }

    public void getStringImage(Bitmap bitmap, int i){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String encodeImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

        signatureBitmap[i] = encodeImage;
        Log.d("TAG", "getStringImage: " + signatureBitmap[i]);
//        return encodeImage;
    }

}
