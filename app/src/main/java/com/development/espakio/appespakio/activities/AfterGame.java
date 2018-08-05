package com.development.espakio.appespakio.activities;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.AfterGamePresenter;
import com.development.espakio.appespakio.view.IAfterGameView;

public class AfterGame extends AppCompatActivity implements View.OnClickListener, IAfterGameView {

    private MediaPlayer mp;
    private Button btnAceptar;
    private TextView txtCorrectas, txtMaxPun;
    private int correct = 0;
    private int nomJuego = 0;
    private AfterGamePresenter afterGamePresenter;
    //Animacion Interfaz
    private Animation uptodown, downtoup;
    private RelativeLayout rlTrofeo, rlFelicidades, rlPuntajes, rlBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        pantallaCompleta();
        setContentView(R.layout.activity_after_game);


        mp = MediaPlayer.create(this, R.raw.win);

        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        txtCorrectas = (TextView)findViewById(R.id.txtCorrectas);
        txtMaxPun = (TextView)findViewById(R.id.txtMaxPun);

        // Relatives Layouts Animations
        rlTrofeo = (RelativeLayout) findViewById(R.id.rlTrofeo);
        rlFelicidades = (RelativeLayout) findViewById(R.id.rlFelicidades);
        rlPuntajes = (RelativeLayout) findViewById(R.id.rlPuntajes);
        rlBoton = (RelativeLayout) findViewById(R.id.rlBoton);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        rlTrofeo.setAnimation(uptodown);
        rlFelicidades.setAnimation(uptodown);
        rlPuntajes.setAnimation(downtoup);
        rlBoton.setAnimation(downtoup);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            correct = bundle.getInt("correctos");
            nomJuego = bundle.getInt("juego");
        }
          // ------------- DE QUE JUEGO VIENE  -------------------
        txtCorrectas.setText(correct + "");

        mp.start();

        btnAceptar.setOnClickListener(this);
        afterGamePresenter = new AfterGamePresenter(this, getApplicationContext());
        afterGamePresenter.checkScore(nomJuego, correct);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
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
            case R.id.btnAceptar:
                //startActivity( new Intent(AfterGame.this, MenuJuegos.class));
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                finish();
                break;
        }
    }

    @Override
    public void chargeValues(int scoreMax) {
        txtMaxPun.setText(scoreMax+"");
    }

    @Override
    public void newScore() {
        Toast.makeText(this, "Superaste tu puntuación!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newAward(int logro) {
        Toast.makeText(this, "Lograste nivel " + logro, Toast.LENGTH_SHORT).show();
    }

}
