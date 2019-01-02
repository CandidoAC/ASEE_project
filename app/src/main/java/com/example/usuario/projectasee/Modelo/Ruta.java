package com.example.usuario.projectasee.Modelo;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.util.List;

@Entity(tableName = "rutas")
public class Ruta {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private float distancia;
    private double calorias;
    private Time tiempo;

    private List<LatLng> lcoordenadas;

    public final static String NOMBRE = "nombre";
    public final static String DISTANCIA = "distancia";
    public final static String CALORIAS = "calorias";
    public final static String TIEMPO = "tiempo";

    @Ignore
    public Ruta() {
    }

    public Ruta(int id, String nombre  , double calorias , Time tiempo,List<LatLng> lcoordenadas) {
        this.id = id;
        this.nombre = nombre;
        this.calorias = calorias;
        this.tiempo = tiempo;
        this.lcoordenadas=lcoordenadas;
        for(int i=0;i<lcoordenadas.size ()-1;i++){
            Location a=new Location ( "" );
            a.setLatitude ( lcoordenadas.get ( i ).latitude );
            a.setLongitude ( lcoordenadas.get ( i ).longitude );
            Location b=new Location ( "" );
            b.setLatitude ( lcoordenadas.get ( i+1 ).latitude );
            b.setLongitude ( lcoordenadas.get ( i+1 ).longitude );
            distancia=distancia+a.distanceTo ( b )/1000;
        }
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

    public List <LatLng> getLcoordenadas() {
        return lcoordenadas;
    }

    public void setLcoordenadas(List <LatLng> lcoordenadas) {
        this.lcoordenadas = lcoordenadas;
    }

    public static void packageIntent(Intent intent, String nombre,
                                     float distancia, float calorias, Time Tiempo) {

        intent.putExtra(Ruta.NOMBRE,nombre );
        intent.putExtra(Ruta.CALORIAS, String.valueOf ( calorias ));
        intent.putExtra(Ruta.DISTANCIA,  String.valueOf ( distancia ));
        intent.putExtra(Ruta.TIEMPO,Tiempo);

    }
}
