package com.example.usuario.projectasee;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private AdapterTabs AdapterTabs;

    private ViewPager mViewPager;
    private Toolbar toolbar;

    List<Ruta> ruteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        ruteList=new ArrayList<Ruta> () ;
        prepareRuteData ();

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        //Prepara la parte donde se situaran los fragments
        AdapterTabs = new AdapterTabs ( getSupportFragmentManager () );

        mViewPager = (ViewPager) findViewById ( R.id.contenedor );
        setupViewPager ( mViewPager );

        //Tabs de la aplicacion
        TabLayout tabLayout = (TabLayout) findViewById ( R.id.tabs );
        tabLayout.setupWithViewPager ( mViewPager );

        tabLayout.getTabAt ( 0 ).setText ( "Principal" );
        tabLayout.getTabAt ( 1 ).setText ( "Lista rutas" );
        tabLayout.getTabAt ( 2 ).setText ( "Calendario" );
        tabLayout.getTabAt ( 3 ).setText ( "Resumen" );

        //menu
        toolbar.setOnMenuItemClickListener ( this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.menu_main , menu );
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterTabs adapter = new AdapterTabs ( getSupportFragmentManager () );
        adapter.addFragment ( new FragmentPrincipal () , "Principal" );
        FragmentListaRutas fr=new FragmentListaRutas ();
        fr.setRuteList ( ruteList );
        adapter.addFragment ( fr , "Lista rutas" );
        adapter.addFragment ( new FragmentCalendario () , "Calendario" );
        FragmentResumen fr1=new FragmentResumen ();
        fr1.setRuteList ( ruteList );
        adapter.addFragment ( fr1, "Resumen" );
        viewPager.setAdapter ( adapter );
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.ic_action_perfil:
                getSupportFragmentManager ().beginTransaction ().replace ( R.id.contenedor , new FragmentPerfil () , "Perfil" ).commit ();
                getSupportFragmentManager ().executePendingTransactions ();
                break;
            case R.id.ic_action_setting:
                getSupportFragmentManager ().beginTransaction ().replace ( R.id.contenedor , new FragmentConfiguracion () , "Configuración" ).commit ();
                getSupportFragmentManager ().executePendingTransactions ();
                break;
        }
        return true;
    }

    private void prepareRuteData() {
        Ruta rute = new Ruta ("Nombre1",1,5,new Time (0,1,25 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre2",4,3,new Time ( 1,0,25 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre3",6,1,new Time ( 0,1,0 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre4",3,6,new Time ( 0,0,15 ) );
        ruteList.add ( rute );

    }

}

