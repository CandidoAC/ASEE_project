package com.example.usuario.projectasee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentCalendario extends Fragment  {
    List<Event> listE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate ( R.layout.calendariofragment , container , false );
        listE=new ArrayList <Event> (  );
        CalendarView c=(CalendarView) view.findViewById ( R.id.calendar );
        c.setOnDateChangeListener ( new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange(CalendarView view , final int year , final int month , final int dayOfMonth) {
                Log.i("Calendar","Dia cambiado");
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext ());
                builder.setMessage("Escribe el nombre del evento")
                        .setTitle("Evento");
                final EditText input=new EditText ( getActivity () );
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                    builder.setPositiveButton ( "Confirmar" , new DialogInterface.OnClickListener () {
                        public void onClick(DialogInterface dialog , int id) {
                            if(!input.getText ().toString ().equals ( "" )) {
                                Event e = new Event ( new Date ( year , month , dayOfMonth ) , input.getText ().toString () );
                                addEvent ( e );
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
        return view;
    }

    public void addEvent(Event e){
        listE.add ( e );
    }
}