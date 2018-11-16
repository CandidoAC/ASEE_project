package com.example.usuario.projectasee;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

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

import java.sql.Time;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


public class FragmentPrincipal extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private Marker marker;
    private double lat;
    private double lon;
    private String m_Text = "";
    private MapView mMapView;
    private Chronometer focus;
    private Button start;
    private boolean clicked;
    private int h, m, s;
    private float distancia, calorias;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate ( R.layout.principalfragment , container , false );

        mMapView = (MapView) rootView.findViewById ( R.id.mapView );
        mMapView.onCreate ( savedInstanceState );

        

        start = (Button) rootView.findViewById ( R.id.startFinish );
        start.setText ( "Start" );
        focus = (Chronometer) rootView.findViewById ( R.id.chronometer );
        clicked = false;

        focus.setOnChronometerTickListener ( new Chronometer.OnChronometerTickListener () {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime () - chronometer.getBase ();
                h = (int) (time / 3600000);
                m = (int) (time - h * 3600000) / 60000;
                s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText ( t );
            }
        } );
        focus.setText ( "00:00:00" );
        start.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    focus.setBase ( SystemClock.elapsedRealtime () );
                    clicked = true;
                    start.setText ( "Stop" );
                    focus.start ();
                } else {
                    clicked = false;
                    start.setText ( "Start" );
                    focus.stop ();
                    focus.setBase ( SystemClock.elapsedRealtime () );
                    focus.setText ( "00:00:00" );
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder ( getContext () );
                    alertDialog.setTitle ( "Ruta completada" );
                    alertDialog.setMessage ( "Escribe el nombre de la ruta:" );

                    final EditText input = new EditText ( getActivity () );
                    input.setInputType ( InputType.TYPE_CLASS_TEXT );
                    alertDialog.setView ( input );
                    alertDialog.setPositiveButton ( "Confirmar" , new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog , int which) {
                            m_Text = input.getText ().toString ();
                            distancia = h * 6000 + m * 6000 / 60 + s * 6000 / 3600;
                            calorias = 8/*13.75 * peso + 5 * altura - 6.76 * edad + 66*/;
                            Ruta ruta = new Ruta ( m_Text , distancia , 5 , new Time ( h , m , s ) );
                        }
                    } );

                    alertDialog.show ();
                }
            }
        } );

        try {
            MapsInitializer.initialize ( getActivity ().getApplicationContext () );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        mMapView.getMapAsync ( this );
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        miUbicacion ();
    }

    public void anadirMarker(double Lon , double lat) {
        LatLng coord = new LatLng ( Lon , lat );
        CameraUpdate ub = CameraUpdateFactory.newLatLngZoom ( coord , 16 );
        if (marker != null) {
            marker.remove ();
        }
        marker = googleMap.addMarker ( new MarkerOptions ().
                position ( coord ).
                title ( "Mi ubicación" )
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

    LocationListener locListener = new LocationListener () {
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
        LocationManager LocManger = (LocationManager) getActivity ().getSystemService ( LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission ( getActivity () , Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( getActivity () , Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocManger.getLastKnownLocation ( LocationManager.GPS_PROVIDER);
        actualizarUb ( location );
        LocManger.requestLocationUpdates ( LocManger.GPS_PROVIDER,500,0,locListener );
    }
}