package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.presenter.GetUsersPresenter;
import com.development.espakio.appespakio.view.IGetUsersView;

import java.util.Vector;

public class MenuUsuarios extends AppCompatActivity implements View.OnClickListener, IGetUsersView{

    private ImageView btnUsuarioUno, btnUsuarioDos, btnUsuarioTres, btnUsuarioPlus;
    private TextView txtUsuarioUno, txtUsuarioDos, txtUsuarioTres;
    private Button btnConfig;
    private GetUsersPresenter getUsersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_usuarios);

        btnUsuarioUno = (ImageView) findViewById(R.id.menuUsu_imgUsuarioUno);
        btnUsuarioUno.setOnClickListener(this);

        btnUsuarioDos = (ImageView) findViewById(R.id.menuUsu_imgUsuarioDos);
        btnUsuarioDos.setOnClickListener(this);

        btnUsuarioTres = (ImageView) findViewById(R.id.menuUsu_imgUsuarioTres);
        btnUsuarioTres.setOnClickListener(this);

        btnUsuarioPlus = (ImageView) findViewById(R.id.menuUsu_imgUsuarioPlus);
        btnUsuarioPlus.setOnClickListener(this);

        txtUsuarioUno = (TextView) findViewById(R.id.menuUsu_txtUsernameUno);
        txtUsuarioUno.setOnClickListener(this);

        txtUsuarioDos = (TextView) findViewById(R.id.menuUsu_txtUsernameDos);
        txtUsuarioDos.setOnClickListener(this);

        txtUsuarioTres = (TextView) findViewById(R.id.menuUsu_txtUsernameTres);
        txtUsuarioTres.setOnClickListener(this);

        btnConfig = (Button) findViewById(R.id.menuUsu_btnConfig);
        btnConfig.setOnClickListener(this);

        getUsersPresenter = new GetUsersPresenter(this, getApplicationContext());
        //getUsersPresenter.performGetUsers();

    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
    }

    void pantallaCompleta(){
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
        switch (view.getId()) {
            case R.id.menuUsu_imgUsuarioPlus:
                getUsersPresenter.selectUser(-1);
                break;
            case R.id.menuUsu_imgUsuarioUno:
            case R.id.menuUsu_txtUsernameUno:
                getUsersPresenter.selectUser(0);
                break;
            case R.id.menuUsu_imgUsuarioDos:
            case R.id.menuUsu_txtUsernameDos:
                getUsersPresenter.selectUser(1);
                break;
            case R.id.menuUsu_imgUsuarioTres:
            case R.id.menuUsu_txtUsernameTres:
                getUsersPresenter.selectUser(2);
                break;
            case R.id.menuUsu_btnConfig:
                goToConfiguration();
                break;
        }

    }

       /*Falta insertar los usuarios cuando es por primera vez en la db interna
       actualizar usuarios en el host
       Eliminar cuando desee salir
       */

    @Override
    public void chargeUsers(Vector<Usuario> users) {
        if(users.size() < 3)
            btnUsuarioPlus.setVisibility(View.VISIBLE);

        //Aqui puedes checar el id de la imagen
        //int id = R.drawable.img_usuario_verde;

        switch (users.size()) {
            case 3:
                txtUsuarioTres.setVisibility(View.VISIBLE);
                btnUsuarioTres.setVisibility(View.VISIBLE);
                txtUsuarioTres.setText(users.get(2).getNickName());
                btnUsuarioTres.setImageResource(users.get(2).getImagen());
            case 2:
                txtUsuarioDos.setVisibility(View.VISIBLE);
                btnUsuarioDos.setVisibility(View.VISIBLE);
                txtUsuarioDos.setText(users.get(1).getNickName());
                btnUsuarioDos.setImageResource(users.get(1).getImagen());
            case 1:
                txtUsuarioUno.setVisibility(View.VISIBLE);
                btnUsuarioUno.setVisibility(View.VISIBLE);
                txtUsuarioUno.setText(users.get(0).getNickName());
                btnUsuarioUno.setImageResource(users.get(0).getImagen());

                break;
            default:
                Toast.makeText(MenuUsuarios.this, "HUBO UN ERROR CON LOS USUARIOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToGameMenu() {
        Intent intent = new Intent(MenuUsuarios.this, MenuJuegos.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

        finish();
    }

    public void goToConfiguration() {
        startActivity(new Intent(MenuUsuarios.this, Configuraciones.class));
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void goToNewUser() {
        startActivity(new Intent(MenuUsuarios.this, NuevoUsuario.class));
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

}

