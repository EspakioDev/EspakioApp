package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.model.Avance;
import com.development.espakio.appespakio.view.IAfterGameView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AfterGamePresenter implements IAfterGamePresenter{

    private IAfterGameView afterGameView;
    private tblAvance tablaAvance;
    private Avance avance;
    private SharedPreferences preferences;
    private int score;
    private String status;

    public AfterGamePresenter(IAfterGameView afterGameView, Context context) {
        this.afterGameView = afterGameView;
        tablaAvance = new tblAvance(context);
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public void checkScore(int idJuego, int score) {
        avance = tablaAvance.getNivelJuego(idJuego);
        afterGameView.chargeValues(avance.getPuntaje());
        if (avance.checkPuntaje(score)) {
            avance.checkLogro();
            tablaAvance.setAvance(avance);
            afterGameView.newScore();
            //saveProgress();
        }
    }

    public void saveProgress() {
        BackgroundWorker worker = new BackgroundWorker();
        worker.execute("setProgress", avance.getID()+"", avance.checkLogro()+"", avance.getPuntaje()+"");
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
        } catch (InterruptedException e) {e.printStackTrace();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (JSONException e) {e.printStackTrace();}
    }

    private boolean onFailed() {
        if(status.contains("Failed"))
            return true;
        return false;
    }

    private boolean onError() {
        if(status.contains("Error"))
            return true;
        return false;
    }
}
