package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.david_galera.hundirlaflota.Adapters.JugadorAdapter;
import com.david_galera.hundirlaflota.Models.Jugador;
import com.david_galera.hundirlaflota.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class JugadoresActivity extends AppCompatActivity {


    private Button buttonVolver;
    private List<Jugador> jugadores;
    private JugadorAdapter adapter;
    private ListView listView;
    private static final String SERVER = "http://10.0.2.2:8080/jugadores";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores);

        buttonVolver = (Button)findViewById(R.id.buttonVolver);

        listView = (ListView) findViewById(R.id.listView);
        jugadores = new ArrayList<Jugador>();
        adapter = new JugadorAdapter(this, R.layout.list_jugadores, jugadores);
        listView.setAdapter(adapter);
        getJugadores();

        buttonVolver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }

        });
    }

    private void getJugadores() {
        jugadores.clear();
        //En el response le el valor del json es decir
        //[{“_id”:”43432424”,”nombre”:”Javier”….}]
        StringRequest request = new StringRequest(SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Jugador[] jugador = gson.fromJson(response, Jugador[].class);
                for(int i=0; i<jugador.length; i++) {
                    jugadores.add(jugador[i]);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JugadoresActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
