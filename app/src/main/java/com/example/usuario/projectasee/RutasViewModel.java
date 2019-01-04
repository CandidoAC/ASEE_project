package com.example.usuario.projectasee;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.usuario.projectasee.Modelo.Ruta;

import java.util.List;

public class RutasViewModel extends AndroidViewModel {

    private RutasRepository repository;
    private LiveData<List<Ruta>> allRoutes;

    public RutasViewModel(@NonNull Application application) {
        super (application);
        repository=new RutasRepository ( application );
        allRoutes =repository.getAllRoutes();
    }

    public void insertarRuta(Ruta r){
        repository.insertarRuta ( r );
    }

    public Ruta getRuta(int id){
        return repository.getRuta ( id );
    }

    public void modificarRuta(Ruta r){
        repository.editarRuta ( r );
    }

    public void borrarRuta(Ruta r){
        repository.borrarRuta ( r );
    }

    public void editarRuta(Ruta r) {
        repository.editarRuta(r);
    }

    public LiveData<List<Ruta>> getAllRoutes(){
        return allRoutes;
    }

}
