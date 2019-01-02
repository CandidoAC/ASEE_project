package com.example.usuario.projectasee.Activity;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.RutesViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PolylineOptions;

public class ActivityInfoRuta extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    Ruta r;
    private MapView mMapView;
    private RutesViewModel rutesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.rutaactivity );
        mMapView = (MapView) findViewById ( R.id.mapView2 );
        mMapView.onCreate (savedInstanceState );

        mMapView.onResume (); // needed to get the map to display immediately

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setNavigationIcon ( R.drawable.ic_arrow );
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        } );
        setSupportActionBar ( toolbar );

        try {
            MapsInitializer.initialize ( getApplicationContext () );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        mMapView.getMapAsync ( this);
        rutesViewModel = ViewModelProviders.of(this).get(RutesViewModel.class);
        Bundle b = getIntent().getExtras();
        Integer id=b.getInt ( "ruteId" );
        r=rutesViewModel.getRuta ( id );

        TextView t=findViewById ( R.id.TextNombreRuta );
        t.setText ( r.getNombre ());

        TextView t1=findViewById ( R.id.TextDistanciaRuta );
        t1.setText ( String.format ( "%.2f",r.getDistancia ()) + " kms");

        TextView t2=findViewById ( R.id.TextCaloriasRuta );
        t2.setText ( String.format ( "%.2f",r.getCalorias()) );

        TextView t3= findViewById ( R.id.TextTimeRuta);
        t3.setText ( r.getTiempo ().toString ());

        Button bModificar=findViewById ( R.id.Modificar );
        bModificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder (v.getContext ());
                alertDialog.setMessage ( "Escribe el nombre de la ruta:" );

                final EditText input = new EditText ( v.getContext ());
                input.setInputType ( InputType.TYPE_CLASS_TEXT );
                alertDialog.setView ( input );
                alertDialog.setPositiveButton ( "Confirmar" , new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {
                        String m_Text = input.getText ().toString ();
                        Ruta ruta = new Ruta ( r.getId () , m_Text , r.getCalorias (), r.getTiempo () , r.getLcoordenadas ());
                        rutesViewModel.modificarRuta ( ruta );

                        TextView t=findViewById ( R.id.TextNombreRuta );
                        t.setText ( String.valueOf ( ruta.getNombre ()));
                    }
                } );

                alertDialog.show ();
            }
        } );
        Button bBorrar=findViewById ( R.id.Borrar );
        bBorrar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                rutesViewModel.borrarRuta ( r );
                Intent i=new Intent ( getBaseContext (),MainActivity.class );
                startActivity ( i );
            }
        } );

    }

    @Override
    protected void onStart() {
        super.onStart ();
        SharedPreferences p=PreferenceManager.getDefaultSharedPreferences ( this );
        if(p.getString ( "listColor","" ).equals ( "Azul" )){
            findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.defaultBackground ) );
        }else{
            if (p.getString ( "listColor" , "" ).equals ( "Blanco" )) {
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.BlancoBackground ) );
            }else{
                findViewById ( R.id.main_content ).setBackgroundColor ( getResources ().getColor ( R.color.VerdeBackground ) );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu_main , menu );
        return true;
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (r.getLcoordenadas ().size () >1){
            googleMap.addPolyline ( new PolylineOptions ()
                    .addAll ( r.getLcoordenadas () )
                    .color ( Color.RED )
                    .width ( 20 ) );

            CameraUpdate ub=CameraUpdateFactory.newLatLngZoom ( r.getLcoordenadas ().get ( (int) r.getLcoordenadas ().size () / 2 ) , 14 );
            googleMap.animateCamera ( ub );
        }else{
            CameraUpdate ub = CameraUpdateFactory.newLatLngZoom ( r.getLcoordenadas ().get ( 0 ), 14 );
            googleMap.animateCamera ( ub );
        }
        this.googleMap.getUiSettings ().setAllGesturesEnabled ( false );
        this.googleMap.getUiSettings ().setZoomGesturesEnabled (true);
    }

}