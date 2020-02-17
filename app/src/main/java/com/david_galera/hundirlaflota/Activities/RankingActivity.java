package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.david_galera.hundirlaflota.Adapters.MyAdapterRanking;
import com.david_galera.hundirlaflota.BaseDatos.BaseDatos;
import com.david_galera.hundirlaflota.Models.Rank;
import com.david_galera.hundirlaflota.R;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private Button buttonVolver;
    private List<Rank> ranking;
    private MyAdapterRanking myAdapterRanking;
    private ListView listView;

    private BaseDatos datos;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        buttonVolver = (Button)findViewById(R.id.buttonVolver);
        listView = (ListView) findViewById(R.id.listView);
        ranking = new ArrayList<Rank>();
        myAdapterRanking = new MyAdapterRanking(this, R.layout.list_ranking, ranking);
        listView.setAdapter(myAdapterRanking);

        buttonVolver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        this.myAdapterRanking.notifyDataSetChanged();
    }
    public void onResume()
    {
        super.onResume();
        datos = new BaseDatos(this,"Datos",null,1);
        db = datos.getReadableDatabase();
        cargarRanking();
    }
    public void onPause()
    {
        super.onPause();
        db.close();
    }

    private void cargarRanking(){

        try{
            //cojo todos los ranking de la base de datos ordenador por intentos y depues por el tiempo en segundos
            Cursor cursor1 = db.rawQuery("select * from Ranking order by intentos, tiempoSegs", null);
            cursor1.moveToFirst();
            do{
                Rank rank = new Rank(cursor1.getInt(0), cursor1.getInt(1), cursor1.getString(2), cursor1.getString(3), cursor1.getInt(4));
                if(!ranking.contains(rank)){
                    ranking.add(rank);
                }

            }while(cursor1.moveToNext());
        } catch(Exception e){
            Toast.makeText(this, "No hay rankings en la base de datos", Toast.LENGTH_LONG).show();
        }
    }

}
