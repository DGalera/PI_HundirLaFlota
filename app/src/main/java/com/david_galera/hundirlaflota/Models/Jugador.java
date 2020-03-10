
package com.david_galera.hundirlaflota.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jugador {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String toJSON() {
        return "{" +
                "\"nombre\":" + "\"" + getNombre() + "\"" +
                "}";
    }
}