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
        addEvent ( new Date ( 18,11,15 ),"prueba" );
        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.i ( "Events",dateClicked.toString () );
                List <Event> listE = calendar.getEvents ( dateClicked.getTime () );
                if (!listE.isEmpty ()) {//TODO No seporque no entra nunca aquí teniendo un eventp
                    Log.i ( "Events","Hay eventos xD" );
                    for (int i = 0; i < listE.size (); i++) {
                        Toast.makeText ( getActivity (),((i + 1) + ")" + listE.get ( i ).getData () + "\n"),Toast.LENGTH_LONG  );
                        Log.i ( "Events",listE.get ( i ).getData ().toString () );
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                TextView t=(TextView)getActivity ().findViewById ( R.id.calendarText );
                t.setText (dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        return view;
    }

    public void addEvent(Date date,String nombre){
        Event ev1 = new Event (Color.RED, date.getTime (), nombre);
        calendar.addEvent(ev1);
    }
}