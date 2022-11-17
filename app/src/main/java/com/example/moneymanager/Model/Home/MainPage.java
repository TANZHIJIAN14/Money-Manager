package com.example.moneymanager.Model.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Model.Chart.chart;
import com.example.moneymanager.Model.History.TC_RecycleViewAdapter;
import com.example.moneymanager.Model.History.history;
import com.example.moneymanager.Model.Profile.profile;
import com.example.moneymanager.Model.Transaction.EditTransaction;
import com.example.moneymanager.Model.Transaction.TransactionCard;
import com.example.moneymanager.Model.Transaction.addTransaction;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;
import com.example.moneymanager.database.userProfile;
import com.example.moneymanager.database.userProfileDb;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainPage extends AppCompatActivity {

    TextView usernameTitle;
    BottomAppBar bottomBar;
    FloatingActionButton addBtn;
    TextView balanceAmount;
    TextView totalIncomes;
    TextView totalExpenses;
    ImageView emptyTransactionHome;
    RecyclerView mainPageTransaction;
    BottomNavigationView bottomBarView;
    String name, date;
    List<transaction> recyclerViewList = new ArrayList<>();
    String currentUserName;
    SharedPreferences spCurUserName;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        usernameTitle = findViewById(R.id.usernameTitle);
        bottomBar = findViewById(R.id.bottomBar);
        addBtn = findViewById(R.id.addBtn);
        balanceAmount = findViewById(R.id.balanceAmount);
        totalIncomes = findViewById(R.id.totalIncomes);
        totalExpenses = findViewById(R.id.totalExpenses);
        bottomBarView = findViewById(R.id.bottomBarView);
        emptyTransactionHome = findViewById(R.id.emptyTransactionHome);
        mainPageTransaction = findViewById(R.id.mainPageTransaction);

        //Get the current user name from login activity and save using share preferences
        spCurUserName = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        currentUserName = spCurUserName.getString("curUser", "");

        //Set the username in home page
        name = "Welcome Back, " + currentUserName;
        usernameTitle.setText(name);


        //Set transparent background
        bottomBar.setBackground(null);

        bottomBarView.setSelectedItemId(R.id.home);
        bottomBarView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.history:
                        Intent intentHistory = new Intent(MainPage.this, history.class);
                        intentHistory.putExtra("curUserHistory", currentUserName);
                        startActivity(intentHistory);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.chart:
                        Intent intentChart = new Intent(MainPage.this, chart.class);
                        intentChart.putExtra("curUserChart", currentUserName);
                        startActivity(intentChart);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent intentProfile = new Intent(MainPage.this, profile.class);
                        intentProfile.putExtra("curUserProfile", currentUserName);
                        startActivity(intentProfile);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current total amount
                Intent intent = new Intent(MainPage.this, addTransaction.class);
                intent.putExtra("curAmount",balanceAmount.getText().toString());
                intent.putExtra("curUserNameFromHomeToAdd", currentUserName);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the current amount
        calculateTotal();

        //Update the data in the card 1,2,3,4
        userDb db = userDb.getInstance(getApplicationContext());
        List<transaction> transactionList = db.userDao().getUserWithTransaction(currentUserName);

        int count = 4;
        for(int i = transactionList.size() - 1; i >= 0 ; i--) {
            if(count > 0) {
                if(addZero(transactionList.get(i).getDate()).equals(date)) {
                    recyclerViewList.add(transactionList.get(i));
                    count--;
                }
            } else {
                break;
            }
        }
        MainTransactionCardAdapter mainTransactionCardAdapter = new MainTransactionCardAdapter(this, recyclerViewList);
        mainPageTransaction.setAdapter(mainTransactionCardAdapter);
        mainPageTransaction.setLayoutManager(new LinearLayoutManager(this));

        //Set empty background
        if(recyclerViewList.size() != 0) {
            emptyTransactionHome.setVisibility(View.INVISIBLE);
        }
    }

    public void calculateTotal() {
        //Set the current amount
        try {
            //Set current date
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            date = df.format(c);

            userDb db = userDb.getInstance(getApplicationContext());
            List<transaction> transactionList = db.userDao().getUserWithTransaction(currentUserName);
            double sum = 0;
            double totalIncome = 0;
            double totalExpense = 0;
            for(int i = 0; i < transactionList.size(); i++) {
                if(addZero(transactionList.get(i).getDate()).equals(date)) {
                    String temp = transactionList.get(i).getAmount();
                    if(!transactionList.get(i).getType().equals("")) {
                        if(transactionList.get(i).getType().equals("Income")) {
                            temp = String.valueOf(Double.parseDouble(temp));
                            totalIncome += Double.parseDouble(temp);
                        } else {
                            temp = String.valueOf(Double.parseDouble(temp) * -1);
                            totalExpense += Double.parseDouble(temp) * -1;
                        }
                    }
                    sum += Double.parseDouble(temp);
                }
            }
            balanceAmount.setText("$ " + String.valueOf(sum));
            totalIncomes.setText("$ " + String.valueOf(totalIncome));
            totalExpenses.setText("-$ " + String.valueOf(totalExpense));
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    public String addZero(String date) {
        String result = "";
        if(date.length() < 11) {
            return result = "0" + date;
        }
        return date;
    }


}