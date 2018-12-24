package com.example.usuario.projectasee.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.usuario.projectasee.Activity.ActivityConfiguracion;
import com.example.usuario.projectasee.Activity.ActivityPerfil;
import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.RutesViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityInfoRuta extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private Marker marker;
    private MapView mMapView;
    private RutesViewModel rutesViewModel;
    private double lat=0.0;
    private double lon=0.0;
    private static final int PETICION_PERMISO_LOCALIZACION=101;

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
        Ruta r=rutesViewModel.getRuta ( id );
        TextView t=findViewById ( R.id.TextNombreRuta );
        t.setText ( String.valueOf ( r.getNombre ()));

        TextView t1=findViewById ( R.id.TextDistanciaRuta );
        t1.setText ( String.valueOf ( r.getDistancia () ));

        TextView t2=findViewById ( R.id.TextCaloriasRuta );
        t2.setText ( String.valueOf ( r.getCalorias()) );

        TextView t3= findViewById ( R.id.TextTimeRuta);
        t3.setText ( r.getTiempo ().toString ());
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
        /*MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.menu_main , menu );*/
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
        miUbicacion ();
    }

    public void anadirMarker(double lat , double lon) {
        LatLng coord = new LatLng ( lat , lon );
        CameraUpdate ub = CameraUpdateFactory.newLatLngZoom ( coord , 16 );
        if (marker != null) {
            marker.remove ();
        }
        marker = googleMap.addMarker ( new MarkerOptions ()
                .position ( coord )
                .title ( "Mi ubicación" )
                .icon ( BitmapDescriptorFactory.fromResource ( R.mipmap.icono ) ) );
        googleMap.animateCamera ( ub );
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }*/

    public void actualizarUb(Location location) {
        if (location != null) {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            anadirMarker ( lat , lon );
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUb ( location );
        }

        @Override
        public void onStatusChanged(String provider , int status , Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER );
            actualizarUb(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 15000,0,locListener);
        }
    }

}