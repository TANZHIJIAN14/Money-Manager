package com.example.moneymanager.LoginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;

public class getStarted extends AppCompatActivity {

    Button getStartedbtn1;
    Button havAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        getStartedbtn1 = findViewById(R.id.getStartedBtn);
        havAcc = findViewById(R.id.getStartedBtn2);

        getStartedbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getStarted.this, Register.class));
                finish();
            }
        });

        havAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getStarted.this, login.class));
                finish();
            }
        });
    }
}