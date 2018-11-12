package com.example.usuario.projectasee;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "rutas")
public class Ruta {

    @PrimaryKey
    private int id;
    private String nombre;
    private float distancia;
    private int calorias;
    private Time tiempo;

    @Ignore
    public Ruta() {
    }

    public Ruta(String nombre , float distancia , int calorias , Time tiempo) {
        this.nombre = nombre;
        this.distancia = distancia;
        this.calorias = calorias;
        this.tiempo = tiempo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public Time getTiempo() {

        return tiempo;
    }

    public void setTiempo(Time tiempo) {

        this.tiempo = tiempo;
    }
}
