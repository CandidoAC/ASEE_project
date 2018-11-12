package com.example.usuario.projectasee;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;

@Dao
public interface DaoRutas {

    @Insert
    public void anadirRuta(Ruta ruta);

    @Query("select * from rutas")
    public List<Ruta> getRutas();

    @Delete
    public void borrarRuta(Ruta ruta);

    @Update
    public void editarUsuario(Ruta ruta);

}
