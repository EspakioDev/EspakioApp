package com.development.espakio.appespakio.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 20/02/2018.
 */
@SuppressWarnings("serial")
public class Cliente implements Serializable{
    private int id;
    private Vector<Usuario> Usuarios;

    public Cliente (int id)
    {
        this.id = id;
        Usuarios = new Vector<Usuario>(3);
    }

    public int getID(){
        return  id;
    }

    public void nuevoUsuario(int idUsuario, String nickName, String birthday){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Usuarios.add(new Usuario(idUsuario, nickName, fecha, id));
    }

    public void nuevoUsuario(int idUsuario, String nickName, String birthday, String imagen, int vidas, int logros){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Usuarios.add(new Usuario(idUsuario, nickName, fecha, imagen, vidas, logros, id));
    }

    public boolean usuariosLlenos() {
        if(Usuarios.size() > 4)
            return false;
        return true;
    }

    public Usuario ultimoUsuario() {
        return Usuarios.lastElement();
    }

    /*private void agregarUsuario(Usuario usuario){
        Usuarios.add(usuario);
    }*/

    public String IDtoString(){
        return "[idCliente: "+id+"]";
    }

    public String IDUsuarios() {
        String result = "";
        for (Usuario usuario: Usuarios) {
            result += usuario.getID()+ ",";
        }
        return result;
    }
}
