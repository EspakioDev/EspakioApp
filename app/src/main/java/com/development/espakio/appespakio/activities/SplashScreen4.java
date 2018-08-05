package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.development.espakio.appespakio.R;

public class SplashScreen4 extends AppCompatActivity {

    int numJuego = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        numJuego = bundle.getInt("correctos");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(numJuego == 1){
                    startActivity(new Intent(SplashScreen4.this, JuegoSiete.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }else if(numJuego == 2){
                    startActivity(new Intent(SplashScreen4.this, JuegoTres.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                } else if (numJuego == 3) {
                    startActivity(new Intent(SplashScreen4.this, JuegoDos.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }else if (numJuego == 4){
                    startActivity(new Intent(SplashScreen4.this, JuegoSeis.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }else if(numJuego == 5){
                    startActivity(new Intent(SplashScreen4.this, JuegoUno.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }else if(numJuego == 6){
                    startActivity(new Intent(SplashScreen4.this, JuegoCinco.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }else if(numJuego == 7){
                    startActivity(new Intent(SplashScreen4.this, JuegoOcho.class));
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }



            }
        },4000);

        setContentView(R.layout.activity_splash_screen4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void fullScreen() {
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
}
