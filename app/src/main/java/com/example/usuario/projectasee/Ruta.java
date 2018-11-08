package com.example.usuario.projectasee;


import java.util.Date;

public class Ruta {
    private String nombre;
    private int distancia,calorías;
    private Date tiempo;

    public Ruta() {
    }

    public Ruta(String nombre , int distancia , int calorías , Date tiempo) {
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

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getCalorías() {
        return calorías;
    }

    public void setCalorías(int calorías) {
        this.calorías = calorías;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }
}
