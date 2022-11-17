package com.example.moneymanager.LoginPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.R;
import com.example.moneymanager.database.user;
import com.example.moneymanager.database.userDb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class login extends AppCompatActivity {
    ImageView backStartBtn1;
    EditText loginEmailAddress;
    EditText LoginPassword;
    Button loginAccBtn, LoginEmailBtn;
    TextView forgotPass;
    SharedPreferences sp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backStartBtn1 = (ImageView) findViewById(R.id.backStartBtn1);
        loginEmailAddress = (EditText) findViewById(R.id.loginEmailAddress);
        LoginPassword = (EditText) findViewById(R.id.LoginPassword);
        loginAccBtn = (Button) findViewById(R.id.loginAccBtn);
        forgotPass = (TextView) findViewById(R.id.forgotPass);
        LoginEmailBtn = (Button) findViewById(R.id.LoginEmailBtn);

        backStartBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Login email button
        LoginEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), emailLogin.class));
                overridePendingTransition(0,0);
            }
        });

        //Check email and password
        userDb db = userDb.getInstance(this.getApplicationContext());
        List<user> userList = db.userDao().getAll();

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        loginAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmailAddress.getText().toString();
                String pass = LoginPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(login.this, MainPage.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(login.this, "Account is successfully registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(login.this, "Please fill all the field", Toast.LENGTH_SHORT).show();
                }
                //Store the username into share preference
                sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("curUser", getName(email));
                editor.commit();

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, forgotPass.class));
                finish();
            }
        });

     }

     private String getName(String actualUsername) {
        for(int i = 0; i < actualUsername.length(); i++) {
            if(actualUsername.charAt(i) == '@') {
                actualUsername = actualUsername.substring(0,i);
            }
        }
        return actualUsername;
     }
}