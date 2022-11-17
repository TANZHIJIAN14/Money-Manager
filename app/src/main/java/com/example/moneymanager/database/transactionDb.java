package com.example.moneymanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {transaction.class}, version = 4, exportSchema = false)
public abstract class transactionDb extends RoomDatabase {
    private static transactionDb database;
    private static String DATABASE_NAME = "transaction";

    public synchronized static transactionDb getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            transactionDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract transactionDao transactionDao();
}
