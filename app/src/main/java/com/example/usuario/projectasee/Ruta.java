package com.example.usuario.projectasee;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.net.rtp.RtpStream;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@Entity(tableName = "rutas")
public class Ruta {

    @PrimaryKey
    private int id;
    private String nombre;
    private float distancia;
    private double calorias;
    private Time tiempo;

    public final static String NOMBRE = "nombre";
    public final static String DISTANCIA = "distancia";
    public final static String CALORIAS = "calorias";
    public final static String TIEMPO = "tiempo";

    @Ignore
    public Ruta() {
    }

    public Ruta(String nombre , float distancia , double calorias , Time tiempo) {
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

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public Time getTiempo() {
        return tiempo;
    }

    public void setTiempo(Time tiempo) {
        this.tiempo = tiempo;
    }
    public static void packageIntent(Intent intent, String nombre,
                                     float distancia, float calorias, Time Tiempo) {

        intent.putExtra(Ruta.NOMBRE,nombre );
        intent.putExtra(Ruta.CALORIAS, String.valueOf ( calorias ));
        intent.putExtra(Ruta.DISTANCIA,  String.valueOf ( distancia ));
        intent.putExtra(Ruta.TIEMPO,Tiempo);

    }
}
