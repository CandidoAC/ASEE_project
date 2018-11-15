package com.example.usuario.projectasee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class ActivityPerfil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.perfilactivity );

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               finish();
            }
        } );
        setSupportActionBar ( toolbar );

    }
    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences ( this );
        if(p.getString ( "listColor","" ).equals ( "Azul" )){
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        }else{
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
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
}
