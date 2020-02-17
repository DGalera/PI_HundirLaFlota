package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.david_galera.hundirlaflota.BaseDatos.BaseDatos;
import com.david_galera.hundirlaflota.Models.Rank;
import com.david_galera.hundirlaflota.R;

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


    private BaseDatos datos;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganar);

        Intentos = (TextView)findViewById(R.id.textViewIntentos2);
        Tiempo = (TextView)findViewById(R.id.textViewTiempo2);
        buttonAñadirRanking = (Button)findViewById(R.id.buttonAñadirRanking);
        editTextNombre = (EditText)findViewById(R.id.editTextNombre);


        Bundle bundle = getIntent().getExtras();

        //cojo los datos del intent
        intentos = bundle.getInt("intentos");
        tiempo = bundle.getString("tiempo");
        tiempoSegs = bundle.getInt("tiempoSegs");

        Intentos.setText(String.valueOf(intentos));
        Tiempo.setText(tiempo);

        buttonAñadirRanking.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                addRank();
            }

        });
    }
    public void onResume()
    {
        super.onResume();
        datos = new BaseDatos(this,"Datos",null,1);
        db = datos.getWritableDatabase();
    }

    public void onPause()
    {
        super.onPause();
        db.close();
    }

    public void addRank(){
        if(editTextNombre.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Introduce tu nombre para añadir al ranking", Toast.LENGTH_LONG).show();
        }
        else if(editTextNombre.getText().toString().length() > 5){
            Toast.makeText(this, "El nombre no puede superar los 5 caracteres", Toast.LENGTH_LONG).show();
        }
        else
        {
            //creo un nuevo rank y le paso el tiempo en string para mostrarlo correctamente y el tiempo en segundos para poder ordenar el ranking
            Rank rank = new Rank(intentos, editTextNombre.getText().toString(), tiempo, tiempoSegs);
            if(db != null)
            {
                ContentValues registro = new ContentValues();
                //meto los valores en el registro
                registro.put("intentos", rank.getIntentos());
                registro.put("nombreJugador", rank.getNombreJugador());
                registro.put("tiempoStr", rank.getTiempoStr());
                registro.put("tiempoSegs", rank.getTiempoSegs());

                //los añado a la base de datos
                try{
                    if(db.insertOrThrow("Ranking", null, registro) == -1)
                    {
                        Toast.makeText(this, "Error al añadir el rank", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Rank añadido", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
            else
            {
                Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_LONG).show();
            }
        }
    }

}