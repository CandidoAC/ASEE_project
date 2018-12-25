package com.example.usuario.projectasee.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;

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

    @TypeConverter
    public static List<LatLng> fromStringcoord(String value) {
      Type listType = new TypeToken<List<LatLng>>() {}.getType();
      return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisrCoord(List<LatLng> list) {
      Gson gson = new Gson();
      String json = gson.toJson(list);
      return json;
    }

}
