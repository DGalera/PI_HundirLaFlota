
package com.david_galera.hundirlaflota.Models;

import com.david_galera.hundirlaflota.Activities.MainActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ranking {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("intentos")
    @Expose
    private Integer intentos;
    @SerializedName("tiempo")
    @Expose
    private String tiempo;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Ranking(String nombre, Integer intentos, String tiempo) {
        this.nombre = nombre;
        this.intentos = intentos;
        this.tiempo = tiempo;
    }

    public int getTiempoSegs(){
        String[] units = getTiempo().split(":"); //will break the string up into an array
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int seconds = Integer.parseInt(units[2]);
        int duration = 3600 * hours + 60 * minutes + seconds; //add up our values
        return duration;
    }

    public String toJSON() {
        return "{" +
                "\"nombre\":" + "\"" + getNombre() + "\"," +
                "\"intentos\":" + "\"" + getIntentos() + "\"," +
                "\"tiempo\":" + "\"" + getTiempo() + "\"" +
                "}";
    }
}
