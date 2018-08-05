package com.development.espakio.appespakio.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 20/02/2018.
 */
@SuppressWarnings("serial")
public class Cliente implements Serializable{
    private int id;
    private Vector<Usuario> Usuarios;

    public Cliente (int id) {
        this.id = id;
        Usuarios = new Vector<Usuario>(3);
    }

    public int getID(){
        return  id;
    }

    /*public void nuevoUsuario(int idUsuario, String nickName, String birthday){
        Usuarios.add(new Usuario(idUsuario, nickName, birthday, id));
    }*/

    public void nuevoUsuario(int idUsuario, String nickName, String birthday, int imagen, int vidas){

        Usuarios.add(new Usuario(idUsuario, nickName, birthday, imagen, vidas, id));
    }

    public Vector<Usuario> getUsuarios () {
        return Usuarios;
    }

    public boolean usuariosLlenos() {
        if(Usuarios.size() > 4)
            return false;
        return true;
    }

    public String IDUsuarios() {
        String result = "";
        for (Usuario usuario: Usuarios) {
            result += usuario.getID()+ ",";
        }
        return result;
    }

    /*public Usuario ultimoUsuario() {
        return Usuarios.lastElement();
    }*/

    /*private void agregarUsuario(Usuario usuario){
        Usuarios.add(usuario);
    }*/
}
