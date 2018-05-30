package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.model.Avance;
import com.development.espakio.appespakio.view.IGameView;

public class GamePresenter implements IGamePresenter{
    private IGameView gameView;
    private SharedPreferences preferences;
    private tblAvance tablaAvance;
    private Avance avance;
    private int score;

    public GamePresenter(IGameView gameView, Context context) {
        preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        tablaAvance = new tblAvance(context);
        this.gameView = gameView;
    }

    public void checkScore(int score) {
        saveScore(score);
        if(isGameSelect()){
            if (avance.checkPuntaje(score)) {
                avance.checkLogro();
                tablaAvance.setAvance(avance);
            }
        }
    }

    private void saveScore(int i) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ScoreAct", i);
        editor.apply();
    }

    private int getGameSelect(){
        return preferences.getInt("GameSelect", -1);
    }

    private boolean isGameSelect() {
        if (getGameSelect() != -1) {
            avance = tablaAvance.getNivelJuego(getGameSelect());
            return true;
        } else
            return false;
    }
}
