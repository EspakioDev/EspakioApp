package com.development.espakio.appespakio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.development.espakio.appespakio.model.Usuario;

import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 07/03/2018.
 */

public class tblUsuarios {
    private InternalDB db;

    public  tblUsuarios(Context context) {
        db = new InternalDB(context);
    }

    public void insertUser(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.TABLE_USUARIO_ID, usuario.getID());
        contentValues.put(DBConstants.TABLE_USUARIO_USERNAME, usuario.getNickName());
        contentValues.put(DBConstants.TABLE_USUARIO_BIRTHDATE, usuario.getBirthDay().toString());
        contentValues.put(DBConstants.TABLE_USUARIO_IMAGEN, usuario.getImagen());
        contentValues.put(DBConstants.TABLE_USUARIO_VIDAS, usuario.getVidas());
        contentValues.put(DBConstants.TABLE_USUARIO_ID_CLIENTE, usuario.getIDCliente());
        contentValues.put(DBConstants.TABLE_USUARIO_SELECT, 0);
        db.insert(DBConstants.TABLE_USUARIO, contentValues);
    }

    public void insertUserSelect(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.TABLE_USUARIO_ID, usuario.getID());
        contentValues.put(DBConstants.TABLE_USUARIO_USERNAME, usuario.getNickName());
        contentValues.put(DBConstants.TABLE_USUARIO_BIRTHDATE, usuario.getBirthDay().toString());
        contentValues.put(DBConstants.TABLE_USUARIO_IMAGEN, usuario.getImagen());
        contentValues.put(DBConstants.TABLE_USUARIO_VIDAS, usuario.getVidas());
        contentValues.put(DBConstants.TABLE_USUARIO_ID_CLIENTE, usuario.getIDCliente());
        contentValues.put(DBConstants.TABLE_USUARIO_SELECT, 1);
        db.insert(DBConstants.TABLE_USUARIO, contentValues);
    }

    public int getSize() {
        return db.getTableCount(DBConstants.TABLE_USUARIO);
    }

    public Vector<Usuario> getUsers() {
        Vector<Usuario> usuarios = new Vector<Usuario>();

        Cursor registros = db.getAll(DBConstants.TABLE_USUARIO);

        while (registros.moveToNext()){
            Usuario usuarioActual = new Usuario(registros.getInt(0),        //IDUsuario
                    registros.getString(1),     //UserName
                    registros.getString(2),     //Birthday
                    registros.getString(3),     //Image
                    registros.getInt(4),        //Vidas
                    //registros.getInt(5),         //Logros
                    registros.getInt(5));       //IDCliente

            usuarios.add(usuarioActual);
        }

        return usuarios;
    }

    public Usuario getUserSelec(){
        Cursor registro = db.getRow(DBConstants.TABLE_USUARIO, DBConstants.TABLE_USUARIO_SELECT, Integer.toString(1));
        Usuario usuario = null;

        if (registro.moveToFirst())
            usuario =new Usuario(registro.getInt(0),        //IDUsuario
                    registro.getString(1),     //UserName
                    registro.getString(2),     //Birthday
                    registro.getString(3),     //Image
                    registro.getInt(4),        //Vidas
                    //registros.getInt(5),         //Logros
                    registro.getInt(5));       //IDCliente

        return usuario;
    }

    public void selectUser(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put(DBConstants.TABLE_USUARIO_SELECT, 0);
        db.updateRow(DBConstants.TABLE_USUARIO, values, DBConstants.TABLE_USUARIO_SELECT, Integer.toString(1));

        values = new ContentValues();
        values.put(DBConstants.TABLE_USUARIO_SELECT, 1);
        db.updateRow(DBConstants.TABLE_USUARIO, values, DBConstants.TABLE_USUARIO_ID, Integer.toString(usuario.getID()));
    }

    public void print() {
        db.printTable(DBConstants.TABLE_USUARIO);
    }

}
