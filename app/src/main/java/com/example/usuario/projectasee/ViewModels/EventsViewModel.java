package com.example.usuario.projectasee.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.Repository.EventRepository;

import java.util.Date;
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

    public void updateEvent(Event e){
        repository.updateEvento ( e );
    }

    public Event getEvent(int id){
        return repository.getEvento ( id );
    }

    public LiveData<List<Event>> getEventsByDate(Date date){
        return repository.getAllEventsDate( date );
    }

    public void borrarEvents(Event e){
        repository.borrarEvent ( e );
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    public LiveData<List<Event>> getAllEventsByDate(Date d){
        return repository.getAllEventsDate(d);
    }

}
