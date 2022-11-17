package com.example.moneymanager.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moneymanager.R;
import com.example.moneymanager.database.DAO_user;
import com.example.moneymanager.database.user;
import com.example.moneymanager.database.userDb;

import java.util.HashMap;
import java.util.List;

public class forgotPass extends AppCompatActivity {

    EditText updateLoginEmailAddress;
    Button updateAccBtn;
    ImageView backStartBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        updateLoginEmailAddress = findViewById(R.id.updateLoginEmailAddress);
        updateAccBtn = findViewById(R.id.updateAccBtn);
        backStartBtn1 = findViewById(R.id.backStartBtn1);

        backStartBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgotPass.this, login.class));
                finish();
            }
        });

//        updateAccBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //update password
//                userDb db = userDb.getInstance(getApplicationContext());
//                List<user> userList = db.userDao().getAll();
//
//                //check enter email existed
//                boolean exist = false;
//                int id = 0;
//                String email = updateLoginEmailAddress.getText().toString();
//                for(int i = 0; i < userList.size(); i++) {
//                    if(email.equals(userList.get(i).getUsername())) {
//                        exist = true;
//                        id = i + 1;
//                        break;
//                    }
//                }
//
//                if(exist) {
//                    //update details
//                    user users = new user();
//                    String pass = updatePassword.getText().toString();
//                    String confirmPass = updateConfirmPass.getText().toString();
//
//                    users.setId(id);
//                    users.setUsername(email);
//                    users.setPassword(pass);
//                    users.setConfirmPass(confirmPass);
//
//                    //check password
//                    if(!(pass.equals("")) && !(confirmPass.equals(""))) {
//                        if(pass.equals(confirmPass)) {
//                            //Update the data using room database
//                            db.userDao().update(users);
//
//                            //Update the data using firebase
//                            HashMap<String, Object> hashMap = new HashMap<>();
//                            hashMap.put("password", pass);
//                            hashMap.put("confirmPass", confirmPass);
//                            DAO_user dao_user = new DAO_user();
//                            dao_user.update(getName(users.getUsername()), hashMap).addOnSuccessListener(suc -> {
//                                Toast.makeText(forgotPass.this, "Record is update", Toast.LENGTH_SHORT).show();
//                            }).addOnFailureListener(er -> {
//                                Toast.makeText(forgotPass.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                            });
//
//                            Toast.makeText(forgotPass.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(forgotPass.this, login.class));
//                            finish();
//                        } else {
//                            Toast.makeText(forgotPass.this, "Password not correct!", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(forgotPass.this, "Please enter password!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(forgotPass.this, "Email not existed!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


    }
    private String getName(String username) {
        for(int i = 0; i < username.length(); i++) {
            if(username.charAt(i) == '@') {
                username = username.substring(0,i);
            }
        }
        return username;
    }
}