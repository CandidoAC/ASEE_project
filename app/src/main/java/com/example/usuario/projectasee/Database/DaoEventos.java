package com.example.usuario.projectasee.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.usuario.projectasee.Modelo.Event;

import java.util.Date;
import java.util.List;

@Dao
public interface DaoEventos {

    @Insert
    public void anadirEvento(Event evento);

    @Query("select * from eventos")
    public LiveData<List<Event>> getEventos();

    @Query("select * from eventos where date=:date")
    public LiveData<List<Event>> getEventosPorFecha(Date date);

    @Query("select * from eventos where id=:id")
    public Event getEvent(int id);

    @Delete
    public void borrarEvento(Event evento);

    @Update
    public void editarEvento(Event evento);
}
