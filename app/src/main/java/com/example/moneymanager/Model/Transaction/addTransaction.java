package com.example.moneymanager.Model.Transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class addTransaction extends AppCompatActivity {

    ImageView closeBtn;
    TextView amountTitle;
    Button saveBtn;
    EditText transactionTitle, amount;
    RadioGroup categoryGroup;
    RadioButton categoryBtn;
    RadioButton entertainment, social_lifestyle, beauty_health, other;
    String categoryTitle;
    RadioGroup cashflowType;
    RadioButton cashFlow;
    String type;
    CalendarView editCalendar;
    String date = "";
    String curUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        closeBtn = findViewById(R.id.closeBtn);
        amountTitle = findViewById(R.id.amountTitle);
        saveBtn = findViewById(R.id.saveBtn);
        transactionTitle = findViewById(R.id.transactionTitle);
        amount = findViewById(R.id.amount);
        categoryGroup = (RadioGroup) findViewById(R.id.categoryGroup);
        entertainment = (RadioButton) findViewById(R.id.entertainment);
        social_lifestyle = (RadioButton)findViewById(R.id.social_lifestyle);
        beauty_health = (RadioButton)findViewById(R.id.beauty_health);
        other = (RadioButton)findViewById(R.id.other);
        cashflowType = findViewById(R.id.cashflowType);
        editCalendar = findViewById(R.id.editCalendar);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        //Get the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Set amount title
        String currentBalance = getIntent().getStringExtra("curAmount");
        amountTitle.setText("Amount : (Your Balance: " + currentBalance + ")");

        //Store radiobutton in list
        List<RadioButton> radioBtnList = new ArrayList<>();
        radioBtnList.add(entertainment);
        radioBtnList.add(social_lifestyle);
        radioBtnList.add(beauty_health);
        radioBtnList.add(other);

        categoryGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = categoryGroup.getCheckedRadioButtonId();
                categoryTitle = radioBtnList.get(id).getText().toString();
                for(int i = 0; i < radioBtnList.size(); i++) {
                    if(i != id) {
                        radioBtnList.get(i).setChecked(false);
                    }
                }
            }
        });

        //Get the date selected
        editCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + " " + stringMonth((i1 + 1)) + " " + i;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get category
                int id_category = categoryGroup.getCheckedRadioButtonId();
                categoryBtn = findViewById(id_category);
                categoryTitle = categoryBtn.getText().toString();

                //Get type of cash flow
                int id = cashflowType.getCheckedRadioButtonId();
                cashFlow = findViewById(id);
                type = cashFlow.getText().toString();

                //Calculate the latest total amount of income and expenses repeatedly
                MainPage mainPage1 = new MainPage();
                mainPage1.calculateTotal();

                //Save the data into the database
                saveTransaction(transactionTitle.getText().toString(), categoryTitle, type, amount.getText().toString(), addZero(date), curUser);
                finish();
                Toast.makeText(addTransaction.this, "Saved succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Set amount title
        String currentBalance = getIntent().getStringExtra("curAmount");
        amountTitle.setText("Amount : (Your Balance: " + currentBalance + ")");

    }

    public void checkType(View v) {
        int id = cashflowType.getCheckedRadioButtonId();
        cashFlow = findViewById(id);
    }

    public void checkCategory(View v) {
        int id = categoryGroup.getCheckedRadioButtonId();
        categoryBtn = (RadioButton) findViewById(id);
    }

    //save user details
    private void saveTransaction(String title, String category, String type, String amount, String date, String curUser) {
        transaction transaction = new transaction();
        transaction.setTitle(title);
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDate(addZero(date));
        transaction.setRelatedUser(curUser);

        userDb db = userDb.getInstance(getApplicationContext());
        db.userDao().insertTransaction(transaction);
        finish();
    }

    private String stringMonth(int i) {
        HashMap<Integer, String> months = new HashMap<>();
        months.put(1, "Jan");
        months.put(2, "Feb");
        months.put(3, "Mar");
        months.put(4, "Apr");
        months.put(5, "May");
        months.put(6, "Jun");
        months.put(7, "Jul");
        months.put(8, "Aug");
        months.put(9, "Sep");
        months.put(10, "Oct");
        months.put(11, "Nov");
        months.put(12, "Dec");
        return months.get(i);
    }

    public String addZero(String date) {
        String result = "";
        if(date.length() < 11) {
            return result = "0" + date;
        }
        return date;
    }
}