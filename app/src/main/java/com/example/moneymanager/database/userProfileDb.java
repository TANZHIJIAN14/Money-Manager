package com.example.moneymanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {userProfile.class}, version = 6, exportSchema = false)
public abstract class userProfileDb extends RoomDatabase {
    private static userProfileDb database;
    private static String DATABASE_NAME = "User_Profile";

    public synchronized static userProfileDb getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            userProfileDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract userProfileDao userProfileDao();
}
