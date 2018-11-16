package com.example.usuario.projectasee;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentCalendario extends Fragment  {
    static List<Event> listE;
    private static String dateView;
    private static Date date;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate ( R.layout.calendariofragment , container , false );
        listE=new ArrayList <Event> (  );
        date=new Date();
        CalendarView c=(CalendarView) view.findViewById ( R.id.calendar );
        FloatingActionButton f=view.findViewById ( R.id.floatingActionButton );
        f.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext ());
                builder.setMessage("Escribe el nombre del evento")
                        .setTitle("Evento");
                final EditText input=new EditText ( getActivity () );
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton ( "Confirmar" , new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog , int id) {
                        if(!input.getText ().toString ().equals ( "" )) {
                            dateView=input.getText ().toString ();
                            showDatePickerDialog ();
                        }
                    }
                } );
                builder.setNegativeButton ( "Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss ();
                    }
                } );
                builder.show ();
            }

        } );
        c.setOnDateChangeListener ( new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange(CalendarView view , final int year , final int month , final int dayOfMonth) {
                TextView t=(TextView) getView ().findViewById ( R.id.calendarText );
                t.setText("");
                verEvents ( new Date ( year ,month,dayOfMonth ) );
            }
        } );
        return view;
    }


    public void verEvents(Date date){
        String s="";
        for (int i= 0;i<listE.size ();i++){
            Event e=(Event)listE.get ( i );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Log.i ( "Cale",e.getDate().toString() );
            Log.i ( "Cale",date.toString () );
            if(e.getDate().toString().equals(date.toString())) {
                Log.i("Calendar", "Igual fecha,añadiendo evento a mostrar");
                s += String.valueOf(i + 1) + "." + e.getNombre() + '\n';
            }
            }

        Log.i ( "Calendar2",s );
        TextView t=(TextView) getView ().findViewById ( R.id.calendarText );
        t.setText ( s );
    }


    private void showDatePickerDialog() {
        android.app.DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getActivity ().getFragmentManager(), "datePicker");

    }
    public static class DatePickerFragment extends android.app.DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog (getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date=new Date ( year,monthOfYear,dayOfMonth );
            Event e = new Event (0, date ,  dateView);
            addEvent ( e );

        }
        public void addEvent(Event e){
            Log.i("Calendar","Añadiendo evento con nombre "+e.getNombre()+" para el día "+e.getDate().toString ());//quitar 1900 años
            listE.add ( e );
        }

    }
}
