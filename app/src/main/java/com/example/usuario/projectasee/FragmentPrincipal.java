package com.example.usuario.projectasee;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;


public class FragmentPrincipal extends Fragment   {
    private GoogleMap googleMap;
    MapView mMapView;
    Chronometer focus;
    Button start, stop, reset;
    boolean clicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate ( R.layout.principalfragment , container , false );

        mMapView = (MapView) rootView.findViewById ( R.id.mapView );
        mMapView.onCreate ( savedInstanceState );

        mMapView.onResume (); // needed to get the map to display immediately

        start = (Button) rootView.findViewById(R.id.startFinish);
        start.setText("Start");
        focus = (Chronometer) rootView.findViewById(R.id.chronometer);
        clicked = false;

        focus.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
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
                }
            }
        });

        try {
            MapsInitializer.initialize ( getActivity ().getApplicationContext () );
        } catch (Exception e) {
            e.printStackTrace ();
        }

        mMapView.getMapAsync ( new OnMapReadyCallback () {
            @Override
            public void onMapReady(GoogleMap mMap) {
                /*googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission ( mMapView.getContext () , Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (  mMapView.getContext ()  , Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled ( true );

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                // create marker
                MarkerOptions marker = new MarkerOptions().position(sydney).title("Hello Maps");

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                googleMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );
                googleMap.getUiSettings ().setAllGesturesEnabled ( false );
               // CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(17).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            */}
        });
        return rootView;
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

}

