package com.example.moneymanager.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Convertor {

    public static byte[] BitMapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap ByteToBitMap(byte[] encodedString) {
        return BitmapFactory.decodeByteArray(encodedString, 0, encodedString.length);
    }
}
