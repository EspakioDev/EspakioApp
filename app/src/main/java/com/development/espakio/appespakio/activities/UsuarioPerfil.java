package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.UserPerfilPresenter;
import com.development.espakio.appespakio.view.IUserPerfilView;

public class UsuarioPerfil extends AppCompatActivity implements View.OnClickListener, IUserPerfilView{

    private TextView txtUsername;
    private ImageView imgUser, changeUser, changeImage;
    private UserPerfilPresenter perfilPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        imgUser = (ImageView) findViewById(R.id.perfil_imgUserPerfil);
        changeUser = (ImageView) findViewById(R.id.imgChangeUser);
        //changeImage = (ImageView) findViewById(R.id.imgChangeImage);

        perfilPresenter = new UserPerfilPresenter(this, getApplicationContext());
        perfilPresenter.performCharge();

    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
    }

    public void pantallaCompleta(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgChangeUser:
                goToUsersMenu();
            /*case R.id.perfil_imgUserPerfil:
                goToImagesPerfil();*/
        }
    }

    @Override
    public void chargeInfoUser(int idImage, String username) {
        txtUsername.setText(username);
        imgUser.setImageResource(idImage);
    }

    @Override
    public void goToUsersMenu() {
        startActivity(new Intent(this, MenuUsuarios.class));
    }

    @Override
    public void goToImagesPerfil() {
        startActivity(new Intent(this, ImagenPerfilUsuario.class));
    }
}
