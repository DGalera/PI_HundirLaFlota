package com.david_galera.hundirlaflota.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper
{

    String crearRanking = "CREATE TABLE Ranking(idRank INTEGER PRIMARY KEY AUTOINCREMENT , intentos INTEGER, nombreJugador TEXT, tiempoStr TEXT, tiempoSegs Integer)";

    public BaseDatos(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(crearRanking);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnt, int versionNue)
    {
        db.execSQL("DROP TABLE IF EXISTS Ranking");
        db.execSQL(crearRanking);
    }
}