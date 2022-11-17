package com.example.moneymanager.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface userDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(user user);

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    List<user> getAll();

    @Delete
    void delete(user user);

    @Update
    void update(user user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTransaction(transaction transactions);

    @Update
    void updateTransaction(transaction transaction);

    @Delete
    void deleteTransaction(transaction transaction);

    //This method functioned as when we pass the current user it will return all the transaction refer to this user
    @Transaction
    @Query("SELECT * FROM transaction_table WHERE RelatedUser = :currentUser")
    List<transaction> getUserWithTransaction(String currentUser);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProfile(userProfile userProfiles);

    @Transaction
    @Query("SELECT * FROM userprofile_table WHERE currentUser = :currentUser")
    List<userProfile> getUserWithProfile(String currentUser);

    @Update
    void updateProfile(userProfile userProfile);
}

















