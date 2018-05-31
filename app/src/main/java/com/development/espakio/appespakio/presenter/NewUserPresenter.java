package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.db.tblCliente;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Avance;
import com.development.espakio.appespakio.model.Constants;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.INewUserView;

import org.json.JSONArray;
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
    private int[] idsJuegos;
    private int iJuegos;
    private Context context;
    private SharedPreferences preferences;
    private int idImagen;

    public NewUserPresenter(INewUserView newUserView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.context = context;
        this.newUserView = newUserView;
        iJuegos = Constants.NO_JUEGOS;
        idsJuegos = new int[iJuegos];
        status = "";
        idUsuario = 0;
        idImagen = Constants.IMAGEN_DEFAULT;
    }

    @Override
    public void performChargeImage() {
        idImagen = isAlredySelectImageUser();
        newUserView.putUserImage(idImagen);
    }

    @Override
    public void performAddUser(String userName, String birthday) {
        if(isEmptyField(userName, birthday)) {
            newUserView.newUserEmptyFields();

        } else {
            registerUser(userName, birthday, idImagen);
            if(onFailed()) {
                newUserView.Failed();
            } else if (onError()) {
                newUserView.Error();
            } else {
                onSuccess(userName, birthday, idImagen);
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

    private void registerUser(String userName, String birthday, int imagen) {
        String type = "newUser";
        BackgroundWorker worker = new BackgroundWorker();

        tblCliente tablaCliente = new tblCliente(context);
        idCliente = tablaCliente.getClient().getID();

        worker.execute(type,  userName, birthday, Integer.toString(idCliente), Integer.toString(imagen), Integer.toString(Constants.VIDAS), Integer.toString(iJuegos));

        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError()){
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                idUsuario = jsonArray.getInt(0);

                for (int i = 1; i < jsonArray.length(); i++) {
                    idsJuegos[i-1] = jsonArray.getInt(i);
                }
            }
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

    private void onSuccess(String userName, String birthday, int imagen) {
        tblUsuarios tablaUsuario = new tblUsuarios(context);
        tblAvance tablaAvance = new tblAvance(context);
        Usuario user = new Usuario(idUsuario, userName, birthday, imagen, idCliente);

        for (int i = 0; i < iJuegos; i++) {
            tablaAvance.insertAvance(new Avance(idsJuegos[i], i));
        }

        tablaUsuario.insertUserSelect(user);
        tablaAvance.print();
    }

    private void putSelectUserPreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredySelectUser", true);
        editor.apply();
    }

    private int isAlredySelectImageUser() {
        int idImage = preferences.getInt("ImageUser", Constants.IMAGEN_DEFAULT);
        preferences.edit().remove("ImageUser").commit();
        return idImage;
    }
}
