package com.david_galera.hundirlaflota.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.david_galera.hundirlaflota.Models.Jugador;
import com.david_galera.hundirlaflota.Models.Ranking;
import com.david_galera.hundirlaflota.R;

import java.util.List;

public class JugadorAdapter extends BaseAdapter {
    //implementamos los métodos abstractos
    //Necesitamos un contexto, un layout que lo pasamos como referencia y una lista de string
    private Context context;
    private int layout;
    private List<Jugador> jugadores;

    // Nos creamos el constructor
    public JugadorAdapter(Context context, int layout, List<Jugador> jugadores) {
        this.context = context;
        this.layout = layout;
        this.jugadores = jugadores;
    }

    // Le dice al activity cuántas veces hay que iterar sobre un listview
    @Override
    public int getCount() {
        return this.jugadores.size();
    }

    // Para obtener un item, me devuelve el item de la posicion
    @Override
    public Object getItem(int position) {
        return this.jugadores.get(position);
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
        v = layoutInflater.inflate(R.layout.list_jugadores,null);

        // Nos traemos el valor de la posición


        String nombreJugador = jugadores.get(position).getNombre();

        TextView nombreJugadorTXT = (TextView) v.findViewById(R.id.idNombreJugador);
        nombreJugadorTXT.setText(nombreJugador);




        // Devolvemos la vista inflada y modificada  para terminar
        return v;
    }

}
