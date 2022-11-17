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
public interface userProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(userProfile userProfile);

    @Query("SELECT * FROM userprofile_table ORDER BY id ASC")
    List<userProfile> getAllUserProfile();

    @Delete
    void delete(userProfile userProfile);

    @Update
    void update(userProfile userProfile);
}
