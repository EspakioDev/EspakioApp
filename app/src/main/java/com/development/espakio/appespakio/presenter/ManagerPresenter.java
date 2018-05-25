package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import com.development.espakio.appespakio.presenter.IManagerPresenter;
import com.development.espakio.appespakio.view.IManagerView;


/**
 * Created by Spectre 13-4107la on 21/03/2018.
 */

public class ManagerPresenter implements IManagerPresenter {

    private SharedPreferences preferences;
    private Context context;
    private IManagerView managerView;

    public ManagerPresenter(IManagerView managerView, Context context) {
        this.managerView = managerView;
        this.context = context;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public void performOpening() {
        if (isAlredySelectUser()) {
            managerView.goToGamesMenu();
        } else if (isAlredyLogin()) {
            managerView.goToUsersMenu();
        } else if (isAlredyOpened()) {
            managerView.goToBegin();
        } else {
            putOpenedPreference();
            managerView.goToWelcome();
        }
    }

    private boolean isAlredyOpened(){
        return preferences.getBoolean("isAlredyOpened", false);
        //return true;
    }

    private boolean isAlredyLogin() {
        return preferences.getBoolean("isAlredyLogin", false);
    }

    private boolean isAlredySelectUser() {
        return preferences.getBoolean("isAlredySelectUser", false);
    }

    private void putOpenedPreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredyOpened", true);
        editor.apply();
    }
}
