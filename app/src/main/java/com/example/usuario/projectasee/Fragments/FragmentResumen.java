package com.example.usuario.projectasee.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.projectasee.Modelo.Ruta;
import com.example.usuario.projectasee.R;
import com.example.usuario.projectasee.RutasViewModel;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentResumen extends Fragment {

    private List<Ruta> routeList = new ArrayList<>();
    private LineGraphSeries<DataPoint> line1Series, line2Series;
    private RutasViewModel rutasViewModel;
    GraphView graphView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resumenfragment, container, false);
        float distancia = 0;
        double calorias = 0;


        Time tiempo = new Time(0, 0, 0);
        rutasViewModel = ViewModelProviders.of(this).get(RutasViewModel.class);
        rutasViewModel.getAllRoutes().observe(this, new Observer<List<Ruta>>() {
            @Override
            public void onChanged(@Nullable List<Ruta> rutas) {
                routeList = rutas;
                float distancia = 0;
                double calorias = 0;
                Time tiempo = new Time(0, 0, 0);
                for (int i = 0; i < routeList.size(); i++) {
                    distancia = distancia + routeList.get(i).getDistancia();
                    calorias = calorias + routeList.get(i).getCalorias();
                    tiempo.setHours(tiempo.getHours() + routeList.get(i).getTiempo().getHours());
                    tiempo.setMinutes(tiempo.getMinutes() + routeList.get(i).getTiempo().getMinutes());
                    tiempo.setSeconds(tiempo.getSeconds() + routeList.get(i).getTiempo().getSeconds());

                }

                TextView t1 = getView().findViewById(R.id.TextDistancia);
                t1.setText(String.format("%.2f", distancia) + " kms");

                TextView t2 = getView().findViewById(R.id.TextCalorias);
                t2.setText(String.format("%.2f", calorias));

                TextView t3 = getView().findViewById(R.id.TextTime);
                t3.setText(tiempo.toString());

                graphView = getView().findViewById(R.id.Graph);

                DataPoint [] vcalorias = getDataPointCalorias();
                double maxCalorias = getMayor(vcalorias);
                DataPoint[] vdistancia = getDataPointDistancia();
                double maxDistancia = getMayor(vdistancia);

                line1Series = new LineGraphSeries<>(vcalorias);
                line1Series.setTitle("Calorías");
                line1Series.setColor(Color.GREEN);
                line1Series.setDrawDataPoints(true);
                line1Series.setDataPointsRadius(15);
                line1Series.setThickness(8);

                graphView.addSeries(line1Series);


                line2Series = new LineGraphSeries<>(vdistancia);

                line2Series.setTitle("Distancia");
                line2Series.setColor(Color.BLUE);
                line2Series.setDrawDataPoints(true);
                line2Series.setDataPointsRadius(15);
                line2Series.setThickness(8);

                graphView.addSeries(line2Series);

                graphView.getLegendRenderer().setVisible(true);

                graphView.getGridLabelRenderer().setNumHorizontalLabels(4);

                graphView.getViewport().setYAxisBoundsManual(true);

                if (maxCalorias > maxDistancia) {
                    graphView.getViewport().setMaxYAxisSize(maxCalorias+100);
                } else{
                    graphView.getViewport().setMaxYAxisSize(maxDistancia+100);
                }

                line1Series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), routeList.get((int) dataPoint.getX()).getNombre(), Toast.LENGTH_SHORT).show();
                    }
                });

                line2Series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), routeList.get((int) dataPoint.getX()).getNombre(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        for (int i = 0; i < routeList.size(); i++) {
            distancia = distancia + routeList.get(i).getDistancia();
            calorias = calorias + routeList.get(i).getCalorias();
            tiempo.setHours(tiempo.getHours() + routeList.get(i).getTiempo().getHours());
            tiempo.setMinutes(tiempo.getMinutes() + routeList.get(i).getTiempo().getMinutes());
            tiempo.setSeconds(tiempo.getSeconds() + routeList.get(i).getTiempo().getSeconds());

        }

        TextView t1 = view.findViewById(R.id.TextDistancia);
        t1.setText(String.valueOf(distancia));

        TextView t2 = view.findViewById(R.id.TextCalorias);
        t2.setText(String.valueOf(calorias));

        TextView t3 = view.findViewById(R.id.TextTime);
        t3.setText(tiempo.toString());

        return view;
    }

    private DataPoint[] getDataPointCalorias() {
        DataPoint[] dp = new DataPoint[routeList.size()];
        for (int i = 0; i < routeList.size(); i++) {
            dp[i] = new DataPoint(i, routeList.get(i).getCalorias());
        }
        return dp;
    }

    private DataPoint[] getDataPointDistancia() {
        DataPoint[] dp = new DataPoint[routeList.size()];
        for (int i = 0; i < routeList.size(); i++) {
            dp[i] = new DataPoint(i, routeList.get(i).getDistancia()*1000);
        }
        return dp;
    }

    private double getMayor(DataPoint[] dp){
        double max = 0;
        for(int i = 0; i < dp.length;i++){
            if(max<dp[i].getY()){
                max=dp[i].getY();
            }
        }
        return max;
    }

    public List<Ruta> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Ruta> routeList) {
        this.routeList = routeList;
    }
}