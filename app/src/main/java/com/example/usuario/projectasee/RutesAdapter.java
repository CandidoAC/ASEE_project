package com.example.usuario.projectasee;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class RutesAdapter  extends RecyclerView.Adapter<RutesAdapter.MyViewHolder>{
    private List<Ruta> rutaList;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre,distancia;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.Nombre);
            distancia = (TextView) view.findViewById(R.id.Distancia);
        }

        public void bind(final Ruta Ruta,final OnItemClickListener listener) {
            String f=String.valueOf ( Ruta.getDistancia ());
            nombre.setText ( Ruta.getNombre () );
            distancia.setText ("\t" +f+" km.");


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //listener.onItemClick(Ruta);
                }
            });
        }
    }


    public RutesAdapter(List<Ruta> rutaList,OnItemClickListener listener) {

        this.rutaList = rutaList;
        this.listener = listener;
    }


    public List<Ruta> rutaList () {
        return this.rutaList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_listrutes, parent, false);

        return new MyViewHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(Ruta item);     //Type of the element to be returned
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
