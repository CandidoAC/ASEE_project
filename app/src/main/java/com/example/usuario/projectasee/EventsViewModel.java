package com.example.usuario.projectasee;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.usuario.projectasee.Modelo.Event;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    private EventRepository repository;
    private LiveData<List<Event>> allEvents;

    public EventsViewModel(@NonNull Application application) {
        super (application);
        repository=new EventRepository ( application );
        allEvents=repository.getAllEvents ();
    }

    public void insertarEvent(Event e){
        repository.insertarEvento ( e );
    }

    public Event getEvent(int id){
        return repository.getEvento ( id );
    }

    public void borrarEvents(Event e){
        repository.borrarEvent ( e );
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

}
