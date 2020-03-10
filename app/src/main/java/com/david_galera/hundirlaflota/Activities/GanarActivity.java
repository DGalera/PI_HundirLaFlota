package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.david_galera.hundirlaflota.BaseDatos.BaseDatos;
import com.david_galera.hundirlaflota.Models.Rank;
import com.david_galera.hundirlaflota.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

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
    private static final String SERVER = "http://172.30.0.224:8080/rankings";
    //private BaseDatos datos;
    //private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganar);


        Intentos = (TextView) findViewById(R.id.textViewIntentos2);
        Tiempo = (TextView) findViewById(R.id.textViewTiempo2);
        buttonAñadirRanking = (Button) findViewById(R.id.buttonAñadirRanking);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);


        Bundle bundle = getIntent().getExtras();

        //cojo los datos del intent
        intentos = bundle.getInt("intentos");
        tiempo = bundle.getString("tiempo");
        tiempoSegs = bundle.getInt("tiempoSegs");

        Intentos.setText(String.valueOf(intentos));
        Tiempo.setText(tiempo);

        buttonAñadirRanking.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editTextNombre.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Introduce tu nombre para añadir al ranking", Toast.LENGTH_LONG).show();
                } else if (editTextNombre.getText().toString().length() > 5) {
                    Toast.makeText(getApplicationContext(), "El nombre no puede superar los 5 caracteres", Toast.LENGTH_LONG).show();
                } else {
                    String data = "{" +
                            "\"nombre\":" + "\"" + editTextNombre.getText().toString() + "\"," +
                            "\"intentos\":" + "\"" + Intentos.getText().toString() + "\"," +
                            "\"tiempo\":" + "\"" + Tiempo.getText().toString() + "\"" +
                            "}";
                    addRank(data);
                }
            }

        });
    }

    public void onResume() {
        super.onResume();
        //datos = new BaseDatos(this,"Datos",null,1);
        //db = datos.getWritableDatabase();
    }

    public void onPause() {
        super.onPause();
        //db.close();
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
                    Toast.makeText(getApplicationContext(), "Rank añadido", Toast.LENGTH_LONG).show();
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