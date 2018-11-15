package com.example.usuario.projectasee;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoEventos {

    @Insert
    public void anadirEvento(Event evento);

    @Delete
    public void borrarEvento(Event evento);

    @Update
    public void editarEvento(Event evento);
}
