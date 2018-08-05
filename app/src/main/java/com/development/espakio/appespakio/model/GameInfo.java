package com.development.espakio.appespakio.model;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {
    private String habilidad;
    private List<Juego> nombreJuegos;

    public class Juego {
        private String nombre;
        private int idImagen;
        private String descripcion;

        public Juego (String nombre, int idImagen, String descripcion){
            this.nombre = nombre;
            this.idImagen = idImagen;
            this.descripcion = descripcion;
        }

        public String getNombre() {
            return nombre;
        }

        public int getIdImagen() {
            return idImagen;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public GameInfo(String habilidad, Juego... juegos){
        this.habilidad = habilidad;
        this.nombreJuegos = new ArrayList<>();
        for (Juego juego : juegos) {
            nombreJuegos.add(juego);
        }
    }

    public GameInfo(){}

    public List<GameInfo> getGamesInfo(){
        List<GameInfo> games = new ArrayList<GameInfo>();

        games.add(new GameInfo(Constants.NAME_HAB_1,
                new Juego(Constants.NAME_JUEGO_1, Constants.IMG_JUEGO_1, Constants.DESC_JUEGO_1),
                new Juego(Constants.NAME_JUEGO_2, Constants.IMG_JUEGO_2, Constants.DESC_JUEGO_2)));

        games.add(new GameInfo(Constants.NAME_HAB_2,
                new Juego(Constants.NAME_JUEGO_3, Constants.IMG_JUEGO_3, Constants.DESC_JUEGO_3),
                new Juego(Constants.NAME_JUEGO_4, Constants.IMG_JUEGO_4, Constants.DESC_JUEGO_4),
                new Juego(Constants.NAME_JUEGO_5, Constants.IMG_JUEGO_5, Constants.DESC_JUEGO_5) ));

        games.add(new GameInfo(Constants.NAME_HAB_3,
                new Juego(Constants.NAME_JUEGO_6, Constants.IMG_JUEGO_6, Constants.DESC_JUEGO_6),
                new Juego(Constants.NAME_JUEGO_7, Constants.IMG_JUEGO_7, Constants.DESC_JUEGO_7) ));

        return games;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public List<Juego> getNombreJuegos() {
        return nombreJuegos;
    }
}
