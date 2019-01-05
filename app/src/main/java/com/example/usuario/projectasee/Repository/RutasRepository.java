package com.example.usuario.projectasee.Repository;

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
    private LiveData<List<Ruta>> allRoutes;

    public RutasRepository(Application app){
        AppDatabase db=AppDatabase.getAppDatabase( app );
        daoRutas=db.daoRutas();
        allRoutes = daoRutas.getRutas();
    }

    public void insertarRuta(Ruta r){
        new InsertRoute( this.daoRutas ).execute ( r );
    }

    public Ruta getRuta(int id){
        Ruta r=null;
        try {
            r=new GetRoute( this.daoRutas ).execute ( id ).get ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        return r;
    }

    public void borrarRuta(Ruta r){
        new DeleteRoute( this.daoRutas ).execute ( r );
    }

    public void editarRuta(Ruta r){
        new UpdateRoute(this.daoRutas).execute(r);
    }

    public LiveData<List<Ruta>> getAllRoutes(){
        return allRoutes;
    }

    private static class InsertRoute extends AsyncTask<Ruta, Void, Ruta>{
        private DaoRutas daoRutas;

        private InsertRoute(DaoRutas daoRutas){
            this.daoRutas=daoRutas;
        }

        @Override
        protected Ruta doInBackground(Ruta... rutas) {
            daoRutas.anadirRuta( rutas[0] );
            return rutas[0];
        }

        @Override
        protected void onPostExecute(Ruta ruta) {
            super.onPostExecute ( ruta );

        }
    }

    private static class DeleteRoute extends AsyncTask<Ruta, Void, Void> {
        private DaoRutas daoRutas;

        private DeleteRoute(DaoRutas daoRutas) {
            this.daoRutas = daoRutas;
        }

        @Override
        protected Void doInBackground(Ruta... rutas) {
            daoRutas.borrarRuta ( rutas[0] );
            return null;
        }
    }

    private static class UpdateRoute extends AsyncTask<Ruta, Void, Void> {
        private DaoRutas daoRutas;

        private UpdateRoute(DaoRutas daoRutas) {
            this.daoRutas = daoRutas;
        }

        @Override
        protected Void doInBackground(Ruta... rutas) {
            daoRutas.editarRuta( rutas[0] );
            return null;
        }
    }

    private static class GetRoute extends AsyncTask<Integer, Void, Ruta>{
        private DaoRutas daoRutas;

        private GetRoute(DaoRutas daoRutas){
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
