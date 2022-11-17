package com.example.moneymanager.database;

import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAO_user {
    private DatabaseReference databaseReference;

    public DAO_user() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("user");
    }

    public Task<Void> add(user user) {
        return databaseReference.child(getName(user.getUsername())).setValue(user);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
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
