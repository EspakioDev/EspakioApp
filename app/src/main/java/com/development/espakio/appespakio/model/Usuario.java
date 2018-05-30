package com.development.espakio.appespakio.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Spectre 13-4107la on 20/02/2018.
 */

public class Usuario implements Serializable{
    private int ID;
    private String nickName;
    private Date birthDay;
    private int imagen;
    private int vidas;
    //private int logros;
    private int IDCliente;

    public Usuario(int ID, String nickName, int imagen){
        this.ID = ID;
        this.nickName = nickName;
        this.imagen = imagen;
    }

    public Usuario(int ID, String nickName, String birthday, int imagen, int IDCliente){
        this.ID = ID;
        this.nickName = nickName;
        this.birthDay = stringToDate(birthday);
        this.IDCliente = IDCliente;
        this.imagen = imagen;
        vidas = Constants.VIDAS;
        //logros = 1;
    }

    public Usuario(int ID, String nickName, String birthday, int imagen, int vidas, int IDCliente){
        this.ID = ID;
        this.nickName = nickName;
        this.birthDay = stringToDate(birthday);
        this.IDCliente = IDCliente;
        this.imagen = imagen;
        this.vidas = vidas;
        //this.logros = logros;
    }

    /* public String toString() {
        return "[ID: "+ID+", Nickname: "+nickName+", Birthday: "+birthDate
                +", Imagen: "+imagen+", Vidas: "+vidas+", Logros: "+logros+", IDCliente: "+IDCliente+"]";
    }*/


    public int getID() { return ID;}

    public String IDtoString() {
        return  "[ID: "+ ID +"]";
    }

    public String getNickName() {
        return nickName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getVidas() {
        return vidas;
    }

    //public int getLogros() {return logros;}

    public int getIDCliente() {
        return IDCliente;
    }

    private Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha;
    }

}