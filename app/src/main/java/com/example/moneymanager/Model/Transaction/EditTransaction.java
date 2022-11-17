package com.example.moneymanager.Model.Transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.moneymanager.database.UserWithTransactions;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.user;
import com.example.moneymanager.database.userDb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditTransaction extends AppCompatActivity {

    ImageView backHomePage;
    EditText editedTransactionTitle, editedAmount;
    RadioGroup editedCategoryGroup;
    RadioButton categoryBtn;
    RadioButton editedEntertainment, editedSocial_lifestyle, editedBeauty_health, editedOther;
    String categoryTitle;
    RadioGroup editedCashflowType;
    RadioButton cashFlow;
    String type;
    Button updateTransactionBtn;
    CalendarView editedCalendar;
    String date = "";
    String curUser = "";
    String currentTransaction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        backHomePage = findViewById(R.id.backHomePage);
        editedTransactionTitle = findViewById(R.id.editedTransactionTitle);
        editedCategoryGroup = (RadioGroup) findViewById(R.id.editedCategoryGroup);
        editedEntertainment = (RadioButton) findViewById(R.id.editedEntertainment);
        editedSocial_lifestyle = (RadioButton)findViewById(R.id.editedSocial_lifestyle);
        editedBeauty_health = (RadioButton)findViewById(R.id.editedBeauty_health);
        editedOther = (RadioButton)findViewById(R.id.editedOther);
        editedAmount = findViewById(R.id.editedAmount);
        editedCashflowType = findViewById(R.id.editedCashflowType);
        updateTransactionBtn = findViewById(R.id.updateTransactionBtn);
        editedCalendar = findViewById(R.id.editedCalendar);

        backHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Get the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Store radiobutton in list
        List<RadioButton> radioBtnList = new ArrayList<>();
        radioBtnList.add(editedEntertainment);
        radioBtnList.add(editedSocial_lifestyle);
        radioBtnList.add(editedBeauty_health);
        radioBtnList.add(editedOther);

        editedCategoryGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = editedCategoryGroup.getCheckedRadioButtonId();
                categoryTitle = radioBtnList.get(id).getText().toString();
            }
        });

        //Get the date selected
        editedCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + " " + stringMonth((i1 + 1)) + " " + i;
            }
        });


        updateTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get category
                int id_category = editedCategoryGroup.getCheckedRadioButtonId();
                categoryBtn = findViewById(id_category);
                categoryTitle = categoryBtn.getText().toString();

                //Get type of cash flow
                int id = editedCashflowType.getCheckedRadioButtonId();
                cashFlow = findViewById(id);
                type = cashFlow.getText().toString();

                //Calculate the latest total amount of income and expenses repeatedly
                MainPage mainPage1 = new MainPage();
                mainPage1.calculateTotal();

                //Save the data into the database
                SharedPreferences sp = getApplicationContext().getSharedPreferences("cardTitle",Context.MODE_PRIVATE);
                currentTransaction = sp.getString("title","");
                updateTransaction(currentTransaction, editedTransactionTitle.getText().toString(), categoryTitle, type, editedAmount.getText().toString(), addZero(date));
                Toast.makeText(EditTransaction.this, "Edited succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkTypeEdited(View v) {
        int id = editedCashflowType.getCheckedRadioButtonId();
        cashFlow = (RadioButton) findViewById(id);
    }

    public void checkCategoryEdited(View v) {
        int id = editedCategoryGroup.getCheckedRadioButtonId();
        categoryBtn = (RadioButton) findViewById(id);
    }

    //save user details
    private void updateTransaction(String cur, String title, String category, String type, String amount, String date) {
        userDb db = userDb.getInstance(getApplicationContext());
        List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);

        int id = 0;
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getTitle().equals(cur)) {
                id = transactionList.get(i).getId();
                break;
            }
        }

        transaction transaction = new transaction();
        transaction.setId(id);
        transaction.setTitle(title);
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setRelatedUser(curUser);

        db.userDao().updateTransaction(transaction);
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