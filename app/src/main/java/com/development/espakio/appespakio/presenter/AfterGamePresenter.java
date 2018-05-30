package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.AdapterViewAnimator;

import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.model.Avance;
import com.development.espakio.appespakio.view.IAfterGameView;

public class AfterGamePresenter implements IAfterGamePresenter{

    private IAfterGameView afterGameView;
    private tblAvance tablaAvance;
    private Avance avance;
    private SharedPreferences preferences;
    private int score;

    public AfterGamePresenter(IAfterGameView afterGameView, Context context) {
        this.afterGameView = afterGameView;
        tblAvance tablaAvance = new tblAvance(context);
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public void checkScore(int idJuego, int score) {
        avance = tablaAvance.getNivelJuego(idJuego);
        if (avance.checkPuntaje(score)) {
            avance.checkLogro();
            tablaAvance.setAvance(avance);
        }
    }

    public void saveProgress() {

    }
}
