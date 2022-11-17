package com.example.moneymanager.LoginPage;

import static android.content.ContentValues.TAG;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.database.DAO_user;
import com.example.moneymanager.database.user;
import com.example.moneymanager.database.userDb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    ImageView backStartBtn;
    TextView alreadyhavAcc;
    Button createAccBtn;
    EditText password;
    EditText confirmPass;
    EditText emailAddress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backStartBtn = (ImageView) findViewById(R.id.backStartBtn);
        alreadyhavAcc = (TextView) findViewById(R.id.alreadyhavAcc);
        createAccBtn = (Button) findViewById(R.id.createAccBtn);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        emailAddress = (EditText) findViewById(R.id.emailAddress);

        //Click to previous activity
        backStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, getStarted.class));
                finish();
            }
        });

        //Click to switch to login activity
        alreadyhavAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, login.class));
                finish();
            }
        });

        //Declare the object of DAO_user
        DAO_user dao_user = new DAO_user();

        //Button for register and checking password using firebase authentication
        mAuth = FirebaseAuth.getInstance();
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_add = emailAddress.getText().toString();
                String pass = password.getText().toString();
                String confirmP = confirmPass.getText().toString();

                if(!TextUtils.isEmpty(email_add) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmP)) {
                    if(pass.equals(confirmP)) {
                        mAuth.createUserWithEmailAndPassword(email_add, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(Register.this, login.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Register.this, "Account successfully registered!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "The password is not correct!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Please fill in all the field!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}











