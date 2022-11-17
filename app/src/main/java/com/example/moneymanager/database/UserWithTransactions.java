package com.example.moneymanager.database;

import androidx.room.Embedded;
import androidx.room.Relation;
import androidx.room.Transaction;

import java.util.List;

public class UserWithTransactions {
    @Embedded public user user;
    @Relation(
            parentColumn = "username", // Refer to user table
            entityColumn = "RelatedUser" // Refer to transaction table
    )
    public List<transaction> transactionList;
}
