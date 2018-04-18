package com.development.espakio.appespakio.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.presenter.IGetUsersPresenter;
import com.development.espakio.appespakio.view.IGetUsersView;

import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 08/03/2018.
 */

public class GetUsersPresenter implements IGetUsersPresenter{

    private IGetUsersView getUsersView;
    private Vector<Usuario> users;
    private String status, result;
    private tblUsuarios table;
    private SharedPreferences preferences;

    public GetUsersPresenter (IGetUsersView getUsersView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.getUsersView = getUsersView;
        table = new tblUsuarios(context);
        users = table.getUsers();
        status = "";
        result = "";
    }

    @Override
    public void performGetUsers() {
        getUsersView.getUsers(getUsersSize());
        getUsersView.getUsersIDs(getUserIDs());
    }

    private int getUsersSize() {
        return table.getSize();
    }

    private String getUserName(int i) {
        return users.get(i).getNickName();
    }

    /*public int getUserID(int i) {
        return users.get(i).getID();
    }*/

    private String getUserIDs() {
        String ids = "";
        for (Usuario user: users) {
            ids += user.IDtoString()+" ";
        }
        return ids;
    }

    public void selectUser(int i){
        table.selectUser(users.get(i));
        putSelectUserPreference();
    }

    private void putSelectUserPreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredySelectUser", true);
        editor.apply();
    }
}
