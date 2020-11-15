package com.saat.contacttest.model.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saat.contacttest.dataModel.PhoneNumberModel;

import java.lang.reflect.Type;
import java.util.List;

public class ListPhoneNumberTypeConverter{

    @TypeConverter
    public static List<String> fromJsonString(String value){
        Gson gson = new Gson();
        Type type =  new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(value,type);
    }

    @TypeConverter
    public static String toJsonString(List<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
