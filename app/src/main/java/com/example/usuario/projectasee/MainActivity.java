package com.example.usuario.projectasee;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    private AdapterTabs AdapterTabs;

    private ViewPager mViewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById ( R.id. toolbar);
        setSupportActionBar ( toolbar );

        //Prepara la parte donde se situaran los fragments
        AdapterTabs = new AdapterTabs(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.contenedor);
        setupViewPager(mViewPager);

        //Tabs de la aplicacion
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText ( "Principal" );
        tabLayout.getTabAt(1).setText("Lista rutas");
        tabLayout.getTabAt(2).setText("Calendario");
        tabLayout.getTabAt(3).setText("Resumen");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterTabs adapter = new AdapterTabs(getSupportFragmentManager());
        adapter.addFragment(new FragmentPrincipal () , "Principal");
        adapter.addFragment(new FragmentListaRutas () , "Lista rutas");
        adapter.addFragment(new FragmentCalendario () , "Calendario");
        adapter.addFragment(new FragmentResumen () , "Resumen");
        viewPager.setAdapter ( adapter );
    }

}
