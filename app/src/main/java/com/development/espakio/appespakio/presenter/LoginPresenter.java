package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblCliente;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Cliente;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.ILoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Spectre 13-4107la on 05/03/2018.
 */

public class LoginPresenter implements ILoginPresenter{

    private ILoginView loginView;
    private String status;
    private int idCliente;
    private Cliente client;
    private Context context;
    private SharedPreferences preferences;

    public LoginPresenter (ILoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
        status = "";
        idCliente = 0;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public void performLogin(String email, String password) {

        if(isEmptyField(email, password)) {
            loginView.loginEmptyFields();
        } else if (!isFormatValid(email)){
            loginView.loginFormatValidation();
        } else {
            getResult(email, password);
            if(onFailed()) {
                loginView.loginFailed();
            } else if (onError()) {
                loginView.loginError();
            } else {
                onSuccess();
                putLoginPreference();
                loginView.idCliente(idCliente);
                loginView.loginSuccess();
            }
        }
    }

    private boolean isFormatValid(String email) {
        //return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return true;
    }

    private boolean isEmptyField(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            return true;
        return false;
    }

    private void getResult(String email, String password) {
        String type = "login";
        BackgroundWorker worker = new BackgroundWorker();
        worker.execute(type, email, password);
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError())
                idCliente = jsonObject.getInt("result");
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

    private void getUsers() {
        client = new Cliente(idCliente);
        BackgroundWorker worker = new BackgroundWorker();
        worker.execute("getUsers", Integer.toString(idCliente));
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError()) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonOb = jsonArray.getJSONObject(i);
                    client.nuevoUsuario(jsonOb.getInt("idUsuario"),
                            jsonOb.getString("Usuario"),
                            jsonOb.getString("Fecha_Nacimiento"),
                            jsonOb.getInt("Imagen"),
                            jsonOb.getInt("Vidas"));
                    //jsonOb.getInt("Logros"));
                }
            }
        } catch (InterruptedException e) {e.printStackTrace();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (JSONException e) {e.printStackTrace();}
    }

    private void onSuccess() {
        tblUsuarios tablaUsuario = new tblUsuarios(context);
        tblCliente tablaCliente = new tblCliente(context);

        getUsers();

        tablaCliente.insertClient(client);

        for (Usuario user: client.getUsuarios()) {
            tablaUsuario.insertUser(user);
        }
    }

    private void putLoginPreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredyLogin", true);
        editor.apply();
    }

}
