package com.example.usuario.projectasee;

import java.util.Date;

public class Event {
    Date date;

    String nombre;

    public Event() {
    }

    public Event(Date date , String nombre) {
        this.date = date;
        this.nombre = nombre;
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
