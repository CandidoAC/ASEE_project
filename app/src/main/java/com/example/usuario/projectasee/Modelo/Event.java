package com.example.usuario.projectasee.Modelo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "eventos")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String nombre;

    @Ignore
    public Event() {
    }

    public Event( Date date , String nombre) {
        this.date = date;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
