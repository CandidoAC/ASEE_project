package com.example.usuario.projectasee.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import com.example.usuario.projectasee.Database.AppDatabase;
import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;
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

import static android.content.Context.MODE_PRIVATE;

public class FragmentPrincipal extends Fragment  implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private Marker marker;
    private double lat=0.0;
    private double lon=0.0;
    private String m_Text = "";
    private MapView mMapView;
    private Chronometer focus;
    private Button start;
    private boolean clicked;
    private int h, m,s;
    private float distancia;
    private double calorias;
    private SharedPreferences prefs;

    private static final int PETICION_PERMISO_LOCALIZACION=101;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences("User", MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate ( R.layout.principalfragment , container , false );

        mMapView = (MapView) rootView.findViewById ( R.id.mapView );
        mMapView.onCreate ( savedInstanceState );

        mMapView.onResume (); // needed to get the map to display immediately

        start = rootView.findViewById(R.id.startFinish);
        start.setText("Start");
        focus = (Chronometer) rootView.findViewById(R.id.chronometer);
        clicked = false;
        focus.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                h   = (int)(time /3600000);
                m = (int)(time - h*3600000)/60000;
                s= (int)(time - h*3600000- m*60000)/1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });
        focus.setText("00:00:00");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked){
                    focus.setBase(SystemClock.elapsedRealtime());
                    clicked = true;
                    start.setText("Stop");
                    focus.start();
                }else{
                    clicked = false;
                    start.setText("Start");
                    focus.stop();
                    focus.setBase(SystemClock.elapsedRealtime());
                    focus.setText("00:00:00");
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Ruta completada");
                    alertDialog.setMessage("Escribe el nombre de la ruta:");

                    final EditText input = new EditText(getActivity());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            long time = SystemClock.elapsedRealtime() - focus.getBase();
                            int hs   = (int)(time /3600000);
                            int mins = (int)(time - h*3600000)/60000;
                            int ss= (int)(time - h*3600000- m*60000)/1000 ;
                            distancia = hs * 6000 + mins * 6000/60 + ss * 6000/3600;
                            float peso = Float.valueOf(prefs.getString("Peso", String.valueOf(0)));
                            int altura = Integer.valueOf(prefs.getString("Altura", String.valueOf(0)));
                            int edad = Integer.valueOf(prefs.getString("Edad", String.valueOf(0)));
                            if(peso != 0 && altura != 0 && edad != 0){
                                calorias = 13.75 * peso + 5 * altura - 6.76 * edad + 66;
                            }else{
                                calorias = 13.75 + 5 - 6.76 + 66;
                            }

                            Ruta ruta = new Ruta(0,m_Text,distancia,calorias,new Time(hs,mins,ss));
                            new AsyncInsert().execute(ruta);
                        }
                    });

                    alertDialog.show();
                }
            }
        });

        try {
            MapsInitializer.initialize ( getActivity ().getApplicationContext () );
        } catch (Exception e) {
            e.printStackTrace ();
        }


        mMapView.getMapAsync ( this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        miUbicacion ();
    }

    public void anadirMarker(double lat , double lon) {
        LatLng coord = new LatLng ( lat , lon );
        CameraUpdate ub = CameraUpdateFactory.newLatLngZoom ( coord , 20 );
        if (marker != null) {
            marker.remove ();
        }
        marker = googleMap.addMarker ( new MarkerOptions()
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
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER );
            actualizarUb(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 5000,0,locListener);
        }
    }

    class AsyncInsert extends AsyncTask<Ruta, Ruta, Ruta> {
        @Override
        protected Ruta doInBackground(Ruta... rutas) {
            AppDatabase db = AppDatabase.getAppDatabase(getActivity());
            db.daoRutas().anadirRuta(rutas[0]);
            return rutas[0];
        }

        @Override
        protected void onPostExecute(Ruta ruta) {
            super.onPostExecute(ruta);

        }
    }
}

