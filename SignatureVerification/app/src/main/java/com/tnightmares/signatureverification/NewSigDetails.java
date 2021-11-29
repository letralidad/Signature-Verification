package com.tnightmares.signatureverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewSigDetails extends AppCompatActivity {

    EditText fName, lName, dept;
    CardView submit;
    ImageView imageView;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private Bitmap bitmap1, bitmap2, bitmap3;

//    bitmapGetSet bitmapGetSet = new bitmapGetSet();
//    String[] bitmaps = new String[3];

//    FirebaseAuth fAuth;
    FirebaseFirestore rootNode;

    private mySqlDbHandler sqlDbHandler;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sig_details);

        rootNode = FirebaseFirestore.getInstance();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        //intent
//        Intent intent = getIntent();
//        arrayList = intent.getExtras().getStringArrayList ("list");
//        Log.d("TAG", "onCreate: " + arrayList );
//        Log.d("TAG", "onCreate: " + arrayList.size());

        //Python
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        final Python py = Python.getInstance();

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        dept = findViewById(R.id.dept);
        imageView = findViewById(R.id.imageConverted);

        try{
            sqlDbHandler = new mySqlDbHandler(this, "SignatureDatabase", null, 1);
            sqLiteDatabase = sqlDbHandler.getWritableDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniqueID = UUID.randomUUID().toString();
                String FirstName = fName.getText().toString().trim();
                String LastName = lName.getText().toString().trim();
                String Department = dept.getText().toString().trim();

                if(TextUtils.isEmpty(FirstName)){
                    fName.setError("First Name is Required!");
                    return;
                }
                if(TextUtils.isEmpty(LastName)){
                    lName.setError("Last Name is Required!");
                    return;
                }
                if(TextUtils.isEmpty(Department)){
                    dept.setError("Department is Required!");
                    return;
                }

                PyObject pyo = py.getModule("Caller");
                for(int i=0; i < 3; i++){
                    String stringQuery =  "Select Image from ImageTable where Name=\"bitmap" + i + "\"";
                    Cursor cursor = sqLiteDatabase.rawQuery(stringQuery, null);

                    cursor.moveToFirst();
                    String image = cursor.getString(0);
                    PyObject obj = pyo.callAttr("new_signature", image, "bitmap"+i);
                    cursor.close();
                }
                sqLiteDatabase.execSQL("DELETE FROM ImageTable");
                sqLiteDatabase.close();

//                PyObject obj = pyo.callAttr("saving");
//                String dir = obj.toString();
//                Log.d("TAG", "onClick: " + dir);




//
//                //Calling the object inside the python file



//
//
//// to be deleted for show processed image testing only
                //get the returned value of PyObject obj
//                String str = obj.toString();
//
//                //convert to byte array
//                byte data[] =android.util.Base64.decode(str, Base64.DEFAULT);
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                imageView.setImageBitmap(bmp);
//
//// to be deleted for testing only

                //userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = rootNode.collection("Users").document(uniqueID);

                Map<String, Object> user = new HashMap<>();
                user.put("UID", uniqueID);
                user.put("fName", FirstName);
                user.put("lName", LastName);
                user.put("dept", Department);
                user.put("featureSignature", "/gs:/signature-verification-f787b.appspot.com/users_SVC/"+uniqueID+".mat");

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NewSigDetails.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewSigDetails.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}