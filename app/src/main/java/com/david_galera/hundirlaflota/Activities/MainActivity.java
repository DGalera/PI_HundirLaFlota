package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.david_galera.hundirlaflota.Models.Ranking;
import com.david_galera.hundirlaflota.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnJugar, btnAcercaDe, btnRanking, btnAyuda;
    private List<Ranking> ranking;
    private int tamañoLista = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ranking = new ArrayList<Ranking>();


        btnJugar = (Button)findViewById(R.id.btnJugar);
        btnAcercaDe = (Button)findViewById(R.id.btnAcercaDe);
        btnRanking = (Button)findViewById(R.id.btnRanking);
        btnAyuda = (Button)findViewById(R.id.btnAyuda);

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

    }



}
