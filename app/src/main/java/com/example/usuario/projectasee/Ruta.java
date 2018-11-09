package com.example.usuario.projectasee;


import java.sql.Time;
import java.util.Date;

public class Ruta {
    private String nombre;
    private float distancia;
    int calorías;
    private Time tiempo;

    public Ruta() {
    }

    public Ruta(String nombre , float distancia , int calorías , Time tiempo) {
        this.nombre = nombre;
        this.distancia = distancia;
        this.calorías = calorías;
        this.tiempo = tiempo;
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

    public int getCalorías() {
        return calorías;
    }

    public void setCalorías(int calorías) {
        this.calorías = calorías;
    }

    public Time getTiempo() {
        return tiempo;
    }

    public void setTiempo(Time tiempo) {
        this.tiempo = tiempo;
    }
}
