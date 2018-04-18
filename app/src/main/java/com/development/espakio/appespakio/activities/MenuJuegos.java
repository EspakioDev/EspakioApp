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
import com.development.espakio.appespakio.model.GamesMenuPresenter;
import com.development.espakio.appespakio.model.Usuario;
import com.development.espakio.appespakio.view.IGamesMenuView;

public class MenuJuegos extends AppCompatActivity implements View.OnClickListener, IGamesMenuView{

    private ImageView btnJuegoUno,btnJuegoDos,btnJuegoTres;
    private Button btnConfig, btnPerfilUsuario;
    private GamesMenuPresenter gamesMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);

        btnJuegoUno = (ImageView) findViewById(R.id.imgJuegoUno);
        btnJuegoDos = (ImageView) findViewById(R.id.imgJuegoDos);
        btnJuegoTres = (ImageView) findViewById(R.id.imgJuegoTres);
        btnPerfilUsuario = (Button) findViewById(R.id.btnPerfilUsuario);
        btnConfig = (Button) findViewById(R.id.btnConfig2);

        btnConfig.setOnClickListener(this);
        btnJuegoUno.setOnClickListener(this);
        btnJuegoDos.setOnClickListener(this);
        btnJuegoTres.setOnClickListener(this);
        btnPerfilUsuario.setOnClickListener(this);

        gamesMenuPresenter = new GamesMenuPresenter(MenuJuegos.this, getApplicationContext());
        gamesMenuPresenter.performGamesMenu();

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
            case R.id.imgJuegoUno:
                startActivity(new Intent(MenuJuegos.this, DescripcionJuego.class));
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                break;
            case R.id.imgJuegoDos:
                startActivity(new Intent(MenuJuegos.this, DescripcionJuego.class));
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                break;
            case R.id.imgJuegoTres:
                startActivity(new Intent(MenuJuegos.this, DescripcionJuego.class));
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                break;
            case R.id.btnConfig2:
                startActivity(new Intent(MenuJuegos.this, Configuraciones.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.btnPerfilUsuario:
                startActivity(new Intent(MenuJuegos.this, PerfilUsuario.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
        }
    }

    @Override
    public void getUserID(int id) {
        Toast.makeText(MenuJuegos.this, "Usuario: " + id, Toast.LENGTH_SHORT).show();
    }
}
