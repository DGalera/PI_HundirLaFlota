package com.david_galera.hundirlaflota.Models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.david_galera.hundirlaflota.Activities.GanarActivity;
import com.david_galera.hundirlaflota.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

//Esta clase es la que gestiona el tablero y toda la jugabilidad
public class Tablero extends View{

    private List<Barco> barcos;
    private Casilla [][] casillas;
    private int barcos4, barcos3, barcos2;
    private int tamaño = 8;
    private int ancho;
    private int anchoCasilla;
    private boolean debugMode = true;
    private int barcosHundidos;
    private int barcosRestantes;
    private int turno;
    public TextView turnoTV, barcosde2TV, barcosde3TV, barcosde4TV;
    public Cronometro cronometro;

    private Random random;
    private Context context;

    private MediaPlayer aguaSound, barcoSound;

    //Estosmetodos son para poder colocar el tablero desde el xml
    public Tablero(Context context) {
        super(context);
        this.context = context;
        init(null);
    }


    public Tablero(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public Tablero(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public Tablero(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    public void init(@Nullable AttributeSet set){
        random = new Random();
        //inicio las casillas y el arraylist de barcos
        casillas = new Casilla[tamaño][tamaño];
        barcos = new ArrayList<Barco>();
        generarTablero();
        aguaSound = MediaPlayer.create(context, R.raw.agua);
        barcoSound = MediaPlayer.create(context, R.raw.barco);

    }

    //Este metodo sirve para llamar al metodo rellenarTablero hasta que devuelva true
    //es decir que se haya rellenado correctamente
    public void generarTablero(){
        while(!rellenarTablero()){
            System.out.println("No se pudo rellenar el tablero, se intentará de nuevo");

        }
        invalidate();
    }

    public boolean rellenarTablero(){ //la funcion sera un boolean para comprobar si se a llenado correctamente o no
            crearBarcos();
            iniciarCasillas();

            for (Barco barco: barcos) {
                int intentosFail = 0; // intentos fallidos por colocar el barco
                try {
                    do {
                        int x;
                        int y;
                        //genero dos numeros de manera aleatoria, su rango dependerá de la orientacion del barco
                        //serán las coordenadas de la casilla inicial del barco
                        if (barco.getOrientacion() == Barco.orientacion.horizontal) {
                            x = random.nextInt(tamaño - barco.getLongitud());
                            y = random.nextInt(tamaño);
                        } else {
                            x = random.nextInt(tamaño);
                            y = random.nextInt(tamaño - barco.getLongitud());
                        }
                        //si se puede colocar en esas casillas, lo coloco
                        if (sePuedeColocarBarco(x, y, barco)) {
                            colocarBarco(x, y, barco);
                            barco.setColocado(true);
                        } else {
                            //si no se puede pruebo a hacer lo mismo pero cambiando la orientación del barco
                            barco.cambiarOrientacion();
                            if (sePuedeColocarBarco(x, y, barco)) {
                                colocarBarco(x, y, barco);
                                barco.setColocado(true);
                                //si aun así no se puede sumo 1 a los intentos fallidos
                            } else {
                                intentosFail += 1;
                            }
                        }
                        if (intentosFail >= 100) { //si despues de 100 intentos fallidos no se ha podido colocar el barco
                            return false; //devuelvo que no se ha podido rellenar el tablero para que no entre en bucle infinito
                        }
                    } while (!barco.isColocado());
                } catch (Exception e){
                    e.printStackTrace();
                    //si por alguna razón peta devuelvo que no se ha podido rellenar el tablero
                    return false;
                }

            }

        return true;
    }



    private boolean sePuedeColocarBarco(int x, int y, Barco barco){
        //se le pasa las coordenadas de la casilla inical y el barco
        //necesito comprobar que desde la casilla x1 hasta la x2 no haya ningun barco, lo mismo con la y1 a la y2
        int x1, x2, y1, y2;
        //x1 e y1 seran x-1 e y-1 porque debe haber una casilla de separación entre barcos
        x1 = x - 1;
        y1 = y - 1;
        //dependiendo de la orientacion del barco le sumaré su longitud a x2 o a y2
        //pero a los dos le sumo +1 por la casilla de separación
        if(barco.getOrientacion() == Barco.orientacion.horizontal) {
            x2 = x +barco.getLongitud() + 1;
            y2 = y + 1;
        } else{
            x2 = x  + 1;
            y2 = y + barco.getLongitud() + 1;
        }
        //recorro los rangos asignados para comprobar que no haya barcos en esas casillas
            for (int i = x1; i <= x2; i++){
                for(int j = y1; j <= y2; j ++){
                    //compruebo que existe la casilla para evitar que haya fallos
                    //por ejemplo y la casilla inical del barco es 0, nunca va a haber una casilla -1 y petaria
                    if(existeCasilla(i, j)) {
                        if (casillas[i][j].isTieneBarco()) {
                            return false;
                        }
                    }
                }

            }

        return true;
    }

    private boolean existeCasilla(int x, int y){
        try{
            //llamo a una funcioon cualquiera de la casilla
            casillas[x][y].isTieneBarco();
            //si no peta significa que existe
            return true;
        } catch (Exception e){
            //si peta no existe
            return false;
        }
    }
    private void colocarBarco(int x, int y, Barco barco) {
        //si la orientacion es horizontal
        if(barco.getOrientacion() == Barco.orientacion.horizontal){
            //recorro las coordenadas necesarias y a las casillas en esa posicion les asigno que tienen un barco y el  id de dichobarco
            for(int i = x; i < x + barco.getLongitud(); i++){
                casillas[i][y].setTieneBarco(true);
                casillas[i][y].setIdBarco(barco.getIdbarco());
            }
        } else{
            for(int i = y; i < y + barco.getLongitud(); i++){
                casillas[x][i].setTieneBarco(true);
                casillas[x][i].setIdBarco(barco.getIdbarco());
            }
        }
    }

    public Barco.orientacion darOrientacionBarco(){
        if((random.nextInt(2) == 0)){
            return Barco.orientacion.horizontal;
        }
        return Barco.orientacion.vertical;
    }

    public void crearBarcos(){
        barcos.clear();
        //crear barco de 4 casillas de longitud
        barcos.add(new Barco(0, 4, darOrientacionBarco()));
        barcos4 = 1;
        //crear barcos de 3 casillas de longitud
        barcos.add(new Barco(1, 3, darOrientacionBarco()));
        barcos.add(new Barco(2, 3, darOrientacionBarco()));
        barcos3 = 2;
        //crear barcos de 2 casillas de longitud
        barcos.add(new Barco(3, 2, darOrientacionBarco()));
        barcos.add(new Barco(4, 2, darOrientacionBarco()));
        barcos.add(new Barco(5, 2, darOrientacionBarco()));
        barcos2 = 3;

        barcosRestantes = barcos.size();
        barcosHundidos = 0;
        turno = 1;
        actualizarTextViews();

    }

    //actualizo los textViews siempre y cuando no sean null
    public void actualizarTextViews() {
        if(turnoTV != null && barcosde3TV != null && barcosde2TV != null) {
            turnoTV.setText(String.valueOf(turno));
            barcosde2TV.setText(String.valueOf(barcos2));
            barcosde3TV.setText(String.valueOf(barcos3));
            barcosde4TV.setText(String.valueOf(barcos4));
        }
    }

    public void iniciarCasillas(){
        //voy por cada casilla inicializandolas
        for(int i = 0; i < tamaño; i++){
            for(int j = 0; j < tamaño; j++){
                casillas[i][j] = new Casilla();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawRGB(0, 0, 0);
        if(getWidth() < getHeight()){
            ancho = getWidth();
        } else {
            ancho = getHeight();
        }

        anchoCasilla = ancho / tamaño;
        Paint paint = new Paint();
        Paint linea = new Paint();
        linea.setARGB(255, 0, 0, 0);

        for (int i = 0; i < tamaño; i += 1){
                for(int j = 0; j < tamaño; j+= 1){
                int x = i * anchoCasilla;
                int y = j *anchoCasilla;
                casillas[i][j].fijarCoordenadas(x, y, anchoCasilla);

                if(casillas[i][j].isPulsado()){
                    //si se esta presionando esa casilla se le pondra un color azul oscuro para
                    //darle un efecto más visual
                    paint.setARGB(255, 60, 61, 63  );//azul oscuro
                } else {
                    //si esta activado el debug mode
                    if (debugMode){
                        //la casilla se pintara de gris si hay un barco y si no de azul claro
                        if (casillas[i][j].isTieneBarco()) {
                            paint.setARGB(255, 209, 196, 233);//gris
                            if(barcos.get(casillas[i][j].getIdBarco()).vidas == 0){
                                paint.setARGB(255, 254, 48, 42 );
                            }
                        } else if(casillas[i][j].isDestapado()) {
                            paint.setARGB(255, 45, 75, 168 );
                        }else{
                            paint.setARGB(255, 77, 79, 83);//azul claro
                        }
                    } else {
                        //si no esta activado el debug mode todas las casillas se pintaran
                        //de azul claro, a no ser que la casilla este destapada y tenga un barco,
                        //en ese caso se pintara de gris
                        if(casillas[i][j].isDestapado() && casillas[i][j].isTieneBarco()){
                            paint.setARGB(255, 209, 196, 233 );//gris
                            if(barcos.get(casillas[i][j].getIdBarco()).vidas == 0){
                                paint.setARGB(255, 254, 48, 42 );
                            }
                        }
                        else if (casillas[i][j].isDestapado()){
                            paint.setARGB(255, 45, 75, 168 );
                        }
                        else{
                            paint.setARGB(255, 77, 79, 83  );//azul claro
                        }


                    }


                }

                //dibujo un rectangulo desde las coordenadas iniciales hasta las coordenadas iniciales + el ancho de la casilla
                canvas.drawRect(x, y, x + anchoCasilla - 1, y + anchoCasilla, paint);
                //dibujo lineas para que quede bien la separación
                canvas.drawLine(x, y, x + anchoCasilla, y, linea);
                canvas.drawLine(x + anchoCasilla , y, x + anchoCasilla , y + anchoCasilla, linea);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        //obtengo las coordenadas donde se ha pulsado
        int x = (int)event.getX();
        int y = (int)event.getY();

        //creo la casilla
        Casilla casillaPulsada = new Casilla();

        //recorro todas las casillas para ver cual ha sido pulsada
        for (int i = 0; i < tamaño; i ++) {
            for (int j = 0; j < tamaño; j++) {
                //esta función sirve para saber si las coordenadas pasadas por paráametro
                //estan dentro de la casilla
                if(casillas[i][j].dentro(x, y)){
                    casillaPulsada = casillas[i][j];
                } else{
                    //si no estan dentro de la casilla le digo que no este
                    //pulsada para evitar fallos
                    casillas[i][j].setPulsado(false);
                }
            }
        }

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                //en caso de que el evento sea el de presionar y que la casilla no haya sido destapada aun
                //le digo a esa casilla que ha sido pulsada
                if (casillaPulsada.isDestapado()) {
                    Toast.makeText(context, "Ya has pulsado esa casilla", Toast.LENGTH_SHORT).show();
                } else {
                    casillaPulsada.setPulsado(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                Barco barco =  barcos.get(casillaPulsada.getIdBarco());
                //si ha dejado de pulsar en una casilla que estaba siendo pulsada
                if(casillaPulsada.isPulsado()){
                    //le digo que ya no esta siendo pulsada
                    casillaPulsada.setPulsado(false);
                    //y la destapo
                    casillaPulsada.setDestapado(true);
                    //si tiene un barco
                    if(casillaPulsada.isTieneBarco()){
                        //y le quedan mas de 1 vida digo tocado
                        if(barco.vidas <= 1){
                            //si solo le queda una vida digo hundido
                            //actualizo las variables
                            barcosHundidos +=1;
                            barcosRestantes -=1;
                            if(barco.getLongitud() == 4){
                                barcos4 -= 1;
                            } else if(barco.getLongitud() == 3){
                                barcos3 -= 1;
                            } else{
                                barcos2 -= 1;
                            }


                        }
                        //le resto la vida al barco
                        barco.vidas -= 1;
                        barcoSound.start();


                        aguaSound.start();
                    } else{
                        turno +=1;
                    }

                }
                if(cronometro.getPausado()){
                    cronometro.pause();
                }
                break;
        }
        actualizarTextViews();
        if(barcosRestantes == 0){
            ganar();
        }


        if(turno == 40){
            perder();
        }



        // tell the View to redraw the Canvas
        invalidate();

        // tell the View that we handled the event
        return true;

    }

    private void ganar() {
        Intent intent = new Intent(context, GanarActivity.class);
        intent.putExtra("intentos", turno);
        intent.putExtra("tiempo", cronometro.getSalida());
        intent.putExtra("tiempoSegs", cronometro.getSegundosTotales());
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    private void perder() {
        Toast.makeText(context, "Has perdido XD", Toast.LENGTH_SHORT).show();
        ((Activity)context).finish();

    }

    public void iniciarCrono(){
        new Thread(cronometro).start();
        cronometro.pause();
    }

}
