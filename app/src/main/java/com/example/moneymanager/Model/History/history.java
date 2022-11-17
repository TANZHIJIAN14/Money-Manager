package com.example.moneymanager.Model.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.Model.Transaction.addTransaction;
import com.example.moneymanager.Model.Chart.chart;
import com.example.moneymanager.Model.Profile.profile;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class history extends AppCompatActivity implements CalendarFragment.onInputListener{

    BottomAppBar bottomBarHistory;
    BottomNavigationView bottomBarViewHistory;
    FloatingActionButton addBtnHistory;
    RecyclerView recyclerView2;
    ImageView filter_icon, emptyImageTransaction;
    String date_filter;
    String category_filter, date;
    List<transaction> filterList;
    TextView filterTitle, date_category, emptyTransaction;
    Button apply_filter;
    List<transaction> defaultList;
    String curUser = "";

    @Override
    public void sendInput(String date, String category) {
        date_filter = date;
        category_filter = category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        bottomBarHistory = findViewById(R.id.bottomBarHistory);
        bottomBarViewHistory = findViewById(R.id.bottomBarViewHistory);
        addBtnHistory = findViewById(R.id.addBtnHistory);
        recyclerView2 = findViewById(R.id.recyclerView2);
        filter_icon = findViewById(R.id.filter_icon);
        filterTitle = findViewById(R.id.filterTitle);
        apply_filter = findViewById(R.id.apply_filter);
        date_category = findViewById(R.id.date_category);
        emptyImageTransaction = findViewById(R.id.emptyImageTransaction);
        emptyTransaction = findViewById(R.id.emptyTransaction);

        //Set the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Set transparent background
        bottomBarHistory.setBackground(null);

        //Make fluent transition on bottomAppBar
        bottomBarViewHistory.setSelectedItemId(R.id.history);
        bottomBarViewHistory.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainPage.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.history:
                        return true;
                    case R.id.chart:
                        startActivity(new Intent(getApplicationContext(), chart.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profile.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        //Set current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        date = df.format(c);

        //Display current date transaction as default
        userDb db = userDb.getInstance(getApplicationContext());
        List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);
        defaultList = new ArrayList<>();
        try{
            for(int i = transactionList.size() - 1; i >= 0; i--) {
                if(transactionList.get(i).getDate().equals(date)) {
                    defaultList.add(transactionList.get(i));
                }
            }
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }

        if(defaultList.size() > 0) {
            TC_RecycleViewAdapter tc_recycleViewAdapter1 = new TC_RecycleViewAdapter(this, defaultList);
            recyclerView2.setAdapter(tc_recycleViewAdapter1);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));

            //Set the empty background as invisible
            emptyImageTransaction.setVisibility(View.INVISIBLE);
        } else {
            //Set the empty background
            emptyImageTransaction.setImageResource(R.drawable.empty_box);
            emptyTransaction.setText("No Transaction Shown");
        }

        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //Access the data of transaction
                    userDb db = userDb.getInstance(getApplicationContext());
                    List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);

                    //Filter the database with date and category
                    filterList = new ArrayList<>();
                    if(!date_filter.equals("") || !category_filter.equals("") ) {
                        for(int i = 0; i < transactionList.size(); i++) {
                            if(addZero(transactionList.get(i).getDate()).equals(date_filter) || transactionList.get(i).getCategory().equals(category_filter)) {
                                filterList.add(transactionList.get(i));
                            }
                        }
                        if(filterList.size() > 0) {
                            TC_RecycleViewAdapter tc_recycleViewAdapter1 = new TC_RecycleViewAdapter(history.this, filterList);
                            recyclerView2.setAdapter(tc_recycleViewAdapter1);
                            recyclerView2.setLayoutManager(new LinearLayoutManager(history.this));
                            emptyImageTransaction.setVisibility(View.INVISIBLE);
                            emptyTransaction.setVisibility(View.INVISIBLE);
                        } else {
                            recyclerView2.setVisibility(View.INVISIBLE);
                            emptyImageTransaction.setImageResource(R.drawable.empty_box);
                            emptyTransaction.setText("No Transaction Shown");
                        }
                    }else {
                        TC_RecycleViewAdapter tc_recycleViewAdapter1 = new TC_RecycleViewAdapter(history.this, defaultList);
                        recyclerView2.setAdapter(tc_recycleViewAdapter1);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(history.this));
                    }
                    Toast.makeText(history.this, "Filtered", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        //Adding listener method on add button to transaction activity
        addBtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current total amount
                Intent intent = new Intent(history.this, addTransaction.class);
                intent.putExtra("curAmount",calculateTotal());
                intent.putExtra("curUserHistory", curUser);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        //Set listener on filter icon
        filter_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarFragment calendarFragment = new CalendarFragment();
                calendarFragment.show(getSupportFragmentManager(), "CalendarFragment");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            //Access the data of transaction
            userDb db = userDb.getInstance(getApplicationContext());
            List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);

            List<transaction> tempDefault = new ArrayList<>();
            for(int i = 0; i < transactionList.size(); i++) {
                if(addZero(transactionList.get(i).getDate()).equals(date)) {
                    tempDefault.add(transactionList.get(i));
                }
            }

            //Filter the database with date and category
            filterList = new ArrayList<>();
            for(int i = 0; i < transactionList.size(); i++) {
                if(addZero(transactionList.get(i).getDate()).equals(date_filter) || transactionList.get(i).getCategory().equals(category_filter)) {
                    filterList.add(transactionList.get(i));
                }
            }
            TC_RecycleViewAdapter tc_recycleViewAdapter1;
            if(filterList.size() > 0) {
                tc_recycleViewAdapter1 = new TC_RecycleViewAdapter(this, filterList);
                emptyImageTransaction.setVisibility(View.INVISIBLE);
                emptyTransaction.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "List Updated!", Toast.LENGTH_SHORT).show();
            } else {
                tc_recycleViewAdapter1 = new TC_RecycleViewAdapter(this, tempDefault);
            }
            recyclerView2.setAdapter(tc_recycleViewAdapter1);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
    }


    private String calculateTotal() {
        //Set the current amount
        double sum = 0;
        try {
            userDb db = userDb.getInstance(getApplicationContext());
            List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);
            for(int i = 0; i < transactionList.size(); i++) {
                String temp = transactionList.get(i).getAmount();
                if(!transactionList.get(i).getType().equals("")) {
                    if(transactionList.get(i).getType().equals("Income")) {
                        temp = String.valueOf(Double.parseDouble(temp));
                    } else {
                        temp = String.valueOf(Double.parseDouble(temp) * -1);
                    }
                }
                sum += Double.parseDouble(temp);
            }
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
        return String.valueOf(sum);
    }

    public String addZero(String date) {
        String result = "";
        if(date.length() < 11) {
            return result = "0" + date;
        }
        return date;
    }
}