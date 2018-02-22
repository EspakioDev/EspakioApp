package com.development.espakio.appespakio.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Spectre 13-4107la on 20/02/2018.
 */
@SuppressWarnings("serial")
public class Cliente implements Serializable{
    private int id;
    private Usuario[] usuarios;

    public Cliente (int id)
    {
        this.id = id;
        usuarios = new Usuario[]{null, null, null};
    }

    public int getID(){
        return  id;
    }

    public boolean nuevoUsuario(String nickName, Date birthday){
        int noUsuario = usuariosLlenos();
        if(noUsuario != -1) {
            //usuarios[noUsuario] = new Usuario(nickName, birthday, id);
            return true;
        }
        return false;
    }

    public int usuariosLlenos() {
        for (int i = 0; i < 3; i++) {
            if (usuarios[i] == null)
                return i;
        }
        return -1;
    }

    public String toString(){
        return "[idCliente: "+id+"]";
    }

}
