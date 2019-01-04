package com.example.usuario.projectasee.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;

import java.util.ArrayList;
import java.util.List;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.MyViewHolder>{
    private List<Ruta> rutaList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ruta ruta);     //Type of the element to be returned
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre,distancia;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.Nombre);
            distancia = (TextView) view.findViewById(R.id.Distancia);
        }

        public void bind(final Ruta Ruta,final OnItemClickListener listener) {
            String f=String.format ( "%.2f",Ruta.getDistancia ());
            nombre.setText ( Ruta.getNombre () );
            distancia.setText ("\t" +f+" km.");

            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    listener.onItemClick ( Ruta );
                }
            });
        }
    }


    public RutasAdapter(List<Ruta> rutaList, OnItemClickListener listener) {

        this.rutaList = rutaList;
        this.listener = listener;
    }
    public List<Ruta> rutaList () {
        return this.rutaList;
    }

    public void setRutaList(List <Ruta> rutaList) {
        this.rutaList = rutaList;
        notifyDataSetChanged ();
    }

    @Override
    public RutasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_listrutes, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(rutaList.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return rutaList.size();
    }

}
