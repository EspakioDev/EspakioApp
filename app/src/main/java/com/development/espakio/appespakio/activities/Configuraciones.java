package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.ConfiguracionPresenter;

public class Configuraciones extends AppCompatActivity implements View.OnClickListener {

    private ImageView contacto,sonido, musica, volver;
    public Boolean son, mus;
    ConfiguracionPresenter configuracionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        configuracionPresenter = new ConfiguracionPresenter(this);
        mus = configuracionPresenter.getConfig_Musica();
        son = configuracionPresenter.getConfig_Sonido();

        sonido = (ImageView)findViewById(R.id.imgSonido);
        musica = (ImageView)findViewById(R.id.imgMusica);
        contacto = (ImageView)findViewById(R.id.imgContacto);
        volver = (ImageView)findViewById(R.id.imgVolver);

        sonido.setOnClickListener(this);
        musica.setOnClickListener(this);
        contacto.setOnClickListener(this);
        volver.setOnClickListener(this);

        if(son)            sonido.setImageResource(R.drawable.ic_sonido);
        else            sonido.setImageResource(R.drawable.ic_sonido2);

        if(mus)            musica.setImageResource(R.drawable.ic_musica);
        else            musica.setImageResource(R.drawable.ic_musica2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
    }

    private void pantallaCompleta() {
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
            case R.id.imgSonido:
                if(son==true){
                    sonido.setImageResource(R.drawable.ic_sonido2);
                    son=false;
                    configuracionPresenter.confiSonido(son);
                }
                else{
                    sonido.setImageResource(R.drawable.ic_sonido);
                    son=true;
                    configuracionPresenter.confiSonido(son);
                }
                break;

            case R.id.imgMusica:
                if(mus==true){
                    musica.setImageResource(R.drawable.ic_musica2);
                    mus=false;
                    configuracionPresenter.confiMusica(mus);
                }
                else{
                    musica.setImageResource(R.drawable.ic_musica);
                    mus=true;
                    configuracionPresenter.confiMusica(mus);
                }
                break;

            case R.id.imgContacto:
                Uri uri = Uri.parse("http://espakio.tk/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.imgVolver:
                startActivity(new Intent(this, MenuJuegos.class));
                break;
        }
    }


}
