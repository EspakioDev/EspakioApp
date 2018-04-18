package com.development.espakio.appespakio.model;

import android.content.Context;

import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.presenter.IGamesMenuPresenter;
import com.development.espakio.appespakio.view.IGamesMenuView;

/**
 * Created by Spectre 13-4107la on 19/03/2018.
 */

public class GamesMenuPresenter  implements IGamesMenuPresenter{

    private Context context;
    private IGamesMenuView gamesMenuView;

    public GamesMenuPresenter(IGamesMenuView gamesMenuView, Context context) {
        this.gamesMenuView = gamesMenuView;
        this.context = context;
    }
    @Override
    public void performGamesMenu() {

        getUserID();

    }

    private void getUserID() {
        tblUsuarios tabla = new tblUsuarios(context);
        Usuario user = tabla.getUserSelec();
        gamesMenuView.getUserID(user.getID());
    }
}
