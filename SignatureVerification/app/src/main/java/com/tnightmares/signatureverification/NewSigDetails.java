package com.tnightmares.signatureverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<String> arrayList = new ArrayList<String>();

//    FirebaseAuth fAuth;
    FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sig_details);

        rootNode = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        arrayList = intent.getExtras().getStringArrayList ("list");
        Log.d("TAG", "onCreate: " + arrayList );


        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        dept = findViewById(R.id.dept);


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