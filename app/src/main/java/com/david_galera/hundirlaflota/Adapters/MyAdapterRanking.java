package com.david_galera.hundirlaflota.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.david_galera.hundirlaflota.Models.Rank;
import com.david_galera.hundirlaflota.R;

import java.util.List;

// El adaptador es una clase normal java, la creamos normal y extendreemos de BaseAdapter que es otra clase
public class MyAdapterRanking extends BaseAdapter {

    //implementamos los métodos abstractos
    //Necesitamos un contexto, un layout que lo pasamos como referencia y una lista de string
    private Context context;
    private int layout;
    private List<Rank> ranks;

    // Nos creamos el constructor
    public MyAdapterRanking(Context context, int layout, List<Rank> ranks) {
        this.context = context;
        this.layout = layout;
        this.ranks = ranks;
    }

    // Le dice al activity cuántas veces hay que iterar sobre un listview
    @Override
    public int getCount() {
        return this.ranks.size();
    }

    // Para obtener un item, me devuelve el item de la posicion
    @Override
    public Object getItem(int position) {
        return this.ranks.get(position);
    }

    //Para obtener el id de un item
    @Override
    public long getItemId(int id) {
        return id;
    }

    // Donde se dibuja lo que queremos hacer, el método clave
    // es donde está el tema
    // convertView es la lista de vistas que se va a dibujar
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista que vamos a inflar
        View v = convertView;

        // Usamos la clase LayoutInflater que se obtiene de un método de la misma clase pasándole u contexto
        // Inflamos la vista que nos hallegado con el layout personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        // Le indicamos el Layout que hemos creado antes
        v = layoutInflater.inflate(R.layout.list_ranking,null);

        // Nos traemos el valor de la posición
        int numIntentos = ranks.get(position).getIntentos();

        TextView numIntentosTXT = (TextView) v.findViewById(R.id.numIntentos);
        numIntentosTXT.setText(String.valueOf(numIntentos));

        String nombreJugador = ranks.get(position).getNombreJugador();

        TextView nombreJugadorTXT = (TextView) v.findViewById(R.id.idNombreJugador);
        nombreJugadorTXT.setText(nombreJugador);

        String tiempo = ranks.get(position).getTiempoStr();

        TextView tiempoTXT = (TextView) v.findViewById(R.id.idTiempo);
        tiempoTXT.setText(tiempo);




        // Devolvemos la vista inflada y modificada  para terminar
        return v;
    }

}
