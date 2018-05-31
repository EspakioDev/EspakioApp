package com.development.espakio.appespakio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.development.espakio.appespakio.model.Cliente;

/**
 * Created by Spectre 13-4107la on 15/03/2018.
 */

public class tblCliente {
    private DataBase db;

    public  tblCliente(Context context) {
        db = new DataBase(context);
    }

    public void insertClient(Cliente cliente) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.TABLE_CLIENTE_ID, cliente.getID());
        db.insert(DBConstants.TABLE_CLIENTE, contentValues);
        db.close();
    }

    public int getSize() {
        int i = db.getTableCount(DBConstants.TABLE_CLIENTE);
        db.close();
        return i;
    }

    public Cliente getClient() {
        Cursor registros = db.getAll(DBConstants.TABLE_CLIENTE);
        registros.moveToFirst();
        Cliente cliente = new Cliente(registros.getInt(0));
        db.close();
        return cliente;
    }

    public void clean() {
        db.cleanTable(DBConstants.TABLE_CLIENTE);
    }
}
