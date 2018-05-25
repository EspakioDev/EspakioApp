package com.development.espakio.appespakio.model;


public final class DescripcionJuego {

    public static final String NOMBRE[] = new String[] {
            "Juego1",
            "Juego2",
            "Juego3",
            "Juego4",
            "Juego5",
            "Juego6",
            "Juego7"
    };
    public static final String DESCRIPCION[] = new String[] {
            "Descripción del Juego1",
            "Descripcion del Juego2",
            "Descripcion del Juego3",
            "Descripcion del Juego4",
            "Descripcion del Juego5",
            "Descripcion del Juego6",
            "Descripcion del Juego7"
    };
    public static final String HABILIDAD[] = new String[] {
            "Habilidad1",
            "Habilidad2",
            "Habilidad3"
    };
    public static final String INSTRUCCIONES[] = new String[] {
            "Instrucciones del Juego1",
            "Instrucciones del Juego2",
            "Instrucciones del Juego3",
            "Instrucciones del Juego4",
            "Instrucciones del Juego5",
            "Instrucciones del Juego6",
            "Instrucciones del Juego7"
    };


    public static String[] getJuego(int i) {
        return new String[]{NOMBRE[i], DESCRIPCION[i], HABILIDAD[i]};
    }

    public static String getInstruccion(int i) {
        return INSTRUCCIONES[i];
    }

    public static String getHabilidad(int i){
        switch (i){
            case 0:     //Juego1
            case 1:     //Juego2
                return HABILIDAD[0];        //--------- Habilidad1
            case 2:     //Juego3
            case 3:     //Juego4
                return HABILIDAD[1];        //--------- Habilidad2
            case 4:     //Juego5
            case 5:     //Juego6
            case 6:     //Juego7
                return HABILIDAD[2];        //--------- Habilidad3
            default:
                return null;
        }
    }

    public  static int noJuegos(){ return NOMBRE.length; }
}
