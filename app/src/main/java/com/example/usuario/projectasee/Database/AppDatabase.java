package com.example.usuario.projectasee.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.usuario.projectasee.Activity.MainActivity;
import com.example.usuario.projectasee.HttpHandler;
import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.Modelo.Ruta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;


@Database(entities = {Ruta.class, Event.class}, version = 1)
@TypeConverters({Converters.class, ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract DaoRutas daoRutas();
    public abstract DaoEventos daoEventos();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "rundb")
                            .addCallback ( roomCallback )
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback (){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate ( db );
            new GetContent ().execute (instance);
        }
    } ;
    private static class GetContent extends AsyncTask<AppDatabase,Void,Void> {

        @Override
        protected Void doInBackground(AppDatabase... appdatabase) {
            HttpHandler h = new HttpHandler ();
            String url = "https://dl.dropboxusercontent.com/s/k2aj8u5kwv4n8fa/fichero.json?dl=0";

            String jasonUrl = h.makeServiceCall ( url );

            if (jasonUrl != null) {
                Log.i ( "JSON" , "Cargando json bien" );
                try {
                    JSONObject JASON = new JSONObject ( jasonUrl );
                    JSONArray JArray = JASON.getJSONArray ( "rutas" );
                    for (int i = 0; i < JArray.length (); i++) {
                        JSONObject o = JArray.getJSONObject ( i );
                        JSONObject time=o.getJSONObject ( "time" );
                        Integer horas=time.getInt ( "Hours" );
                        Integer min=time.getInt ( "Minutes" );
                        Integer s=time.getInt ( "Seconds" );
                        appdatabase[0].daoRutas ().anadirRuta ( new Ruta ( 0, o.getString ( "nombre" ) , (float) o.getDouble ( "distancia" ) , (float) o.getDouble ( "calorias" ) , new Time ( horas,min,s)) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace ();
                }

            }
            return null;
        }
    }
    public static void destroyInstance() {
        instance = null;
    }


}