package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.db.tblAvance;
import com.development.espakio.appespakio.db.tblUsuarios;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.IUserPerfilView;

public class UserPerfilPresenter implements IUserPerfilPresenter{

    private IUserPerfilView userPerfilView;
    private Context context;
    private tblUsuarios tableUsers;
    private tblAvance tableProgress;
    private Usuario user;
    //private SharedPreferences preferences;

    public UserPerfilPresenter(IUserPerfilView userPerfilView, Context context) {
        //this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        tableUsers = new tblUsuarios(context);
        tableProgress = new tblAvance(context);
        user = tableUsers.getUserSelec();
        this.context = context;
        this.userPerfilView = userPerfilView;
    }

    @Override
    public void performCharge() {
        userPerfilView.chargeInfoUser(user.getImagen(), user.getNickName());
    }

}
