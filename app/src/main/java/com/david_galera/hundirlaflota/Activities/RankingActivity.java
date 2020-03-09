package com.david_galera.hundirlaflota.Activities;

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
import com.david_galera.hundirlaflota.Adapters.MyAdapterRanking;
import com.david_galera.hundirlaflota.Models.Ranking;
import com.david_galera.hundirlaflota.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {

    private Button buttonVolver;
    private List<Ranking> ranking;
    private MyAdapterRanking myAdapterRanking;
    private ListView listView;



    private static final String SERVER = "http://10.0.2.2:8080/jugadores";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        buttonVolver = (Button)findViewById(R.id.buttonVolver);
        listView = (ListView) findViewById(R.id.listView);
        ranking = new ArrayList<Ranking>();
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
        ObtenerValores();
    }
    public void onPause()
    {
        super.onPause();
    }



    private void ObtenerValores() {
        StringRequest request = new StringRequest(SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Ranking[] rankings = gson.fromJson(response, Ranking[].class);
                for(int i=0; i<rankings.length; i++) {
                    ranking.add(rankings[i]);
                }
                orderRankings();
                myAdapterRanking.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RankingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void orderRankings() {

        Collections.sort(ranking, new Comparator() {

            public int compare(Object o1, Object o2) {

                Integer x1 = ((Ranking) o1).getIntentos();
                Integer x2 = ((Ranking) o2).getIntentos();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer x3 = ((Ranking) o1).getTiempoSegs();
                Integer x4 = ((Ranking) o2).getTiempoSegs();
                return x3.compareTo(x4);
            }});
    }

    }
