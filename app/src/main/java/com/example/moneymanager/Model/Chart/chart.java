package com.example.moneymanager.Model.Chart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.moneymanager.Model.History.TC_RecycleViewAdapter;
import com.example.moneymanager.Model.History.history;
import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.Model.Transaction.addTransaction;
import com.example.moneymanager.Model.Profile.profile;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class chart extends AppCompatActivity {

    BottomNavigationView bottomBarViewChart;
    BottomAppBar bottomBarChart;
    FloatingActionButton addBtnChart;
    TabLayout tabLayout;
    ViewPager2 layout_chart;
    MyViewPageAdapter myViewPageAdapter;
    CardView monthly_menu, choosePeriod;
    TextView periodTitle, periodMenu;
    String date;
    double totalEntertainmentIncome = 0;
    double totalSocialIncome  = 0;
    double totalBeautyIncome  = 0;
    double totalOtherIncome  = 0;
    String curUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        bottomBarChart = findViewById(R.id.bottomBarChart);
        bottomBarViewChart = findViewById(R.id.bottomBarViewChart);
        addBtnChart = findViewById(R.id.addBtnChart);
        tabLayout = findViewById(R.id.tabLayout);
        layout_chart = findViewById(R.id.layout_chart);
        monthly_menu = findViewById(R.id.monthly_menu);
        choosePeriod = findViewById(R.id.choosePeriod);
        periodMenu = findViewById(R.id.periodMenu);
        periodTitle = findViewById(R.id.periodTitle);

        //Get the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Set the fragment adapter
        myViewPageAdapter = new MyViewPageAdapter(this);
        layout_chart.setAdapter(myViewPageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                layout_chart.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        layout_chart.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        //Set listener on choosePeriod to set the period
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        MaterialDatePicker materialDatePicker = builder.build();

        choosePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        //Set default date
        Calendar calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date = materialDatePicker.getHeaderText();
                //Set the date according the period type selected
                String[] displayDate = date.split(" "); //4 Oct 2022
                if(periodMenu.getText().toString().equals("Monthly")) {
                    periodTitle.setText(displayDate[1]);
                } else if (periodMenu.getText().toString().equals("Annually")) {
                    periodTitle.setText(displayDate[2]);
                }

                //Refresh fragment
                layout_chart.setAdapter(myViewPageAdapter);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        layout_chart.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                layout_chart.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        tabLayout.getTabAt(position).select();
                    }
                });
            }
        });


        //Set transparent background
        bottomBarChart.setBackground(null);

        bottomBarViewChart.setSelectedItemId(R.id.chart);
        bottomBarViewChart.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainPage.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), history.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.chart:
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

        addBtnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current total amount
                Intent intent = new Intent(chart.this, addTransaction.class);
                intent.putExtra("curAmount",calculateTotal());
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        monthly_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(chart.this, monthly_menu);
                popupMenu.inflate(R.menu.monthly_menu);
                //<--Set period on the statistic-->
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Calendar calendar = Calendar.getInstance();
                        switch (item.getItemId()) {
                            case R.id.week:
                                //Set the current week
                                periodMenu.setText("Daily");
                                periodTitle.setText("Choose date");
                                break;
                            case R.id.month:
                                //Set the current month
                                periodMenu.setText("Monthly");
                                periodTitle.setText("Choose month");
                                break;
                            case R.id.annuar:
                                //Set the current year
                                periodMenu.setText("Annually");
                                periodTitle.setText("Choose year");
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public String date() {
        String selectedPeriod = date + " " + periodMenu.getText().toString();
        return selectedPeriod;
    }

    public void onResume() {
        super.onResume();
        //Set the fragment adapter
        layout_chart.setAdapter(myViewPageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                layout_chart.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        layout_chart.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
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

    private void monthSummary(String month) {
        userDb db = userDb.getInstance(getApplicationContext());
        List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);
        for(int i = 0; i < transactionList.size(); i++) {
            String[] tempSeperatedDate = transactionList.get(i).getDate().split(" ");
            if(tempSeperatedDate[1].equals(month)) {
                if(transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Income")) {
                    totalEntertainmentIncome += Double.parseDouble(transactionList.get(i).getAmount());
                } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Income")) {
                    totalSocialIncome += Double.parseDouble(transactionList.get(i).getAmount());
                } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Income")) {
                    totalBeautyIncome += Double.parseDouble(transactionList.get(i).getAmount());
                } else if (transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Income")) {
                    totalOtherIncome += Double.parseDouble(transactionList.get(i).getAmount());
                }
            }
        }
    }
}