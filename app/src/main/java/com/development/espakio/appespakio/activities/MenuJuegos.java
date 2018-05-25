package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.GamesMenuPresenter;
import com.development.espakio.appespakio.view.IGamesMenuView;

import java.util.ArrayList;

public class MenuJuegos extends AppCompatActivity implements View.OnClickListener, IGamesMenuView{


    private ImageView btnPerfilUsuario, btnConfig;
    private GamesMenuPresenter gamesMenuPresenter;

    private int[] imgJuegos;
    private ArrayList<String> nombreJuegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);
        pantallaCompleta();


        btnPerfilUsuario = (ImageView) findViewById(R.id.btnPerfilUsuario);
        btnConfig = (ImageView) findViewById(R.id.btnConfig2);

        btnConfig.setOnClickListener(this);
        btnPerfilUsuario.setOnClickListener(this);
        gamesMenuPresenter = new GamesMenuPresenter(MenuJuegos.this, getApplicationContext());
        gamesMenuPresenter.performGamesMenu();

        // a imgJuego y a NombresJuegos es donde debes agregarle informacion


        imgJuegos = new int[]{
                R.drawable.img_juego_uno,
                R.drawable.img_luna,
                R.drawable.img_luna
        };

        nombreJuegos.add("Juego Uno");
        nombreJuegos.add("Juego Dos");
        nombreJuegos.add("Juego Tres");

        //recuerda que deben ser del mismo tamaño

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.listUno);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapterMenuJuegos adapter = new MyAdapterMenuJuegos(this, imgJuegos, nombreJuegos, 1);
        recyclerView.setAdapter(adapter);

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

            case R.id.btnConfig2:
                startActivity(new Intent(MenuJuegos.this, Configuraciones.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.btnPerfilUsuario:
                startActivity(new Intent(MenuJuegos.this, UsuarioPerfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
           /* default:
                setGameSelect(view.getId());
                startActivity(new Intent(MenuJuegos.this, DescripcionJuego.class));
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);*/
        }
    }

    @Override
    public void getUserID(int id) {
        Toast.makeText(MenuJuegos.this, "Usuario: " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getHabilitiesGames(String[] Habilities) {
        /*txtHabilidadUno.setText(Habilities[0]);
        txtHabilidadDos.setText(Habilities[1]);*/
    }

    @Override
    public void setGameSelect(int id) {
        /*int iGameSelect;
        switch (id) {
            case R.id.imgJuego11:
                iGameSelect = 0;
                break;
            case R.id.imgJuego12:
                iGameSelect = 1;
                break;
            case R.id.imgJuego13:
                iGameSelect = 2;
                break;
            case R.id.imgJuego21:
                iGameSelect = 3;
                break;
            case R.id.imgJuego22:
                iGameSelect = 4;
                break;
            case R.id.imgJuego23:
                iGameSelect = 5;
                break;
            default:
                iGameSelect = -1;
        }
        gamesMenuPresenter.putGameSelectPref(iGameSelect);*/
    }


}
