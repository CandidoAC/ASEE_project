package com.example.usuario.projectasee.Adapters;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.usuario.projectasee.ViewModels.EventsViewModel;
import com.example.usuario.projectasee.Fragments.FragmentListaEvents;
import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.R;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{
    private List<Event> eventList;
    private FragmentListaEvents fragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        Integer id;
        private FragmentListaEvents fragment;
        ImageButton mod,borrar;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.NombreEvent);
            mod=(ImageButton) view.findViewById ( R.id.ModificarE );

            borrar=(ImageButton) view.findViewById ( R.id.BorrarE );
        }

        public void bind(final Event event, final FragmentListaEvents frag) {
            id=event.getId ();
            nombre.setText ( event.getNombre () );
            this.fragment=frag;

            final EventsViewModel eventsViewModel = ViewModelProviders.of(fragment ).get(EventsViewModel.class);
            mod.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    alertDialog.setMessage("Escribe el nombre del evento:");

                    final EditText input = new EditText(v.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            Event ev=eventsViewModel.getEvent ( id );
                            ev.setNombre ( m_Text );
                            eventsViewModel.updateEvent ( ev );

                            nombre.setText(String.valueOf(ev.getNombre()));
                        }
                    });

                    alertDialog.show();
                }
            });
            borrar.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    frag.delete ( id );
                }
            } );

        }
    }


    public EventsAdapter(List<Event> eventsList,FragmentListaEvents frag) {

        this.eventList = eventsList;
        this.fragment=frag;
    }
    public List<Event> eventList () {
        return this.eventList;
    }

    public void setEventList(List <Event> eventsList) {
        this.eventList = eventsList;
        notifyDataSetChanged ();
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_listevents, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(eventList.get(position),fragment);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
