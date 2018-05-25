package com.development.espakio.appespakio.model;

public class Avance {
    private int ID;
    private int IDJuego;
    private int logro;
    private int puntaje;

    public Avance(int id, int idJuego) {
        ID = id;
        IDJuego = idJuego;
        logro = 0;
        puntaje = 0;
    }

    public Avance(int id, int idJuego, int logro, int puntaje) {
        this.ID = id;
        this.IDJuego = idJuego;
        this.logro = logro;
        this.puntaje = puntaje;
    }

    public Avance(int idJuego, int logro, int puntaje) {
        this.IDJuego = idJuego;
        this.logro = logro;
        this.puntaje = puntaje;
    }

    public void setID(int ID) { this.ID = ID; }

    public int getID() { return ID; }

    public int getIDJuego() { return IDJuego; }

    public int getLogro() { return logro; }

    public int getPuntaje() { return puntaje; }

    public boolean checkLogro() {
         if ( (logro == 0 && puntaje > 19) || (logro == 1 && puntaje > 39) || (logro == 2 && puntaje > 59)){
             logro++;
             return true;
         } else
             return false;
    }

    public boolean checkPuntaje(int puntaje) {
        if ( puntaje > this.puntaje) {
            this.puntaje = puntaje;
            return true;
        } else
            return false;
    }

}
