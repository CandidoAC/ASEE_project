package com.example.usuario.projectasee;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.usuario.projectasee.Modelo.Ruta;

import java.util.List;

public class RutesViewModel extends AndroidViewModel {

    private RutasRepository repository;
    private LiveData<List<Ruta>> allRutes;

    public RutesViewModel(@NonNull Application application) {
        super (application);
        repository=new RutasRepository ( application );
        allRutes=repository.getAllRutes ();
    }

    public void insertarRuta(Ruta r){
        repository.insertarRuta ( r );
    }

    public Ruta getRuta(int id){
        return repository.getRuta ( id );
    }

    public void modificarRuta(Ruta r){
        repository.ModificarrRuta ( r );
    }

    public void borrarRuta(Ruta r){
        repository.borrarRuta ( r );
    }

    public LiveData<List<Ruta>> getAllRutes(){
        return allRutes;
    }

}
