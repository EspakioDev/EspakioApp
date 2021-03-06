package com.development.espakio.appespakio.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.GameInfo;
import com.development.espakio.appespakio.presenter.GamesMenuPresenter;
import com.development.espakio.appespakio.view.IGamesMenuView;

public class MenuJuegos extends AppCompatActivity implements View.OnClickListener, IGamesMenuView, HorizontalHabilidadAdapter.OnItemClickListenerRV{

    private ImageView btnPerfilUsuario, btnConfig;
    private GamesMenuPresenter gamesMenuPresenter;
    private Dialog desJuego;
    private int idJuego;
    private GameInfo juegos;

    private TextView txtDescripcio;
    private ImageView imgLogoJuego;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);

        btnPerfilUsuario = (ImageView) findViewById(R.id.menuJuegos_imgUserPerfil);
        btnConfig = (ImageView) findViewById(R.id.btnConfig2);

        btnConfig.setOnClickListener(this);
        btnPerfilUsuario.setOnClickListener(this);

        //desJuego = new Dialog(getApplicationContext());

        gamesMenuPresenter = new GamesMenuPresenter(this, getApplicationContext());
        gamesMenuPresenter.performGamesMenu();

        // a imgJuego y a NombresJuegos es donde debes agregarle informacion

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.listJuegos);
        recyclerView.setLayoutManager(layoutManager);
        juegos = new GameInfo();
        MyAdapterMenuJuegos adapter = new MyAdapterMenuJuegos(this, juegos.getGamesInfo());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);

        //inicializar popup

        desJuego = new Dialog(this);

        desJuego.setContentView(R.layout.descripcionjuego_popup);

        Button btnPlay = (Button)desJuego.findViewById(R.id.btnPlay);
        TextView txtCancelar = (TextView)desJuego.findViewById(R.id.txtCancelar);
        txtDescripcio = (TextView)desJuego.findViewById(R.id.txtDescJuego);
        imgLogoJuego = (ImageView) desJuego.findViewById(R.id.imglogoJuego);

        btnPlay.setOnClickListener(this);
        txtCancelar.setOnClickListener(this);
        desJuego.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
        gamesMenuPresenter.chargeImage();
    }

    private void pantallaCompleta(){
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
                startActivity(new Intent(this, Configuraciones.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.menuJuegos_imgUserPerfil:
                startActivity(new Intent(this, UsuarioPerfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.txtCancelar:
                desJuego.cancel();
                pantallaCompleta();
                break;
            case R.id.btnPlay:
                selectGame();
                desJuego.cancel();
                break;
        }
    }

    @Override
    public void getUserID(int id) {
        //Toast.makeText(MenuJuegos.this, "Usuario: " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void putUserImage(int idImage) {
        btnPerfilUsuario.setImageResource(idImage);
    }

    @Override
    public void onItemClick(int childPosition, int parentPosition) {
        //Toast.makeText(this, parentPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
        switch (parentPosition){
            case 0:
                if(childPosition == 0)
                    idJuego = 0;
                if(childPosition == 1)
                    idJuego = 1;

                break;
            case 1:
                if(childPosition == 0)
                    idJuego = 2;
                if(childPosition == 1)
                    idJuego = 3;
                if(childPosition == 2)
                    idJuego = 4;
                break;
            case 2:
                if(childPosition == 0)
                    idJuego = 5;
                if(childPosition == 1)
                    idJuego = 6;
                break;
        }
        showpopup(childPosition, parentPosition);
    }

    public void selectGame() {
        switch (idJuego) {
            case 0:
                Intent intent = new Intent(this,SplashScreen4.class);
                intent.putExtra("correctos", 1);
                startActivity(intent);
                break;
            case 1:
                Intent intent7 = new Intent(this,SplashScreen4.class);
                intent7.putExtra("correctos", 7);
                startActivity(intent7);
                break;
            case 2:
                //Toast.makeText(this, "Juego 3", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this,SplashScreen4.class);
                intent2.putExtra("correctos", 2);
                startActivity(intent2);
                break;
            case 3:
                //Toast.makeText(this, "Juego 3", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(this,SplashScreen4.class);
                intent3.putExtra("correctos", 3);
                startActivity(intent3);
                break;
            case 4:
               // Toast.makeText(this, "Juego 3", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(this,SplashScreen4.class);
                intent4.putExtra("correctos", 3);
                startActivity(intent4);
                break;
            case 5:
                //Toast.makeText(this, "Juego 3", Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(this,SplashScreen4.class);
                intent5.putExtra("correctos", 5);
                startActivity(intent5);
                break;
            case 6:
                //Toast.makeText(this, "Juego 3", Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(this,SplashScreen4.class);
                intent6.putExtra("correctos", 6);
                startActivity(intent6);
                break;

        }
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public  void showpopup(int childPosition, int parentPosition){
        GameInfo.Juego j = juegos.getGamesInfo().get(parentPosition).getNombreJuegos().get(childPosition);
        imgLogoJuego.setImageResource(j.getIdImagen());
        txtDescripcio.setText(j.getDescripcion());

        desJuego.show();
    }

}
