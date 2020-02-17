package com.david_galera.hundirlaflota.Models;

import java.util.Objects;

public class Rank {
    private int idRank;
    private int Intentos;
    private String NombreJugador;
    private String TiempoStr;//para mostrar el tiempo en forma de string
    private int TiempoSegs;//para poder ordenar el ranking por tiempo

    public Rank(int intentos, String nombreJugador, String tiempoStr, int tiempoSegs){
        Intentos = intentos;
        TiempoSegs = tiempoSegs;
        TiempoStr = tiempoStr;
        NombreJugador = nombreJugador;
    }

    public Rank(int idrank, int intentos, String nombreJugador, String tiempoStr, int tiempoSegs){
        Intentos = intentos;
        TiempoSegs = tiempoSegs;
        TiempoStr = tiempoStr;
        NombreJugador = nombreJugador;
        idRank = idrank;
    }

    public int getIntentos() {
        return Intentos;
    }

    public String getTiempoStr() {
        return TiempoStr;
    }

    public String getNombreJugador() {
        return NombreJugador;
    }

    public int getTiempoSegs() {
        return TiempoSegs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return idRank == rank.idRank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRank);
    }
}
