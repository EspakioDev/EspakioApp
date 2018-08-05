package com.development.espakio.appespakio.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.ManagerPresenter;
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
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void fullScreen() {
        //Barra Transparente y FullScreen.
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
        //
    }

    @Override
    public void goToBegin() {
        startActivity(new Intent(SplashScreen.this, SplashScreen2.class));
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
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
