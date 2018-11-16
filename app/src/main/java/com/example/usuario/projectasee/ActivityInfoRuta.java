package com.example.usuario.projectasee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    public static Ruta rute;
    private double lat=0.0;
    private double lon=0.0;
    private static final int PETICION_PERMISO_LOCALIZACION=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.rutaactivity );
        rute=new Ruta (  );
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
        //new AsyncGetRuta().execute ( 9 );
        //Log.i ( "Ruta",rute.getNombre () );
        try {
            MapsInitializer.initialize ( getApplicationContext () );
        } catch (Exception e) {
            e.printStackTrace ();
        }


        mMapView.getMapAsync ( this);
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
        CameraUpdate ub = CameraUpdateFactory.newLatLngZoom ( coord , 18 );
        if (marker != null) {
            marker.remove ();
        }
        marker = googleMap.addMarker ( new MarkerOptions ()
                .position ( coord )
                .title ( "Mi ubicación" )
                .icon ( BitmapDescriptorFactory.fromResource ( R.mipmap.icono ) ) );
        googleMap.animateCamera ( ub );
    }

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 5000,0,locListener);
        }
    }
    class AsyncGetRuta extends AsyncTask<Integer, Void, Ruta> {
        @Override
        protected Ruta doInBackground(Integer... id) {
            AppDatabase db = AppDatabase.getAppDatabase(ActivityInfoRuta.this);
            return db.daoRutas().getRuta(id[0]);
        }

        @Override
        protected void onPostExecute(Ruta ruta) {
            super.onPostExecute(ruta);
            rute=ruta;
        }
    }

}