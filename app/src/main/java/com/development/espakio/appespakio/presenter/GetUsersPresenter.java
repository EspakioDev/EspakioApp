package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Avance;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.IGetUsersView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Created by Spectre 13-4107la on 08/03/2018.
 */

public class GetUsersPresenter implements IGetUsersPresenter {

    private IGetUsersView getUsersView;
    private Vector<Usuario> users;
    private String status;
    private tblUsuarios tablaUsuarios;
    private tblAvance tablaAvance;
    private SharedPreferences preferences;
    private Vector<Avance> avanceJuegos;

    public GetUsersPresenter (IGetUsersView getUsersView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.getUsersView = getUsersView;
        avanceJuegos = new Vector<Avance>();
        tablaAvance = new tblAvance(context);
        tablaUsuarios = new tblUsuarios(context);
        users = tablaUsuarios.getUsers();
        getUsersView.chargeUsers(users);
        status = "";
    }

    @Override
    public void selectUser(int i){
        if(i != -1) {
            tablaUsuarios.selectUser(users.get(i));
            putSelectUserPreference();
            cleanProgress();
            getProgress(users.get(i).getID());
            saveProgress();
            getUsersView.goToGameMenu();
        } else
            getUsersView.goToNewUser();
    }

    private void getProgress(int idUsuario) {
        BackgroundWorker worker = new BackgroundWorker();
        worker.execute("getProgress", Integer.toString(idUsuario));
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError()) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonOb = jsonArray.getJSONObject(i);
                    avanceJuegos.add(new Avance(jsonOb.getInt("idAvanceJuego"),
                            jsonOb.getInt("idJuego"),
                            jsonOb.getInt("Logros"),
                            jsonOb.getInt("Puntaje_Max")));
                }
            }
        } catch (InterruptedException e) {e.printStackTrace();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (JSONException e) {e.printStackTrace();}
    }
    
    private void saveProgress(){
        if(!avanceJuegos.isEmpty())
            tablaAvance.getProgress(avanceJuegos);
        //tablaAvance.print();
    }

    private void putSelectUserPreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredySelectUser", true);
        editor.apply();
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

    private void cleanProgress(){
        tablaAvance.clean();
    }
}
