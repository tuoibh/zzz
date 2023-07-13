package com.example.myapplication.data.converter;

import android.net.Uri;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

@ProvidedTypeConverter
public class UriConverter {
    @TypeConverter
    public static String fromUri(Uri uri){
        return uri.toString();
    }

    @TypeConverter
    public static Uri fromString(String str){
        return Uri.parse(str);
    }
}
