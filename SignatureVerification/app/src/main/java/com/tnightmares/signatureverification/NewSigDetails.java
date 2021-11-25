package com.tnightmares.signatureverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewSigDetails extends AppCompatActivity {

    EditText fName, lName, dept;
    CardView submit;
    ImageView imageView;
    private ArrayList<String> arrayList = new ArrayList<String>();

//    FirebaseAuth fAuth;
    FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sig_details);

        rootNode = FirebaseFirestore.getInstance();

        //intent
        Intent intent = getIntent();
        arrayList = intent.getExtras().getStringArrayList ("list");
        Log.d("TAG", "onCreate: " + arrayList );
        Log.d("TAG", "onCreate: " + arrayList.size());

        //Python
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        final Python py = Python.getInstance();

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        dept = findViewById(R.id.dept);
        imageView = findViewById(R.id.imageConverted);


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

                //Calling the object inside the python file
                PyObject pyo = py.getModule("Caller");
                PyObject obj = pyo.callAttr("new_signature", arrayList.get(0), arrayList.get(1), arrayList.get(2), uniqueID+".mat");

// to be deleted for testing only
                //get the returned value of PyObject obj
                String str = obj.toString();

                //convert to byte array
                byte data[] =android.util.Base64.decode(str, Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bmp);




// to be deleted for testing only

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