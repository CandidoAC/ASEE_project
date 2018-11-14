package com.example.usuario.projectasee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentListaRutas extends Fragment {
    private List<Ruta> ruteList = new ArrayList<> ();
    private RecyclerView recyclerView;
    private RutesAdapter mAdapter;
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
        mAdapter = new RutesAdapter ( ruteList , new RutesAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(Ruta item) {//TODO Conseguir que cambie la pantalla a la de info rutas
                Intent i=new Intent ( getContext (),ActivityInfoRuta.class );
                startActivity ( i );
            }
        } );

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        return view;
    }

    public List <Ruta> getRuteList() {
        return ruteList;
    }

    public void setRuteList(List <Ruta> ruteList) {
        this.ruteList = ruteList;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onDestroy","**************");
    }
}