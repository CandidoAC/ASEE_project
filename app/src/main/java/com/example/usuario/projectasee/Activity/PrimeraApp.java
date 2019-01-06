package com.example.usuario.projectasee.Activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.usuario.projectasee.Utils.RutaService;

public class PrimeraApp extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        //User default
        SharedPreferences edit=getSharedPreferences ( "User",MODE_PRIVATE );
        if(edit.getString ( "Nombre", "Luke" )=="" && edit.getString ( "UsPesoer", "70" )=="") {
            SharedPreferences.Editor prefsEditor = getSharedPreferences ( "User" , MODE_PRIVATE ).edit ();
            prefsEditor.putString ( "Nombre" , "Luke" );
            prefsEditor.putString ( "Apellidos" , "SkyWalker" );
            prefsEditor.putString ( "Sexo" , "H" );
            prefsEditor.putString ( "Edad" , "50" );
            prefsEditor.putString ( "Altura" , "165" );
            prefsEditor.putString ( "Peso" , "70" );
            prefsEditor.commit ();
        }

        Intent i=new Intent ( this,MainActivity.class );
        startActivity ( i );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        Intent service = new Intent(this, RutaService.class);
        stopService (service);
        NotificationManager nm = (NotificationManager) getSystemService ( NOTIFICATION_SERVICE );
        nm.cancelAll ();
    }
}
