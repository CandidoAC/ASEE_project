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
