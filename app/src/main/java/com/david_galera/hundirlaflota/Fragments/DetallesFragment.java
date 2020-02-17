package com.david_galera.hundirlaflota.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.david_galera.hundirlaflota.R;


public class DetallesFragment extends Fragment {

    //este fragment envia los textviews al otro fragment para que puedan ser gestionados desde el tablero

    private TextView tvTurnosNum, cronometro, tvBarcosde2, tvBarcosde3, tvBarcosde4;

    private DataListener callback;

    public DetallesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalles, container, false);

        tvTurnosNum = (TextView) view.findViewById(R.id.tvTurnosNum);
        cronometro = (TextView) view.findViewById(R.id.cronometro);
        tvBarcosde2 = (TextView) view.findViewById(R.id.tvBarcosDe2num);
        tvBarcosde3 = (TextView) view.findViewById(R.id.tvBarcosDe3num);
        tvBarcosde4 = (TextView) view.findViewById(R.id.tvBarcosDe4num);

        callback.sendData(tvTurnosNum, cronometro, tvBarcosde2, tvBarcosde3, tvBarcosde4);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // Convertimos el contexto en DataListener y lo guardamos en el callback
            callback = (DataListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " DataListener mal implementado");
        }

    }

    public interface DataListener{
        void sendData(TextView turno, TextView cronometro, TextView barcosde2, TextView barcosde3, TextView barcosde4);
    }


}
