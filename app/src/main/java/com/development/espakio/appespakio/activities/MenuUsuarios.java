package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.Cliente;
import com.development.espakio.appespakio.model.GetUsersPresenter;
import com.development.espakio.appespakio.view.IGetUsersView;

public class MenuUsuarios extends AppCompatActivity implements View.OnClickListener, IGetUsersView{

    private ImageView btnUsuarioUno, btnUsuarioDos, btnUsuarioTres, btnUsuarioPlus;
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

        btnConfig = (Button) findViewById(R.id.menuUsu_btnConfig);
        btnConfig.setOnClickListener(this);

        getUsersPresenter = new GetUsersPresenter(MenuUsuarios.this, getApplicationContext());
        getUsersPresenter.performGetUsers();

        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuUsu_imgUsuarioPlus:
                goToNewUser();
                break;
            case R.id.menuUsu_imgUsuarioUno:
                getUsersPresenter.selectUser(0);
                goToGameMenu();
                break;
            case R.id.menuUsu_imgUsuarioDos:
                getUsersPresenter.selectUser(1);
                goToGameMenu();
                break;
            case R.id.menuUsu_imgUsuarioTres:
                getUsersPresenter.selectUser(2);
                goToGameMenu();
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
    public void getUsers(int sizeUsuarios) {
        switch (sizeUsuarios) {
            case 0:
                btnUsuarioUno.setVisibility(View.INVISIBLE);
                btnUsuarioDos.setVisibility(View.INVISIBLE);
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 1:
                btnUsuarioDos.setVisibility(View.INVISIBLE);
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 3:
                btnUsuarioPlus.setVisibility(View.INVISIBLE);
                break;
            default:
                Toast.makeText(MenuUsuarios.this, "HUBO UN ERROR CON LOS USUARIOS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUsersIDs(String ids) {
        Toast.makeText(MenuUsuarios.this, ids, Toast.LENGTH_SHORT).show();
    }

    private void goToGameMenu() {
        Intent intent = new Intent(MenuUsuarios.this, MenuJuegos.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }

    private void goToConfiguration() {
        startActivity(new Intent(MenuUsuarios.this, Configuraciones.class));
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    private void goToNewUser() {
        startActivity(new Intent(MenuUsuarios.this, NuevoUsuario.class));
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

}
