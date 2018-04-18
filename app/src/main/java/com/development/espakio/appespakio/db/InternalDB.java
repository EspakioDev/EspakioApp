package com.development.espakio.appespakio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Spectre 13-4107la on 06/03/2018.
 */

public class InternalDB extends SQLiteOpenHelper {

    public InternalDB(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaCliente = "CREATE TABLE " + DBConstants.TABLE_CLIENTE + "(" +
                DBConstants.TABLE_CLIENTE_ID + " INTEGER PRIMARY KEY " +
                //DBConstants.TABLE_CLIENTE_EMAIL + " TEXT " +
                ")";
        String queryCrearTablaUsuario = "CREATE TABLE " + DBConstants.TABLE_USUARIO+ "(" +
                DBConstants.TABLE_USUARIO_ID + " INTEGER PRIMARY KEY, " +
                DBConstants.TABLE_USUARIO_USERNAME + " TEXT, " +
                DBConstants.TABLE_USUARIO_BIRTHDATE + " DATE, " +
                DBConstants.TABLE_USUARIO_IMAGEN + " TEXT, " +
                DBConstants.TABLE_USUARIO_VIDAS + " INTEGER, " +
                //DBConstants.TABLE_USUARIO_LOGROS + " INTEGER, " +
                DBConstants.TABLE_USUARIO_ID_CLIENTE + " INTEGER, " +
                DBConstants.TABLE_USUARIO_SELECT + " INTEGER DEFAULT 0, "+
                "FOREIGN KEY (" + DBConstants.TABLE_USUARIO_ID_CLIENTE + ") " +
                "REFERENCES " + DBConstants.TABLE_CLIENTE + "(" + DBConstants.TABLE_CLIENTE_ID + ")" +
                ")";

        db.execSQL(queryCrearTablaCliente);
        db.execSQL(queryCrearTablaUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USUARIO);
        onCreate(db);
    }

    public Cursor getAll(String table) {
        String query = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void insert(String table, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, contentValues);
        db.close();
    }

    public int getTableCount(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, table);
    }

    public Cursor getRow(String table, String column, String condition) {
        String query = "SELECT * FROM " + table + " WHERE "+ column +" = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, new String[]{condition});
    }

    public Cursor getColumn(String table, String column) {
        String query = "SELECT " + column + " FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void updateRow(String table, ContentValues values, String columnCodition, String condition) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(table, values, columnCodition + " = ?", new String[]{condition});
    }

    private void updateColum() {

    }

    public void printTable(String table){
        Cursor registros = getAll(table);

        while (registros.moveToNext()){
            String fila = "|";

            for (int i = 0; i < registros.getColumnCount(); i++) {
                fila += registros.getString(i) + "|";
            }
            Log.d(table , fila);
        }

    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
