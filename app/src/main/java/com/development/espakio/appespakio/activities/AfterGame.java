package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.view.IAfterGameView;

public class AfterGame extends AppCompatActivity implements View.OnClickListener, IAfterGameView{

    private MediaPlayer mp;
    private Button btnAceptar;
    private TextView txtCorrectas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game);

        mp = MediaPlayer.create(this, R.raw.win);

        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtCorrectas = (TextView) findViewById(R.id.txtCorrectas);
        //Bundle bundle = getIntent().getExtras();
        txtCorrectas.setText("Algo");
        //txtCorrectas.setText(String.valueOf(bundle.getInt("correctos")));
        mp.start();

        btnAceptar.setOnClickListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    void pantallaCompleta() {
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
            case R.id.btnAceptar:
                startActivity( new Intent(AfterGame.this, MenuJuegos.class));
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                finish();
                break;
        }

    }

    @Override
    public void chargeValues(int scoreMax) {

    }
}
