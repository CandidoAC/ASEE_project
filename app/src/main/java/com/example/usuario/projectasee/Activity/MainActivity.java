package com.example.usuario.projectasee.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.projectasee.Adapters.AdapterTabs;
import com.example.usuario.projectasee.AppActiva;
import com.example.usuario.projectasee.Fragments.FragmentPrincipal;
import com.example.usuario.projectasee.Fragments.*;
import com.example.usuario.projectasee.Fragments.FragmentListaRutas;
import com.example.usuario.projectasee.Fragments.FragmentResumen;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.RutaService;

public class  MainActivity extends AppCompatActivity {

    private com.example.usuario.projectasee.Adapters.AdapterTabs AdapterTabs;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    //List <Ruta> ruteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        Intent service = new Intent(this, AppActiva.class);
        startService(service);

        setContentView ( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        //User default
        SharedPreferences.Editor prefsEditor = getSharedPreferences("User", MODE_PRIVATE).edit();
        prefsEditor.putString ( "Nombre" , "Luke" );
        prefsEditor.putString ( "Apellidos" , "SkyWalker" );
        prefsEditor.putString ( "Sexo" , "H" );
        prefsEditor.putString ( "Edad" , "50" );
        prefsEditor.putString ( "Altura" , "165" );
        prefsEditor.putString ( "Peso" , "70" );
        prefsEditor.commit ();


        //Prepara la parte donde se situaran los fragments
        AdapterTabs = new AdapterTabs ( getSupportFragmentManager () );

        mViewPager = (ViewPager) findViewById ( R.id.contenedor2 );
        mViewPager.setOffscreenPageLimit ( 3 );
        setupViewPager ( mViewPager );

        //Tabs de la aplicacion
        TabLayout tabLayout = (TabLayout) findViewById ( R.id.tabs );
        tabLayout.setupWithViewPager ( mViewPager );

        tabLayout.getTabAt ( 0 ).setText ( "Principal" );
        tabLayout.getTabAt ( 1 ).setText ( "Lista rutas" );
        tabLayout.getTabAt ( 2 ).setText ( "Calendario" );
        tabLayout.getTabAt ( 3 ).setText ( "Resumen" );

    }

    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences ( this );
        if (p.getString ( "listColor" , "" ).equals ( "Azul" )) {
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        } else {
            if (p.getString ( "listColor" , "" ).equals ( "Blanco" )) {
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
            } else {
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.VerdeBackground ) );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu_main , menu );
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterTabs adapter = new AdapterTabs ( getSupportFragmentManager () );
        adapter.addFragment ( new FragmentPrincipal () , "Principal" );
        FragmentListaRutas fr = new FragmentListaRutas ();
        adapter.addFragment ( fr , "Lista rutas" );
        adapter.addFragment ( new FragmentCalendario () , "Calendario" );
        FragmentResumen fr1 = new FragmentResumen ();
        adapter.addFragment ( fr1 , "Resumen" );
        viewPager.setAdapter ( adapter );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId ()) {
            case R.id.ic_action_setting:
                Log.i ( "setting" , "Configuracion" );
                Intent i = new Intent ( this , ActivityConfiguracion.class );
                startActivity ( i );
                Log.i ( "setting" , "Configuracion terminada" );
                break;
            case R.id.ic_action_perfil:
                Log.i ( "perfil" , "Perfil" );
                i = new Intent ( this , ActivityPerfil.class );
                startActivity ( i );
                Log.i ( "perfil" , "Perfil terminado" );
                break;

            default:
                return super.onOptionsItemSelected ( item );
        }
        return true;
    }
}

