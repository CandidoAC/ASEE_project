package com.example.usuario.projectasee;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

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
                .replace(R.id.fragment, new SettingFragment ())
                .commit();
        setSupportActionBar ( toolbar );
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
}

