package com.example.myapplication.data.converter;

import androidx.annotation.NonNull;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

@ProvidedTypeConverter
public class ConverterGenreID {
    @TypeConverter
    public static String fromList(@NonNull List<Integer> list){
        String str = null;
        if(list!=null){
            for(int i = 0; i<list.size(); i++){
                str = list.get(i) + ",";
            }
        }
        return str;
    }

    @NonNull
    @TypeConverter
    public static List<Integer> fromString(@NonNull String str){

        List<Integer> listInt = new ArrayList<>();
        if(str!=null){
            String[] listString = (str.split(","));
            for(String item: listString){
                listInt.add(Integer.parseInt(item));
            }
        }
        return listInt;
    }
}
