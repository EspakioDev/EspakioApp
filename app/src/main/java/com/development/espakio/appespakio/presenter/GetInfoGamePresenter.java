package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.view.IGetInfoGameView;

public class GetInfoGamePresenter implements IGetInfoGamePresenter {

    private Context context;
    private SharedPreferences preferences;
    private IGetInfoGameView infoGameView;

    public GetInfoGamePresenter(IGetInfoGameView infoGameView, Context context) {
        this.infoGameView = infoGameView;
        this.context = context;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    public void performOpening() {
        int iGameSelect = getGameSelect();

        if(iGameSelect != -1)
            //infoGameView.showInfoGame(getInfoGame(iGameSelect));
            ;
        else
            infoGameView.failed();
    }

    private int getGameSelect(){
        return preferences.getInt("GameSelect", -1);
    }

    /*private String[] getInfoGame(int i) {
        return DescripcionJuego.getJuego(i);
    }*/
}
