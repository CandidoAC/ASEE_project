package com.example.usuario.projectasee.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;

public class Converters {

    @TypeConverter
    public static Time fromString(String value) {
        Type timeType = new TypeToken<Time>() {}.getType();
        return new Gson().fromJson(value, timeType);
    }

    @TypeConverter
    public static String fromTime(Time time) {
        Gson gson = new Gson();
        String json = gson.toJson(time);
        return json;
    }

}
