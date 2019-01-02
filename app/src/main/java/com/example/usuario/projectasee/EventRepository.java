package com.example.usuario.projectasee;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.usuario.projectasee.Database.AppDatabase;
import com.example.usuario.projectasee.Database.DaoEventos;
import com.example.usuario.projectasee.Modelo.Event;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventRepository {
    private DaoEventos daoEventos;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application app){
        AppDatabase db=AppDatabase.getAppDatabase ( app );
        daoEventos=db.daoEventos ();
        allEvents=daoEventos.getRutas();
    }

    public void insertarEvento(Event e){
        new InsertEvento ( this.daoEventos ).execute ( e );
    }

    public Event getEvento(int id){
        Event ev=null;
        try {
            ev=new GetEvento ( this.daoEventos ).execute ( id ).get ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return ev;
    }


    public void borrarEvent(Event e){
        new DeleteEvento ( this.daoEventos ).execute ( e );
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    private static class InsertEvento extends AsyncTask<Event, Void, Event>{
        private DaoEventos daoEventos;

        private InsertEvento(DaoEventos daoEventos){
            this.daoEventos=daoEventos;
        }

        @Override
        protected Event doInBackground(Event... eventos) {
            daoEventos.anadirEvento ( eventos[0] );
            return eventos[0];
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute ( event );

        }
    }

    private static class DeleteEvento extends AsyncTask<Event, Void, Void> {
        private DaoEventos daoEventos;

        private DeleteEvento(DaoEventos daoEventos){
            this.daoEventos=daoEventos;
        }

        @Override
        protected Void doInBackground(Event... events) {
            daoEventos.borrarEvento ( events[0] );
            return null;
        }
    }
    private static class GetEvento extends AsyncTask<Integer, Void, Event>{
        private DaoEventos daoEventos;

        private GetEvento(DaoEventos daoEventos){
            this.daoEventos=daoEventos;
        }

        @Override
        protected Event doInBackground(Integer... id) {
            return daoEventos.getEvent ( id[0] );
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute ( event );

        }
    }
}
