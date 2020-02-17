package com.david_galera.hundirlaflota.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.david_galera.hundirlaflota.Models.Cronometro;
import com.david_galera.hundirlaflota.Models.Tablero;
import com.david_galera.hundirlaflota.R;


public class TableroFragment extends Fragment {

    private Tablero tablero;

    public TableroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tablero, container, false);

        tablero = (Tablero)view.findViewById(R.id.tablero);


        return view;
    }


    public void renderTextViews(TextView turno, TextView cronometro, TextView barcosde2, TextView barcosde3, TextView barcosde4){
        tablero.turnoTV = turno;
        tablero.barcosde2TV = barcosde2;
        tablero.barcosde3TV = barcosde3;
        tablero.barcosde4TV = barcosde4;
        tablero.cronometro = new Cronometro("Cronometro", cronometro);
        tablero.actualizarTextViews();
        tablero.iniciarCrono();
    }




}
