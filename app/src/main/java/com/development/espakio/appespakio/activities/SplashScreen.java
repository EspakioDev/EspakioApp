package com.development.espakio.appespakio.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Bundle;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.ManagerPresenter;
import com.development.espakio.appespakio.view.IManagerView;

public class SplashScreen extends Activity implements IManagerView {

    private ManagerPresenter managerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        managerPresenter = new ManagerPresenter(SplashScreen.this, getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                managerPresenter.performOpening();
            }
        },4000);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void goToBegin() {
        startActivity(new Intent(SplashScreen.this, Inicio.class));
        finish();
    }

    @Override
    public void goToWelcome() {
        startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void goToUsersMenu() {
        startActivity(new Intent(SplashScreen.this, MenuUsuarios.class));
        finish();
    }

    @Override
    public void goToGamesMenu() {
        startActivity(new Intent(SplashScreen.this, MenuJuegos.class));
        finish();
    }
}
