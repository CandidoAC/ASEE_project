package com.example.usuario.projectasee.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.usuario.projectasee.Modelo.Ruta;

import java.util.List;

@Dao
public interface DaoRutas {

    @Insert
    public void anadirRuta(Ruta ruta);

    @Query("select * from rutas")
    public android.arch.lifecycle.LiveData <List <Ruta>> getRutas();

    @Query("select * from rutas where id=:id")
    public Ruta getRuta(int id);

    @Query("select * from rutas where id=:id")
    public Ruta getRuta(int id);

    @Delete
    public void borrarRuta(Ruta ruta);

    @Update
    public void editarUsuario(Ruta ruta);

}
