package com.example.moneymanager.LoginPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class emailLogin extends AppCompatActivity {
    private int code ;
    String inputCode;
    EditText passCode1,passCode2,passCode3,passCode4;
    Button checkCodeBtn;
    ImageView backLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        passCode1 = findViewById(R.id.digit1);
        passCode2 = findViewById(R.id.digit2);
        passCode3 = findViewById(R.id.digit3);
        passCode4 = findViewById(R.id.digit4);

        checkCodeBtn = findViewById(R.id.checkCodeBtn);

        backLoginBtn = findViewById(R.id.backLoginBtn);

        passCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    passCode2.requestFocus();
                }
            }
        });
        passCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    passCode3.requestFocus();
                }
            }
        });
        passCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    passCode4.requestFocus();
                }
            }
        });

        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
                overridePendingTransition(0,0);
                finish();
            }
        });

        checkCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCode = passCode1.getText().toString() + passCode2.getText().toString() + passCode3.getText().toString() + passCode4.getText().toString();

                if(inputCode.equals(String.valueOf(code))) {
                    startActivity(new Intent(getApplicationContext(), MainPage.class));
                    overridePendingTransition(0,0);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}