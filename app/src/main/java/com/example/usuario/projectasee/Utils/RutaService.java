package com.example.usuario.projectasee.Utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

import com.example.usuario.projectasee.Fragments.FragmentPrincipal;
import com.example.usuario.projectasee.Modelo.Ruta;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RutaService extends Service {
    private Timer temporizador=new Timer (  );
    private static  final long Intervalo=1000;
    private int cronometro=0;
    private static FragmentPrincipal fragment;
    public static boolean isRunning=false;
    private Handler handle;

    public static  void setUpdateListener(FragmentPrincipal fr){
        fragment=fr;
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate() {
        super.onCreate ();
        handle=new Handler (){
            @Override
            public void handleMessage(Message msg) {
                fragment.setChronometer ( cronometro );
            }
        };
        iniciarCronometro();
    }

    @Override
    public void onDestroy() {
        pararCronometro();
        super.onDestroy ();
    }

    private void iniciarCronometro() {
        isRunning=true;
        temporizador.schedule ( new TimerTask () {
            @Override
            public void run() {
                cronometro=cronometro+1;
                handle.sendEmptyMessageDelayed  ( 0,10 );
                Log.i("Cronometro",String.valueOf (  cronometro));
            }
        },0,Intervalo );
    }

    private void pararCronometro() {
        isRunning=false;
        if(temporizador!=null){
            cronometro = 0;
            handle.sendEmptyMessageDelayed  ( 0,10 );
            Log.i("Cronometro",String.valueOf (  cronometro));
            temporizador.cancel ();
        }
    }

    public static boolean isRunning() {
        return isRunning;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
