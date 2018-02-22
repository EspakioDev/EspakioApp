package com.development.espakio.appespakio.models;

import android.annotation.SuppressLint;

import java.util.Date;

/**
 * Created by Spectre 13-4107la on 20/02/2018.
 */

public class Usuario {
    private int idUsuario;
    private String nickName;
    private Date birthDate;
    private String imagen;
    private int vidas;
    private int logros;
    private int idCliente;

    public Usuario(int idUsuario, String nickName, Date birthdate, int idCliente){

        this.idUsuario = idUsuario;
        this.nickName = nickName;
        this.birthDate = birthdate;
        this.idCliente = idCliente;
        imagen = "Default.png";
        vidas = 3;
        logros = 0;
    }

    public String toString() {
        return "[ID: "+idUsuario+", Nickname: "+nickName+", Birthday: "+birthDate
                +", Imagen: "+imagen+", Vidas: "+vidas+", Logros: "+logros+", idCliente: "+idCliente+"]";
    }

}
