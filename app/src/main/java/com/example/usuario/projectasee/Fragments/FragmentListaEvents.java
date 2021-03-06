package com.example.usuario.projectasee.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.projectasee.Adapters.EventsAdapter;
import com.example.usuario.projectasee.ViewModels.EventsViewModel;
import com.example.usuario.projectasee.Modelo.Event;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.ViewModels.EventsViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentListaEvents extends DialogFragment {
    private List<Event> EventList;
    private RecyclerView recyclerView;
    private EventsAdapter mAdapter;
    private EventsViewModel eventsViewModel;


    public static FragmentListaEvents newInstance(String title,Date date) {
        FragmentListaEvents frag = new FragmentListaEvents ();
        Bundle args = new Bundle ();
        args.putString ( "title" , title );
        args.putLong ( "Date",date.getTime () );
        frag.setArguments ( args );
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        EventList=new ArrayList <> (  );
        mAdapter=new EventsAdapter ( EventList,this );
        Bundle args = new Bundle ( getArguments () );
        Date date=new Date(args.getLong ( "Date" ));
        eventsViewModel = ViewModelProviders.of (this).get ( EventsViewModel.class );
        eventsViewModel.getEventsByDate (date).observe ( this , new Observer <List <Event>> () {
            @Override
            public void onChanged(@Nullable List <Event> eventos) {
                if(eventos.size ()==0){
                    getDialog ().dismiss ();
                }
                mAdapter.setEventList ( eventos );
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Bundle args = new Bundle(getArguments ());
        getDialog ().setTitle ( args.getString("title", null) );
        View view = inflater.inflate(R.layout.listaeventsfragment ,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_viewEvent);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (getActivity ().getBaseContext ());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.addItemDecoration(new DividerItemDecoration (getActivity (), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public void delete(int id){
        eventsViewModel.borrarEvents ( eventsViewModel.getEvent ( id ));
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("onresumeListEvents","**************");
    }

}