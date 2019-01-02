package com.example.usuario.projectasee.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.usuario.projectasee.HttpHandler;
import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.Modelo.Ruta;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


@Database(entities = {Ruta.class, Event.class}, version = 1)
@TypeConverters({Converters.class, ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract DaoRutas daoRutas();
    public abstract DaoEventos daoEventos();
    public  static Context context;

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "rundb")
                            .addCallback ( roomCallback )
                            .build();
            AppDatabase.getAppDatabase ( context ).context=context.getApplicationContext();
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

            SharedPreferences prefs=context.getSharedPreferences ("User", Context.MODE_PRIVATE);
            double peso=Float.valueOf ( prefs.getString ( "Peso", null));
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
                        List<LatLng> LCoordenadas =new ArrayList<LatLng> ();
                        JSONArray JArrayCoord = o.getJSONArray ( "coordenadas" );
                        for (int j = 0; j < JArrayCoord.length (); j++) {
                            JSONObject coor = JArrayCoord.getJSONObject ( j );
                            LCoordenadas.add ( new LatLng ( coor.getDouble ( "Lat" ),coor.getDouble ( "Long" ) ) );
                        }
                        double timeMIN=horas*60+min+s/60.0;
                        double calorias = 0.092*(peso*2.2)*timeMIN;
                        appdatabase[0].daoRutas ().anadirRuta ( new Ruta ( 0, o.getString ( "nombre" )  , calorias , new Time ( horas,min,s),LCoordenadas) );
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