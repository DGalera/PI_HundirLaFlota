package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.david_galera.hundirlaflota.Models.Ranking;
import com.david_galera.hundirlaflota.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class GanarActivity extends AppCompatActivity {
    private TextView Numero;
    private TextView Intentos;
    private TextView Tiempo;
    private Button buttonAñadirRanking;
    private EditText editTextNombre;

    private int intentos;
    private String tiempo;
    private int tiempoSegs;

    private RequestQueue requestQueue;
    private String saveData;
    private static final String SERVER = "http://10.0.2.2:8080/rankings";
    //private BaseDatos datos;
    //private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganar);


        Intentos = (TextView) findViewById(R.id.textViewIntentos2);
        Tiempo = (TextView) findViewById(R.id.textViewTiempo2);
        TextView nombrejugador = (TextView) findViewById(R.id.textViewNombre);
        buttonAñadirRanking = (Button) findViewById(R.id.buttonAñadirRanking);

        nombrejugador.setText(MainActivity.jugador.getNombre());

        Bundle bundle = getIntent().getExtras();

        //cojo los datos del intent
        intentos = bundle.getInt("intentos");
        tiempo = bundle.getString("tiempo");

        Intentos.setText(String.valueOf(intentos));
        Tiempo.setText(tiempo);

        buttonAñadirRanking.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Ranking ranking = new Ranking(MainActivity.jugador.getNombre(), intentos, tiempo);

                    String data = ranking.toJSON();
                    addRank(data);
                }
            //}

        });
    }


    //Funcion VOLLEY metodo POST
    public void addRank(String data) {
        saveData = data;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //DEVOLVER EL RESPONSE EN JSONOBJECT
                    JSONObject objres = new JSONObject(response);
                    //Lo imprimimos
                    //Toast.makeText(getApplicationContext(), "Rank añadido", Toast.LENGTH_LONG).show();
                    finish();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();
                }
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
                    return saveData == null ? null : saveData.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }


}