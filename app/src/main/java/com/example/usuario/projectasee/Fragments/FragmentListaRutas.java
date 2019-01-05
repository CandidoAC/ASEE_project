package com.example.usuario.projectasee.Fragments;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.projectasee.*;
import com.example.usuario.projectasee.Activity.ActivityInfoRuta;
import com.example.usuario.projectasee.Adapters.RutasAdapter;
import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.ViewModels.RutasViewModel;


import java.util.ArrayList;
import java.util.List;

public class FragmentListaRutas extends Fragment {
    private List<Ruta> ruteList;
    private RecyclerView recyclerView;
    private RutasAdapter mAdapter;
    private RutasViewModel rutasViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ruteList=new ArrayList<>(  );
        rutasViewModel =new RutasViewModel( getActivity ().getApplication () );
        rutasViewModel.getAllRoutes().observe ( this , new Observer <List <Ruta>> () {
            @Override
            public void onChanged(@Nullable List <Ruta> rutas) {
                ruteList=rutas;
                mAdapter.setRutaList ( ruteList );
            }
        } );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listarutasfragment ,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (getActivity ().getBaseContext ());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.addItemDecoration(new DividerItemDecoration (getActivity (), LinearLayoutManager.VERTICAL));
        mAdapter = new RutasAdapter( ruteList , new RutasAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(Ruta item) {
                Intent i=new Intent ( getContext (),ActivityInfoRuta.class );
                Bundle b=new Bundle (  );
                b.putInt ( "ruteId",item.getId () );
                i.putExtras ( b );
                startActivity ( i );
            }
        } );
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public List <Ruta> getRuteList() {
        return ruteList;
    }


    public void setRuteList(List <Ruta> ruteList)
    {
        this.ruteList = ruteList;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onDestroy","**************");
    }

}