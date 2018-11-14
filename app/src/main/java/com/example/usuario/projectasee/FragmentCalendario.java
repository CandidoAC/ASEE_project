package com.example.usuario.projectasee;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentCalendario extends Fragment  {
    CompactCalendarView calendar;
    private SimpleDateFormat dateFormatMonth=new SimpleDateFormat ( "MMMM-yyyy", Locale.getDefault () );

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate ( R.layout.calendariofragment , container , false );
        calendar=(CompactCalendarView) view.findViewById ( R.id.calendar );
        calendar.setUseThreeLetterAbbreviation ( true );
        //addEvent ( ,"prueba" );
        Event ev1 = new Event (Color.RED, new Date ( 2018,11,15 ).getTime (), "prueba");
        Log.i ( "Events","Evento "+ev1.getData ()+" creado el dia "+ new Date ( 2018,11,15 ).getTime ());


        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.i ( "Events",String.valueOf(dateClicked.getTime ()));
                List <Event> listE = calendar.getEvents ( dateClicked.getTime () );

                if (!listE.isEmpty ()) {//TODO No se porque no entra nunca aquí teniendo un evento
                    Log.i ( "Events","Hay eventos xD" );
                    for (int i = 0; i < listE.size (); i++) {
                        Toast.makeText ( getActivity (),((i + 1) + ")" + listE.get ( i ).getData () + "\n"),Toast.LENGTH_LONG  );
                        Log.i ( "Events",listE.get ( i ).getData ().toString () );
                    }
                }else{
                    Log.i ( "Events","No hay eventos" );
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                TextView t=(TextView)getActivity ().findViewById ( R.id.calendarText );
                t.setText (dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        TextView t=(TextView)view.findViewById ( R.id.calendarText );
        t.setText (dateFormatMonth.format(calendar.getFirstDayOfCurrentMonth ()));
        return view;
    }

    public void addEvent(Date date,String nombre){

    }
}