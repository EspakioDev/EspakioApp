package com.development.espakio.appespakio.db;

/**
 * Created by Spectre 13-4107la on 06/03/2018.
 */

public final class DBConstants {
    public static final String DATABASE_NAME = "dbEspakio";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLIENTE                = "tblCliente";
    public static final String TABLE_CLIENTE_ID             = "ID";
    //public static final String TABLE_CLIENTE_EMAIL        = "Email";

    public static final String TABLE_USUARIO                = "tblUsuario";
    public static final String TABLE_USUARIO_ID             = "ID";
    public static final String TABLE_USUARIO_USERNAME       = "UserName";
    public static final String TABLE_USUARIO_BIRTHDATE      = "BirthDate";
    public static final String TABLE_USUARIO_IMAGEN         = "Imagen";
    public static final String TABLE_USUARIO_VIDAS          = "Vidas";
    //public static final String TABLE_USUARIO_LOGROS         = "Logros";
    public static final String TABLE_USUARIO_ID_CLIENTE     = "IDCliente";
    public static final String TABLE_USUARIO_SELECT         = "Seleccionado";

    public static final String TABLE_JUEGO                  = "tblJuegos";
    public static final String TABLE_JUEGO_ID               = "ID";
    public static final String TABLE_JUEGO_NOMBRE           = "Nombre";
    public static final String TABLE_JUEGO_DESCRIPCION      = "Descripcion";
    public static final String TABLE_JUEGO_HABILIDAD        = "Habilidad";
    public static final String TABLE_JUEGO_PRUEBA           = "Prueba";

    public static final String TABLE_AVANCE                 = "tblAvance";
    public static final String TABLE_AVANCE_ID              = "ID";
    public static final String TABLE_AVANCE_ID_JUEGO        = "ID_Juego";
    public static final String TABLE_AVANCE_LOGRO           = "Logro";
    public static final String TABLE_AVANCE_PUNTAJE         = "Puntaje";


}
