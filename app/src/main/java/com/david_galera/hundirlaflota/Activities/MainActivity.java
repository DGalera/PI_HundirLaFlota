package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.david_galera.hundirlaflota.Models.Jugador;
import com.david_galera.hundirlaflota.Models.PlayerResponse;
import com.david_galera.hundirlaflota.Models.Ranking;
import com.david_galera.hundirlaflota.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnJugar, btnAcercaDe, btnRanking, btnAyuda, btnJugadores;
    private TextView txtConectados;
    private EditText txtNombre;
    private List<Ranking> ranking;
    private List<Jugador> jugadores;
    private RequestQueue requestQueue;
    public static Jugador jugador;
    private static final String SERVERJUGADORES = "http://10.0.2.2:8080/jugadores";
    private static final String SERVERRANKINGS = "http://10.0.2.2:8080/rankings";
    private boolean cargados =false;
    private int tamañoAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugadores = new ArrayList<Jugador>();
        ranking = new ArrayList<Ranking>();
        getRankings();

        final Handler timer = new Handler();
        final Runnable runnable = new Runnable(){
            @Override
            public void run(){
                getNumJugadores();
                if(cargados){
                    tamañoAux = ranking.size();
                    getRankings();

                }

                timer.postDelayed(this,5000);

            }
        };

        timer.postDelayed(runnable,0);

        txtConectados = (TextView)findViewById(R.id.txtConectados);
        btnJugar = (Button)findViewById(R.id.btnJugar);
        btnAcercaDe = (Button)findViewById(R.id.btnAcercaDe);
        btnRanking = (Button)findViewById(R.id.btnRanking);
        btnAyuda = (Button)findViewById(R.id.btnAyuda);
        btnJugadores = (Button)findViewById(R.id.btnJugadores);

        btnJugar.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainActivity.this, JugarActivity.class);
                startActivity(intent);
            }

        });
        btnAcercaDe.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainActivity.this, AcercaDeActivity.class);
                startActivity(intent);
            }

        });
        btnRanking.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
            }

        });
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(intent);
            }

        });

        btnJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JugadoresActivity.class);
                startActivity(intent);
            }
        });
        Acceder();


    }


    public void Acceder() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.name_dialog, null);

        //botones para el alert
        txtNombre = (EditText) mView.findViewById(R.id.txt_name);
        Button btnComprobar = (Button) mView.findViewById(R.id.btn_guardar);

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);


        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // txtNombre.setText(txtNombre.getText().toJSON());
                //txtNombreBD = txtNombre;
                if (txtNombre.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduce un nombre", Toast.LENGTH_SHORT).show();
                }else{
                    Jugador newJugador = new Jugador(txtNombre.getText().toString());
                    String data = newJugador.toJSON();
                    addJugador(data);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.setCancelable(false);

        alertDialog.show();
    }

    public void addJugador(final String data) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVERJUGADORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PlayerResponse playerResponse = gson.fromJson(response, PlayerResponse.class);
                jugador = playerResponse.getResult();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }

    public void deleteJugador() {
        String ServerRemove = "http://10.0.2.2:8080/jugadores/"+jugador.getId();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, ServerRemove, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Jugador eliminado", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        requestQueue.add(stringRequest);
    }

    private void getRankings() {
        ranking.clear();

        StringRequest request = new StringRequest(SERVERRANKINGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Ranking[] rankings = gson.fromJson(response, Ranking[].class);
                for(int i=0; i<rankings.length; i++) {
                    ranking.add(rankings[i]);
                }

                if(cargados) {
                    if(tamañoAux < ranking.size()){
                        Toast.makeText(getApplicationContext(), "Se ha añadido un nuevo ranking", Toast.LENGTH_SHORT).show();
                    }
                }

                cargados = true;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void getNumJugadores() {
        jugadores.clear();
        //En el response le el valor del json es decir
        //[{“_id”:”43432424”,”nombre”:”Javier”….}]
        StringRequest request = new StringRequest(SERVERJUGADORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Jugador[] jugadors = gson.fromJson(response, Jugador[].class);
                for(int i=0; i<jugadors.length; i++) {
                    jugadores.add(jugadors[i]);
                }
                String str = getString (R.string.jugadores_online) + ": "+jugadores.size();
                txtConectados.setText(str);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteJugador();
    }


}
