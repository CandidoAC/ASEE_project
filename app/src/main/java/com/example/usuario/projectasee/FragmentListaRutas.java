package com.example.usuario.projectasee;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentListaRutas extends Fragment {
    private List<Ruta> ruteList = new ArrayList<> ();
    private RecyclerView recyclerView;
    private RutesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listarutasfragment ,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        mAdapter = new RutesAdapter(ruteList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (getActivity ().getBaseContext ());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.addItemDecoration(new DividerItemDecoration (getActivity (), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareRuteData();
        return view;
    }

    private void prepareRuteData() {
        Ruta rute = new Ruta ("Nombre1",1,5,new Date ( 0,0,0,1,25 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre2",4,3,new Date ( 0,0,0,0,25 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre3",6,1,new Date ( 0,0,0,1,0 ) );
        ruteList.add ( rute );

        rute = new Ruta ("Nombre4",3,6,new Date ( 0,0,0,0,15 ) );
        ruteList.add ( rute );



        mAdapter.notifyDataSetChanged();
    }
}