package com.example.usuario.projectasee.Fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.Utils.Notification;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.ViewModels.RutasViewModel;
import com.example.usuario.projectasee.Utils.RutaService;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class FragmentPrincipal extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private LatLng firstUbi;
    private boolean firstUb = true;
    private Marker marker;
    private double lat = 0.0;
    private double lon = 0.0;
    private String m_Text = "";
    private MapView mMapView;
    private Time Time;
    private TextView focus;
    private Button start;
    private boolean clicked;
    private double calorias;
    private RutasViewModel rutasViewModel;
    private List<LatLng> lcoordenadas;
    Notification not;
    Boolean notificacion;
    Polyline p;
    private List<Polyline> ListP;
    Context context;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lcoordenadas = new ArrayList<LatLng>();
        ListP = new ArrayList<Polyline>();
        not = new Notification(getActivity());
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (p.getBoolean("switchId", false)) {
            notificacion = true;
        } else {
            notificacion = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.principalfragment, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        start = rootView.findViewById(R.id.startFinish);
        start.setText("Start");
        focus = (Chronometer) rootView.findViewById(R.id.chronometer);
        clicked = false;
        rutasViewModel = ViewModelProviders.of(this).get(RutasViewModel.class);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    clicked = true;
                    lcoordenadas = new ArrayList<LatLng>();
                    start.setText("Stop");
                    iniciarCronometro();
                    if (notificacion)
                        not.addNotification();
                } else {
                    firstUb = false;

                    focus.setText("00:00:00");
                    if (notificacion)
                        not.destroyNotifications();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Ruta completada");
                    alertDialog.setMessage("Escribe el nombre de la ruta:");

                    final EditText input = new EditText(getActivity());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("Confirmar", null);
                    alertDialog.setNegativeButton("Cancelar", null);
                    final AlertDialog dialog = alertDialog.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            m_Text = input.getText().toString();
                            context = getContext();
                            if (!m_Text.trim().isEmpty()) {
                                SharedPreferences prefs = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                                double timeMIN = Time.getHours() * 60 + Time.getMinutes() + Time.getSeconds() / 60.0;
                                double peso = Float.valueOf(prefs.getString("Peso", "70"));
                                calorias = 0.092 * (peso * 2.2) * timeMIN;
                                if(lcoordenadas.isEmpty () && ActivityCompat.checkSelfPermission ( getActivity () , android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( getActivity () , android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( getActivity () , android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
                                    LocationManager locationManager = (LocationManager) getActivity ().getSystemService ( Context.LOCATION_SERVICE );
                                    Location location = locationManager.getLastKnownLocation ( LocationManager.GPS_PROVIDER );
                                    if(location!=null) {
                                        lcoordenadas.add ( new LatLng ( location.getLatitude () , location.getLongitude () ) );
                                        Ruta ruta = new Ruta (m_Text , calorias , Time , lcoordenadas );
                                        rutasViewModel.insertarRuta ( ruta );
                                    }else{
                                        Toast.makeText ( context,"Error al crear ruta: No se encuentra GPS",Toast.LENGTH_SHORT ).show ();
                                    }
                                }else {
                                    Ruta ruta = new Ruta (m_Text , calorias , Time , lcoordenadas );
                                    rutasViewModel.insertarRuta ( ruta );
                                    clearRute();
                                }
                            } else {
                                Toast.makeText(context, "Por favor, indique un nombre para la ruta", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            clicked = false;
                            start.setText("Start");
                            dialog.dismiss();
                            pararCronometro();
                        }
                    });
                }
            }
        });
        RutaService.setUpdateListener(this);
        clicked = RutaService.isRunning();
        if (!clicked) {
            start.setText("Start");
            focus.setText("00:00:00");
        } else {
            start.setText("Stop");
            if (notificacion)
                not.addNotification();
        }
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        return rootView;
    }

    private void iniciarCronometro() {
        Intent service = new Intent(getActivity(), RutaService.class);
        getActivity().startService(service);
    }

    private void pararCronometro() {
        Intent service = new Intent(getActivity(), RutaService.class);
        getActivity().stopService(service);
    }

    public void setChronometer(int s) {
        int h = (int) s / 3600;
        s = s - h * 3600;
        int m = (int) s / 60;
        s = s - m * 60;
        Time = new Time(h, m, s);

        focus.setText((h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        miUbicacion();
    }

    public void anadirMarker(double lat, double lon) {
        LatLng coord = new LatLng(lat, lon);
        if (firstUb) {
            firstUbi = coord;
        }
        CameraUpdate ub = CameraUpdateFactory.newLatLngZoom(coord, 18);
        if (marker != null) {
            marker.remove();
        }
        marker = googleMap.addMarker(new MarkerOptions()
                .position(coord)
                .title("Mi ubicación")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icono)));
        googleMap.animateCamera(ub);
    }

    public void clearRute() {
        for (Polyline line : ListP) {
            line.remove();
        }
        ListP.clear();
        googleMap.clear();
    }


    public void actualizarUb(Location location) {

        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            anadirMarker(lat, lon);
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUb(location);
            if (clicked) {
                Log.i("GPS", "new line created");
                lcoordenadas.add(new LatLng(location.getLatitude(), location.getLongitude()));
                p = googleMap.addPolyline(new PolylineOptions()
                        .addAll(lcoordenadas)
                        .color(Color.RED)
                        .width(20));
                ListP.add ( p );
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

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
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizarUb(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
        }
    }
}
