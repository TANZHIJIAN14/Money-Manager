package com.example.moneymanager.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface transactionDao {
    @Insert(onConflict = REPLACE)
    void insert(transaction transaction);

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    List<transaction> getAllTransaction();

    @Delete
    void delete(transaction transaction);

    @Update
    void update(transaction transaction);

}
