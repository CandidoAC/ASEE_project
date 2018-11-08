package com.example.usuario.projectasee;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RutesAdapter  extends RecyclerView.Adapter<RutesAdapter.MyViewHolder>{
    private List<Ruta> rutaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre,distancia;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.Nombre);
            distancia = (TextView) view.findViewById(R.id.Distancia);
        }

        public void bind(Ruta Ruta) {
            String f=String.valueOf ( Ruta.getDistancia ());
            nombre.setText ( Ruta.getNombre () );
            distancia.setText ("\t" +f+" km.");
        }
    }


    public RutesAdapter(List<Ruta> rutaList) {

        this.rutaList = rutaList;
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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(rutaList.get(position));
    }

    @Override
    public int getItemCount() {
        return rutaList.size();
    }
}
