package com.david_galera.hundirlaflota.Models;

import java.util.Objects;

public class Barco {
    private int idbarco;
    private int longitud;
    public int vidas;
    private orientacion orientacion;
    private boolean colocado = false;

    public Barco(int idbarco, int longitud, orientacion orientacion) {
        this.idbarco = idbarco;
        this.longitud = longitud;
        this.orientacion = orientacion;
        this.vidas = longitud;
    }

    public int getIdbarco() {
        return idbarco;
    }

    public int getLongitud() {
        return longitud;
    }


    public Barco.orientacion getOrientacion() {
        return orientacion;
    }

    public boolean isColocado() {
        return colocado;
    }

    public void setColocado(boolean colocado) {
        this.colocado = colocado;
    }


    public void cambiarOrientacion() {
        if(this.getOrientacion() == orientacion.horizontal){
            this.orientacion = orientacion.vertical;
        }else {
            this.orientacion = orientacion.horizontal;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barco barco = (Barco) o;
        return idbarco == barco.idbarco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idbarco);
    }


    public enum orientacion{
        horizontal,
        vertical
    }




}
