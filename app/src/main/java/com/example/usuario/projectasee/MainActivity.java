package com.example.usuario.projectasee;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.system.Os;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    private AdapterTabs AdapterTabs;

    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
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
        //toolbar.setOnMenuItemClickListener ( this );
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
        adapter.addFragment ( new FragmentListaRutas () , "Lista rutas" );
        adapter.addFragment ( new FragmentCalendario () , "Calendario" );
        adapter.addFragment ( new FragmentResumen () , "Resumen" );
        viewPager.setAdapter ( adapter );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // switch (item.getItemId ()) {
         //   case R.id.ic_action_perfil:
           //     getSupportFragmentManager ().beginTransaction ().replace ( R.id.contenedor , new FragmentPerfil () , "Perfil" ).commit ();
             //   getSupportFragmentManager ().executePendingTransactions ();
               // break;
            //case R.id.ic_action_setting:
              //  getSupportFragmentManager ().beginTransaction ().replace ( R.id.contenedor , new FragmentConfiguracion () , "Configuración" ).commit ();
                //getSupportFragmentManager ().executePendingTransactions ();
                //break;
        //}
        int id = item.getItemId();


        Fragment fragment = null;
        if (id == R.id.ic_action_perfil) {
            fragment = new FragmentPerfil();
        }else if(id == R.id.ic_action_setting){
            fragment = new FragmentConfiguracion();

        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contenedor,fragment);
        //ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        ft.commit();
        getSupportFragmentManager ().executePendingTransactions ();
        return super.onOptionsItemSelected(item);
    }
}

