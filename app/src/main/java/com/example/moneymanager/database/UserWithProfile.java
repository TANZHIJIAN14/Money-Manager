package com.example.moneymanager.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithProfile {
    @Embedded public user users;
    @Relation(
            parentColumn = "username",
            entityColumn = "currentUser"
    )

    public List<userProfile> userProfileList;
}
