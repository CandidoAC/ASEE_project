package com.example.usuario.projectasee.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Converters {

    //Date
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date==null) {
            return(null);
        }

        return(date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch==null) {
            return(null);
        }
        return(new Date(millisSinceEpoch));
    }

    //Time
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

    //Lista LatLng
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
