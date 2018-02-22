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

import com.development.espakio.appespakio.R;

public class MenuJuegos extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnJuegoUno,btnJuegoDos,btnJuegoTres;
    private Button btnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_menu_juegos);

        btnJuegoUno = (ImageView) findViewById(R.id.imgJuegoUno);
        btnJuegoDos = (ImageView) findViewById(R.id.imgJuegoDos);
        btnJuegoTres = (ImageView) findViewById(R.id.imgJuegoTres);
        btnConfig = (Button) findViewById(R.id.btnConfig);

        btnConfig.setOnClickListener(this);
        btnJuegoUno.setOnClickListener(this);
        btnJuegoDos.setOnClickListener(this);
        btnJuegoTres.setOnClickListener(this);

        changeStatusBarColor();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
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
            case R.id.btnConfig:
                startActivity(new Intent(MenuJuegos.this, Configuraciones.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
        }
    }
}
