package com.example.moneymanager.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.util.TableInfo;

import java.io.Serializable;

@Entity(tableName = "userprofile_table")
public class userProfile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    @ColumnInfo(name = "profile_Username")
    String profile_Username = "";

    @ColumnInfo(name = "profile_bio")
    String profile_bio = "";

    @ColumnInfo(name = "user_birth_date")
    String user_birth_date = "";

    @ColumnInfo(name = "currentUser")
    String currentUser = "";

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] profilePic;

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_Username() {
        return profile_Username;
    }

    public void setProfile_Username(String profile_Username) {
        this.profile_Username = profile_Username;
    }

    public String getProfile_bio() {
        return profile_bio;
    }

    public void setProfile_bio(String profile_bio) {
        this.profile_bio = profile_bio;
    }

    public String getUser_birth_date() {
        return user_birth_date;
    }

    public void setUser_birth_date(String user_birth_date) {
        this.user_birth_date = user_birth_date;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


}
