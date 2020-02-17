package com.david_galera.hundirlaflota.Models;

public class Casilla {

    private int x,y,ancho;
    private boolean tieneBarco = false;
    private boolean destapado = false;
    private boolean pulsado = false;
    private int idBarco;

    public void fijarCoordenadas(int x,int y, int ancho) {
        this.x=x;
        this.y=y;
        this.ancho=ancho;
    }

    public boolean dentro(int xx,int yy) {
        if (xx >= this.x && xx <= this. x + ancho && yy >= this.y && yy <= this.y + ancho)
            return true;
        else
            return false;
    }

    public boolean isDestapado() {
        return destapado;
    }

    public void setDestapado(boolean destapado) {
        this.destapado = destapado;
    }

    public int getIdBarco() {
        return idBarco;
    }

    public void setIdBarco(int idBarco) {
        this.idBarco = idBarco;
    }

    public boolean isTieneBarco() {
        return tieneBarco;
    }

    public void setTieneBarco(boolean tieneBarco) {
        this.tieneBarco = tieneBarco;
    }

    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }
}
