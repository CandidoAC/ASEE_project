package com.example.usuario.projectasee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FragmentPrincipal extends Fragment   {
    private GoogleMap googleMap;
    private String m_Text = "";
    private MapView mMapView;
    private Chronometer focus;
    private Button start;
    private boolean clicked;
    private int h, m,s;
    private float distancia, calorias;
    private Bundle time;
    private Calendar c;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = Calendar.getInstance();
        prefsEditor = this.getActivity().getSharedPreferences("User", MODE_PRIVATE).edit();
        prefs = this.getActivity().getSharedPreferences("User", MODE_PRIVATE);

//        time = new Bundle();
//        setArguments(time);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate ( R.layout.principalfragment , container , false );

        mMapView = (MapView) rootView.findViewById ( R.id.mapView );
        mMapView.onCreate ( savedInstanceState );

        mMapView.onResume (); // needed to get the map to display immediately
//        if(savedInstanceState != null){
//            focus.setText(savedInstanceState.getString("Tiempo",null));
//            Log.i("España", "JAJAJAJAJAJAJAJAJAJAcv "+ focus.getText());
//            focus.start();
//        }

        start = (Button) rootView.findViewById(R.id.startFinish);
        start.setText("Start");
        focus = (Chronometer) rootView.findViewById(R.id.chronometer);
        clicked = false;
//        if(focus.getText().toString().equals("")){
//            focus.setText("00:00:00");
//            Log.i("SalTiempo", "JAJAJAJAJAJAJAJAQue hago aqui: ");
//        }

//        if( !focus.getText().toString().equals("00:00:00")/*prefs.getString("timeBackground",null)!= null*/){
//            int endTime = c.get(Calendar.SECOND);
//            long secondsElapsed = endTime - Integer.valueOf(prefs.getString("timeBackground",null));
//            long timeChrono = Integer.valueOf(focus.getText().toString());
//            long totaltime = secondsElapsed + timeChrono;
//            prefsEditor.remove("timeBackground");
//            focus.setText(String);
//            long time = SystemClock.elapsedRealtime() - focus.getBase();
//            Log.i("SalTiempo", "JAJAJAJAJAJAJAJA: " + time);
//            h   = (int)(time /3600000);
//            m = (int)(time - h*3600000)/60000;
//            s= (int)(time - h*3600000- m*60000)/1000 ;
//            String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
//            focus.setText(t);
//            focus.start();
//        }
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
                            calorias = 8/*13.75 * peso + 5 * altura - 6.76 * edad + 66*/;
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

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        Log.i("España", "JAJAJAJAJAJAJAJAJAJAsi "+ focus.getText().toString());
//        savedInstanceState.putString("Tiempo", focus.getText().toString());
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if(savedInstanceState!=null) {
//            m_Text = savedInstanceState.getString("Tiempo", null);
//            Log.i("España", "JAJAJAJAJAJAJAJAJAJAac "+ m_Text);
//        }
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        int startTime = c.get(Calendar.SECOND);
//        prefsEditor.putString("timeBackground",String.valueOf(startTime));
//        prefsEditor.commit();
//    }


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


    class AsyncInsert extends AsyncTask<Ruta, Void, Ruta> {
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

