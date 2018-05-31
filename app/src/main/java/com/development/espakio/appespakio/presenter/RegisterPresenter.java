package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblCliente;
import com.development.espakio.appespakio.model.Cliente;
import com.development.espakio.appespakio.view.IRegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Spectre 13-4107la on 08/03/2018.
 */

public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterView registerView;
    private String status, message;
    private int idCliente;
    private Context context;
    private SharedPreferences preferences;

    public RegisterPresenter (IRegisterView registerView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        this.context = context;
        this.registerView = registerView;
        status = "";
        message = "";
        idCliente = 0;
    }

    @Override
    public void performRegister(String email, String password, String verPassword) {

        if (isEmptyField(email, password)) {
            registerView.registerEmptyFields();
        } else if (!isFormatValid(email)) {
            registerView.registerFormatValidation();
        } else if (!isPasswordMatch(password, verPassword)) {
            registerView.passwordNotMatch();
        }else {
            getResult(email, password);
            if(isEmailDuplicate()) {
                registerView.emailDuplicate();
            } else if(onFailed()) {
                registerView.registerFailed();
            } else if (onError()) {
                registerView.registerError();
            } else {
                onSucces();
                putLoginPreference();
                registerView.idCliente(idCliente);
                registerView.registerSuccess();
            }
        }
    }

    private boolean isPasswordMatch(String password, String verPassword) {
        return password.equals(verPassword);
    }

    private boolean isEmailDuplicate() {
        return message.equals("El correo ya esta registrado");
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
        String type = "register";
        BackgroundWorker worker = new BackgroundWorker();
        worker.execute(type, email, password);
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            message = jsonObject.getString("message");
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

    private void onSucces(){
        tblCliente tablaCliente = new tblCliente(context);
        tablaCliente.insertClient(new Cliente(idCliente));
    }

    private void putLoginPreference(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAlredyLogin", true);
        editor.apply();
    }
}
