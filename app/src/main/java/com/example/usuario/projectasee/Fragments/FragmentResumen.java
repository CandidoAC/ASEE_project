package com.example.usuario.projectasee.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.RutasViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class FragmentResumen extends Fragment {

    private List<Ruta> ruteList = new ArrayList<> ();
    private RutasViewModel rutasViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resumenfragment ,container,false);
        float distancia=0;
        double calorias=0;
        Time tiempo=new Time ( 0,0,0 );
        rutasViewModel = ViewModelProviders.of(this).get(RutasViewModel.class);
        rutasViewModel.getAllRoutes().observe ( this , new Observer <List <Ruta>> () {
            @Override
            public void onChanged(@Nullable List <Ruta> rutas) {
                ruteList=rutas;
                float distancia=0;
                double calorias=0;
                Time tiempo=new Time ( 0,0,0 );
                for(int i=0;i<ruteList.size ();i++){
                    distancia=distancia+ruteList.get ( i ).getDistancia ();
                    calorias=calorias+ruteList.get ( i ).getCalorias();
                    tiempo.setHours ( tiempo.getHours ()+ruteList.get ( i ).getTiempo ().getHours () );
                    tiempo.setMinutes ( tiempo.getMinutes ()+ruteList.get ( i ).getTiempo ().getMinutes ());
                    tiempo.setSeconds ( tiempo.getSeconds ()+ruteList.get ( i ).getTiempo ().getSeconds ());

                }

                TextView t1=getView ().findViewById ( R.id.TextDistancia );
                t1.setText ( String.format ( "%.2f",distancia)+" kms" );

                TextView t2=getView ().findViewById ( R.id.TextCalorias );
                t2.setText ( String.format ( "%.2f",calorias) );

                TextView t3= getView ().findViewById ( R.id.TextTime);
                t3.setText ( tiempo.toString ());

            }
        } );
        for(int i=0;i<ruteList.size ();i++){
            distancia=distancia+ruteList.get ( i ).getDistancia ();
            calorias=calorias+ruteList.get ( i ).getCalorias();
            tiempo.setHours ( tiempo.getHours ()+ruteList.get ( i ).getTiempo ().getHours () );
            tiempo.setMinutes ( tiempo.getMinutes ()+ruteList.get ( i ).getTiempo ().getMinutes ());
            tiempo.setSeconds ( tiempo.getSeconds ()+ruteList.get ( i ).getTiempo ().getSeconds ());

        }

        TextView t1=view.findViewById ( R.id.TextDistancia );
        t1.setText ( String.valueOf ( distancia ) );

        TextView t2=view.findViewById ( R.id.TextCalorias );
        t2.setText ( String.valueOf ( calorias) );

        TextView t3= view.findViewById ( R.id.TextTime);
        t3.setText ( tiempo.toString ());


        return view;
    }

    public List <Ruta> getRuteList() {
        return ruteList;
    }

    public void setRuteList(List <Ruta> ruteList) {
        this.ruteList = ruteList;
    }
}