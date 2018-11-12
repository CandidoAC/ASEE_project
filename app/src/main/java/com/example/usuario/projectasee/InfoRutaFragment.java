package com.example.usuario.projectasee;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoRutaFragment extends Fragment {
    Ruta ruta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Configuracion","Carga el layout de info_rutas");
        getFragmentManager ().beginTransaction ().hide(getFragmentManager ().findFragmentById ( R.id.contenedor2));
        View view = inflater.inflate(R.layout.rutafragment ,container,false);
        return view;
    }
}
