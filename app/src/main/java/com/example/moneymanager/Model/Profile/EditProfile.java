package com.example.moneymanager.Model.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanager.Model.History.CalendarFragment;
import com.example.moneymanager.R;
import com.example.moneymanager.database.Convertor;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.userDb;
import com.example.moneymanager.database.userProfile;
import com.example.moneymanager.database.userProfileDb;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    ImageView backProfile, saveProfile;
    TextView displayUsername;
    EditText editedUsername, editedBio;
    CalendarView editedBirthDate;
    CircleImageView profilePicker, profile_image;
    String birthDate;
    String curUser = "";
    Bitmap bitmap;
    InputStream is;
    Uri uri;

    public interface passUsername{
        void sendInput(String username);
    }

    public passUsername passUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backProfile = findViewById(R.id.backProfile);
        saveProfile = findViewById(R.id.saveProfile);
        displayUsername = findViewById(R.id.displayUsername);
        editedUsername = findViewById(R.id.editedUsername);
        editedBio = findViewById(R.id.editedBio);
        editedBirthDate = findViewById(R.id.editedBirthDate);
        profilePicker = findViewById(R.id.profilePicker);
        profile_image = findViewById(R.id.profile_image);

        //Get the current user
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Set listener back to profile activity
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, profile.class));
                finish();
            }
        });

        //Birth date calendar
        editedBirthDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                birthDate = i2 + " " + stringMonth((i1 + 1)) + " " + i;
            }
        });

        //Upload the profile picture
        profilePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(EditProfile.this)
                        .maxResultSize(1080,1080)
                        .start(20);
            }
        });

        //Set the username and profile pic
        userDb db = userDb.getInstance(getApplicationContext());
        List<userProfile> userProfileList = db.userDao().getUserWithProfile(curUser);
        if(userProfileList.size() > 0) {
            int latest = userProfileList.size() - 1;
            displayUsername.setText(userProfileList.get(latest).getProfile_Username());
            profile_image.setImageURI(getImageUri(getApplicationContext(), Convertor.ByteToBitMap(userProfileList.get(latest).getProfilePic())));
        }

        //Save the profile
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editedUsername.getText().toString();
                displayUsername.setText(username);
                String bio = editedBio.getText().toString();
                saveProfile(username, bio, birthDate);

                Intent intent = new Intent(EditProfile.this,profile.class);
                intent.putExtra("profileUsername", username);
                intent.putExtra("birthDate", birthDate);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 20 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            profile_image.setImageURI(uri);
            try {
                is = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    //Save the profile data into database
    private void saveProfile(String username, String bio, String birthDate) {
        try{
            userProfile userProfile = new userProfile();

            if(!username.equals("")) {
                userProfile.setProfile_Username(username);
            }
            if(!bio.equals("")) {
                userProfile.setProfile_bio(bio);
            }
            if(!birthDate.equals("")) {
                userProfile.setUser_birth_date(birthDate);
            }
            userProfile.setCurrentUser(curUser);
            if(!bitmap.equals("") || bitmap != null) {
                userProfile.setProfilePic(Convertor.BitMapToByte(bitmap));
            } else {
                userDb db = userDb.getInstance(getApplicationContext());
                List<userProfile> userProfileList = db.userDao().getUserWithProfile(curUser);
                if(userProfileList.size() > 0) {
                    int latest = userProfileList.size() - 1;
                    userProfile.setProfilePic(userProfileList.get(latest).getProfilePic());
                }
            }

            userDb db = userDb.getInstance(getApplicationContext());
            db.userDao().insertProfile(userProfile);
            finish();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Convert the month into string
    private String stringMonth(int i) {
        HashMap<Integer, String> months = new HashMap<>();
        months.put(1, "Jan");
        months.put(2, "Feb");
        months.put(3, "Mar");
        months.put(4, "Apr");
        months.put(5, "May");
        months.put(6, "Jun");
        months.put(7, "Jul");
        months.put(8, "Aug");
        months.put(9, "Sep");
        months.put(10, "Oct");
        months.put(11, "Nov");
        months.put(12, "Dec");
        return months.get(i);
    }
}






