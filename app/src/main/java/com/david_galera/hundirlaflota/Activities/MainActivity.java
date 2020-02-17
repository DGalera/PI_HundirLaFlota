package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.david_galera.hundirlaflota.R;

public class MainActivity extends AppCompatActivity {

    private Button btnJugar, btnAcercaDe, btnRanking, btnAyuda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
