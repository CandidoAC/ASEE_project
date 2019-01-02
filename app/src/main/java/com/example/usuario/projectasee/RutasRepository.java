package com.example.usuario.projectasee;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.usuario.projectasee.Database.AppDatabase;
import com.example.usuario.projectasee.Database.DaoRutas;
import com.example.usuario.projectasee.Modelo.Ruta;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RutasRepository {
    private DaoRutas daoRutas;
    private LiveData<List<Ruta>> allRutes;

    public RutasRepository(Application app){
        AppDatabase db=AppDatabase.getAppDatabase ( app );
        daoRutas=db.daoRutas ();
        allRutes=daoRutas.getRutas();
    }

    public void insertarRuta(Ruta r){
        new InsertRute ( this.daoRutas ).execute ( r );
    }

    public Ruta getRuta(int id){
        Ruta r=null;
        try {
            r=new GetRute ( this.daoRutas ).execute ( id ).get ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return r;
    }

    public void borrarRuta(Ruta r){
        new DeleteRute ( this.daoRutas ).execute ( r );
    }

    public LiveData<List<Ruta>> getAllRutes(){
        return allRutes;
    }

    private static class InsertRute extends AsyncTask<Ruta, Void, Ruta>{
        private DaoRutas daoRutas;

        private InsertRute(DaoRutas daoRutas){
            this.daoRutas=daoRutas;
        }

        @Override
        protected Ruta doInBackground(Ruta... rutas) {
            daoRutas.anadirRuta ( rutas[0] );
            return rutas[0];
        }

        @Override
        protected void onPostExecute(Ruta ruta) {
            super.onPostExecute ( ruta );

        }
    }

    private static class DeleteRute extends AsyncTask<Ruta, Void, Void> {
        private DaoRutas daoRutas;

        private DeleteRute(DaoRutas daoRutas) {
            this.daoRutas = daoRutas;
        }

        @Override
        protected Void doInBackground(Ruta... rutas) {
            daoRutas.borrarRuta ( rutas[0] );
            return null;
        }
    }
    private static class GetRute extends AsyncTask<Integer, Void, Ruta>{
        private DaoRutas daoRutas;

        private GetRute(DaoRutas daoRutas){
            this.daoRutas=daoRutas;
        }

        @Override
        protected Ruta doInBackground(Integer... id) {
            return daoRutas.getRuta ( id[0] );
        }

        @Override
        protected void onPostExecute(Ruta ruta) {
            super.onPostExecute ( ruta );

        }
    }
}
