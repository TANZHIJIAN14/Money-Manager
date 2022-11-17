package com.example.moneymanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {user.class, transaction.class, userProfile.class},
        version = 3,
        exportSchema = false)
public abstract class userDb extends RoomDatabase {
    private static userDb database;
    private static String DATABASE_NAME = "users";

    public synchronized static userDb getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    userDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract userDao userDao();

}
