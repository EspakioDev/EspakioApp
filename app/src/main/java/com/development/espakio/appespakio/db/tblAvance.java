package com.development.espakio.appespakio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.development.espakio.appespakio.model.Avance;


import java.util.Vector;

public class tblAvance {

    private DataBase db;

    public  tblAvance(Context context) {
        db = new DataBase(context);
    }

    public void insertAvance(Avance avance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.TABLE_AVANCE_ID, avance.getID());
        contentValues.put(DBConstants.TABLE_AVANCE_ID_JUEGO, avance.getIDJuego());
        contentValues.put(DBConstants.TABLE_AVANCE_LOGRO, avance.getLogro());
        contentValues.put(DBConstants.TABLE_AVANCE_PUNTAJE, avance.getPuntaje());
        db.insert(DBConstants.TABLE_AVANCE, contentValues);
    }

    public int getSize() {
        return db.getTableCount(DBConstants.TABLE_AVANCE);
    }

    public Vector<Avance> getAvance() {
        Vector<Avance> avances = new Vector<Avance>();

        Cursor registros = db.getAll(DBConstants.TABLE_AVANCE);

        while (registros.moveToNext()){
            Avance avanceActual = new Avance( registros.getInt(1),     //IDJuego
                    registros.getInt(2),     //Logro
                    registros.getInt(3));    //Puntaje_Max

            avances.add(avanceActual);
        }
        return avances;
    }

    public Avance getNivelJuego(int idJuego){
        Cursor registro = db.getRow(DBConstants.TABLE_AVANCE, DBConstants.TABLE_AVANCE_ID_JUEGO, Integer.toString(idJuego));
        Avance avance = null;

        if (registro.moveToFirst())
            avance = new Avance(registro.getInt(0),        //IDAvanceJuego
                    registro.getInt(2),     //Logro
                    registro.getInt(3));    //Puntaje

        return avance;
    }

    public void print() {
        db.printTable(DBConstants.TABLE_AVANCE);
        //
    }

    public void getProgress(Vector<Avance> avanceJuegos) {
        for (Avance avance: avanceJuegos) {
            insertAvance(avance);
        }
    }

    public void setAvance(Avance avance) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.TABLE_AVANCE_PUNTAJE, avance.getPuntaje());
        values.put(DBConstants.TABLE_AVANCE_LOGRO, avance.getLogro());
        db.updateRow(DBConstants.TABLE_AVANCE, values, DBConstants.TABLE_AVANCE_ID, Integer.toString(avance.getID()));
    }

    public void clean() {
        db.cleanTable(DBConstants.TABLE_AVANCE);
    }
}
