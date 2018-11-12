package com.example.usuario.projectasee;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import java.sql.Time;

@Database(entities = {Ruta.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoRutas daoRutas();
}
