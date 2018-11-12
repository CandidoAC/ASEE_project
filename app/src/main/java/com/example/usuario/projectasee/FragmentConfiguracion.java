package com.example.usuario.projectasee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentConfiguracion extends Fragment {

    /*@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        Log.i("Configuracion","Carga el layout de setting");
        View view = inflater.inflate(R.layout.settingfragment ,container,false);
        Log.i("Prueba", container.toString());
        return view;
    }
}
