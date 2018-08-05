package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.IGamesMenuView;

/**
 * Created by Spectre 13-4107la on 19/03/2018.
 */

public class GamesMenuPresenter  implements IGamesMenuPresenter {

    private Context context;
    private SharedPreferences preferences;
    private IGamesMenuView gamesMenuView;
    private Usuario user;

    public GamesMenuPresenter(IGamesMenuView gamesMenuView, Context context) {
        this.gamesMenuView = gamesMenuView;
        this.context = context;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    private int isAlredySelectImageUser() {
        tblUsuarios tabla = new tblUsuarios(context);
        user = tabla.getUserSelec();
        return user.getImagen();
    }

    @Override
    public void performGamesMenu() {
        //getUserID();
        //getDataGames();
        getProgress();
    }

    @Override
    public void chargeImage() {
        gamesMenuView.putUserImage(isAlredySelectImageUser());
    }

    @Override
    public void putGameSelectPref(int i) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("GameSelect", i);
        editor.apply();
    }

    private void getUserID() {
        gamesMenuView.getUserID(user.getID());
    }

    private void getProgress() {
        tblAvance tabla = new tblAvance(context);
        tabla.print();
    }
}
