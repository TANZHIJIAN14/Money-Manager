package com.example.moneymanager.Model.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.LoginPage.getStarted;
import com.example.moneymanager.LoginPage.login;
import com.example.moneymanager.Model.Chart.chart;
import com.example.moneymanager.Model.History.history;
import com.example.moneymanager.Model.Home.MainPage;
import com.example.moneymanager.Model.Transaction.addTransaction;
import com.example.moneymanager.R;
import com.example.moneymanager.database.Convertor;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;
import com.example.moneymanager.database.userProfile;
import com.example.moneymanager.database.userProfileDb;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    BottomNavigationView bottomBarViewProfile;
    BottomAppBar bottomBarProfile;
    FloatingActionButton addBtnProfile;
    RecyclerView statusRecyclerView;
    List<StatusCard> statusCardList = new ArrayList<>();
    TextView switchAccount, logout, profileName, profileBio;
    ImageView editIcon, profilePic;
    String curUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomBarViewProfile = findViewById(R.id.bottomBarViewProfile);
        bottomBarProfile = findViewById(R.id.bottomBarProfile);
        addBtnProfile = findViewById(R.id.addBtnProfile);
        statusRecyclerView = findViewById(R.id.statusRecyclerView);
        switchAccount = findViewById(R.id.switchAccount);
        logout = findViewById(R.id.logout);
        editIcon = findViewById(R.id.editIcon);
        profileName = findViewById(R.id.profileName);
        profileBio = findViewById(R.id.profileBio);
        profilePic = findViewById(R.id.profilePic);

        //Get the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Set transparent background
        bottomBarProfile.setBackground(null);

        getStatusTitle();

        //Set the recycler view
        StatusAdaptor statusAdaptor = new StatusAdaptor(this, statusCardList);
        statusRecyclerView.setAdapter(statusAdaptor);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        bottomBarViewProfile.setSelectedItemId(R.id.profile);
        bottomBarViewProfile.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.chart:
                        startActivity(new Intent(getApplicationContext(), chart.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        addBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get current total amount
                Intent intent = new Intent(profile.this, addTransaction.class);
                intent.putExtra("curAmount", calculateTotal());
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(profile.this, EditProfile.class),14);
            }
        });

        switchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, login.class));
                overridePendingTransition(0,0);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, getStarted.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDb db = userDb.getInstance(getApplicationContext());
        List<userProfile> userProfileList = db.userDao().getUserWithProfile(curUser);
        if(userProfileList.size() > 0) {
            int latest = userProfileList.size() - 1;
            profileName.setText(userProfileList.get(latest).getProfile_Username());
            profileBio.setText(userProfileList.get(latest).getProfile_bio());
            profilePic.setImageBitmap(Convertor.ByteToBitMap(userProfileList.get(latest).getProfilePic()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14 && resultCode == RESULT_OK) {
            profileName.setText(data.getStringExtra("profileUsername"));
            profileBio.setText((data.getStringExtra("birthDate")));
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

    private void getStatusTitle() {
        String[] statusTitles = getResources().getStringArray(R.array.statusList);
        int[] statusIcon = {R.drawable.happy, R.drawable.workdesk, R.drawable.gaming, R.drawable.sleeping, R.drawable.sport};
        String[] statusColor = {"#FFE033", "#A3ADAE", "#25B44D", "#58BC7C", "#8E20E1"};
        for(int i = 0; i < statusTitles.length; i++) {
            statusCardList.add(new StatusCard(statusTitles[i], statusColor[i], statusIcon[i]));
        }
    }

}