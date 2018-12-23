package com.example.usuario.projectasee.Activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.Fragments.SettingFragment;

public class ActivityConfiguracion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.settingactivity );

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, new SettingFragment())
                .commit();
        setSupportActionBar ( toolbar );
    }

    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences ( this );
        if(p.getString ( "listColor","" ).equals ( "Azul" )){
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        }else {
            if (p.getString ( "listColor" , "" ).equals ( "Blanco" )) {
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
            }else{
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.VerdeBackground ) );
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.menu_main , menu );*/
        getMenuInflater ().inflate ( R.menu.menu_main , menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case  R.id.ic_action_setting:
                Log.i("setting","Configuracion");
                Intent i=new Intent (this,ActivityConfiguracion.class);
                startActivity ( i );
                Log.i("setting","Configuracion terminada");
                break;
            case  R.id.ic_action_perfil:
                Log.i("perfil","Perfil");
                i=new Intent (this,ActivityPerfil.class);
                startActivity ( i );
                Log.i("perfil","Perfil terminado");
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
    // Creates and displays a notification
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addNotification() {
        Intent i = new Intent ( this , MainActivity.class );
        PendingIntent pi = PendingIntent.getActivity ( this , 0 , i , 0 );
        Notification.Builder not = new Notification.Builder ( this );
        not.setContentTitle ( "La aplicacion esta activa" ).
                setSmallIcon ( R.mipmap.icono_app_round ).
                setLargeIcon ( BitmapFactory.decodeResource ( getResources () , R.mipmap.icono_app_round ) ).
                setContentIntent ( pi ).
                setVibrate ( new long[]{Notification.DEFAULT_VIBRATE} ).
                setPriority ( Notification.PRIORITY_MAX );
        NotificationManager nm = (NotificationManager) this.getSystemService ( Context.NOTIFICATION_SERVICE );
        nm.notify ( 0 , not.build () );
    }
    }

