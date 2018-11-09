package com.example.usuario.projectasee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentResumen extends Fragment {

    private List<Ruta> ruteList = new ArrayList<> ();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resumenfragment ,container,false);
        float distancia=0;
        int calorias=0;
        Time tiempo=new Time ( 0,0,0 );
        for(int i=0;i<ruteList.size ();i++){
            distancia=distancia+ruteList.get ( i ).getDistancia ();
            calorias=calorias+ruteList.get ( i ).getCalorías ();
            tiempo.setHours ( tiempo.getHours ()+ruteList.get ( i ).getTiempo ().getHours () );
            tiempo.setMinutes ( tiempo.getMinutes ()+ruteList.get ( i ).getTiempo ().getMinutes ());
            tiempo.setSeconds ( tiempo.getSeconds ()+ruteList.get ( i ).getTiempo ().getSeconds ());

        }

         t1=view.findViewById ( R.id.TextDistancia );
        t1.setText ( String.valueOf ( distancia ) );

        EditText t2=view.findViewById ( R.id.TextCalorias );
        t2.setText ( String.valueOf ( calorias) );

        EditText t3= view.findViewById ( R.id.TextTime);
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