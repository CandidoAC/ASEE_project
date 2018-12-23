package com.example.usuario.projectasee.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.Modelo.Ruta;


@Database(entities = {Ruta.class, Event.class}, version = 1)
@TypeConverters({Converters.class, ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract DaoRutas daoRutas();
    public abstract DaoEventos daoEventos();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "rundb")
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }


}
