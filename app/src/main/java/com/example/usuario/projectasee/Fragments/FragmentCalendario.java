package com.example.usuario.projectasee.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.example.usuario.projectasee.Database.AppDatabase;
import com.example.usuario.projectasee.EventsViewModel;
import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FragmentCalendario extends Fragment  {
    static List<Event> listE;
    private static String dateView;
    private static Date date;
    private EventsViewModel eventsViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable final ViewGroup container , @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate ( R.layout.calendariofragment , container , false );
        date = new Date();
        listE=new ArrayList<Event> () ;
        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        eventsViewModel.getAllEvents ().observe ( this , new Observer <List <Event>> () {
            @Override
            public void onChanged(@Nullable List <Event> events) {
                listE=events;
            }
        } );
        final CalendarView c = view.findViewById ( R.id.calendar );
        FloatingActionButton f = view.findViewById ( R.id.floatingActionButton );
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
            public void onSelectedDayChange(CalendarView Cview , final int year , final int month , final int dayOfMonth) {
                TextView t=(TextView) getView ().findViewById ( R.id.calendarText );
                t.setText("");
                verEvents ( new Date ( year ,month,dayOfMonth ) );
            }
        } );
        return view;
    }


    public void verEvents(Date date){
        String s="";
        int j=1;
        for (int i= 0;i<listE.size ();i++){
            Event e=(Event)listE.get ( i );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Log.i ( "Cale",e.getDate().toString() );
            Log.i ( "Cale",date.toString () );
            if(e.getDate().toString().equals(date.toString())) {
                Log.i("Calendar", "Igual fecha,añadiendo evento a mostrar");
                s += String.valueOf(j) + "." + e.getNombre() + '\n';
                j++;
            }
            }

        Log.i ( "Calendar2",s );
        TextView t=(TextView) getView ().findViewById ( R.id.calendarText );
        t.setText ( s );
    }


    private void showDatePickerDialog() {
        android.app.DialogFragment datePicker = new DatePickerFragment();
        ((DatePickerFragment) datePicker).setEventsViewModel ( eventsViewModel );
        datePicker.show(getActivity ().getFragmentManager(), "datePicker");

    }
    public static class DatePickerFragment extends android.app.DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        EventsViewModel eventsViewModel;

        public void setEventsViewModel(EventsViewModel eventsViewModel) {
            this.eventsViewModel=eventsViewModel;
        }

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
            Event e = new Event (date ,  dateView);
            addEvent ( e );

        }
        public void addEvent(Event e){
            Log.i("Calendar","Añadiendo evento con nombre "+e.getNombre()+" para el día "+e.getDate().toString ());//quitar 1900 años
            eventsViewModel.insertarEvent ( e );
        }

    }

}
