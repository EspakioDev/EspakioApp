package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.IUserPerfilView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class UserPerfilPresenter implements IUserPerfilPresenter {

    private IUserPerfilView userPerfilView;
    private tblUsuarios tableUsers;
    private tblAvance tableProgress;
    private Usuario user;
    private SharedPreferences preferences;
    private String status;

    public UserPerfilPresenter(IUserPerfilView userPerfilView, Context context) {
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        tableUsers = new tblUsuarios(context);
        tableProgress = new tblAvance(context);
        user = tableUsers.getUserSelec();
        this.userPerfilView = userPerfilView;
        status = "";
    }

    @Override
    public void performCharge() {
        selectImage();
        userPerfilView.chargeInfoUser(user.getImagen(), user.getNickName());
    }

    private int isAlredySelectImageUser() {
        return preferences.getInt("ImageUser", user.getIdImagen());
    }

    private boolean selectImage() {
        int idImage = isAlredySelectImageUser();
        if (idImage != user.getImagen()) {
            user.setImagen(idImage);
            preferences.edit().remove("ImageUser").commit();
            saveImage();
            return true;
        } else
            return false;

    }

    private void saveImage() {
        tableUsers.updateUser(user);

        BackgroundWorker worker = new BackgroundWorker();
        worker.execute("setImage", user.getID()+"", user.getIdImagen()+"");
        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
        } catch (InterruptedException e) {e.printStackTrace();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (JSONException e) {e.printStackTrace();}

        if(status.contains("Successfully"))
            userPerfilView.onSuccesToChangeImage();
    }

}
