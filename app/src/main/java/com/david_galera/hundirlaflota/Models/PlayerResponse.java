package com.david_galera.hundirlaflota.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerResponse {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("result")
    @Expose
    private Jugador result;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Jugador getResult() {
        return result;
    }

    public void setResult(Jugador result) {
        this.result = result;
    }
}
