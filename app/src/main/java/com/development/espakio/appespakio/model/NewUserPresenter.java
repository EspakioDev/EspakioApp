package com.development.espakio.appespakio.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.development.espakio.appespakio.db.tblCliente;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.presenter.INewUserPresenter;
import com.development.espakio.appespakio.view.INewUserView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Spectre 13-4107la on 15/03/2018.
 */

public class NewUserPresenter implements INewUserPresenter{

    private INewUserView newUserView;
    private String status;
    private int idUsuario;
    private int idCliente;
    private Context context;
    private SharedPreferences preferences;

    public NewUserPresenter(INewUserView newUserView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.context = context;
        this.newUserView = newUserView;
        status = "";
        idUsuario = 0;
    }

    @Override
    public void performAddUser(String userName, String birthday) {

        if(isEmptyField(userName, birthday)) {
            newUserView.newUserEmptyFields();

        } else {
            getResult(userName, birthday);
            if(onFailed()) {
                newUserView.newUserFailed();
            } else if (onError()) {
                newUserView.newUSerError();
            } else {
                onSuccess(userName, birthday);
                putSelectUserPreference();
                newUserView.idUsuario(idUsuario);
                newUserView.newUserSuccess();
            }
        }
    }

    private boolean isEmptyField(String userName, String birthday) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(birthday))
            return true;
        return false;
    }

    private void getResult(String userName, String birthday) {
        String type = "newUser";
        BackgroundWorker1 worker = new BackgroundWorker1();

        tblCliente tablaCliente = new tblCliente(context);
        idCliente = tablaCliente.getClient().getID();

        worker.execute(type, userName, birthday, Integer.toString(idCliente));
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError())
                idUsuario = jsonObject.getInt("result");
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

    private void onSuccess(String userName, String birthday) {
        tblUsuarios tablaUsuario = new tblUsuarios(context);
        Usuario user = new Usuario(idUsuario, userName, birthday, idCliente);
        tablaUsuario.insertUserSelect(user);
    }

    private void putSelectUserPreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredySelectUser", true);
        editor.apply();
    }
}
