package com.development.espakio.appespakio.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.GamePresenter;
import com.development.espakio.appespakio.view.IGameView;

public class JuegoUno extends AppCompatActivity implements View.OnClickListener, IGameView {

    private MediaPlayer mp, fondo;

    private Button btnUno, btnDos;
    private ImageView pausa;

    //Timer
    private TextView txtTimer, txtNivel;
    private CountDownTimer timer;
    private long timeleftinMilliseconds = 60000;
    private boolean timeRunning = true;
    //

    private int niveles = 0, sec = 0;
    private int numUno = 0, numDos = 0;
    private int numOprA = 0, numOprA2 = 0, numOprB = 0, numOprB2 = 0;
    private int correctas = 0;
    //private int psBandera = 0;

    private LottieAnimationView animationView;

    //Pause Boton
    private Dialog pausapopup;

    private GamePresenter gamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_uno);

        mp = MediaPlayer.create(this, R.raw.botonsound);
        fondo = MediaPlayer.create(this, R.raw.sonido_fondo);

        //Timer
        txtTimer = (TextView)findViewById(R.id.txtTimer);


        pausa = (ImageView)findViewById(R.id.imgPausa);

        btnUno = (Button)findViewById(R.id.btnUno);
        btnDos = (Button)findViewById(R.id.btnDos);

        btnUno.setOnClickListener(this);
        btnDos.setOnClickListener(this);
        btnUno.setText(String.valueOf(numAleatorioA(1)));
        btnDos.setText(String.valueOf(numAleatorioA(2)));

        //Dialog
        pausapopup = new Dialog(this);

        animationView = (LottieAnimationView)findViewById(R.id.animationTime);
        fondo.start();

        starStop();
        pausa.setOnClickListener(this);

        gamePresenter = new GamePresenter(this, getApplicationContext());
    }

    public void showpopup(View v){
        Button btnReanudar, btnReiniciar, btnInicio;
        pausapopup.setContentView(R.layout.pausapopup);
        btnReiniciar = (Button)pausapopup.findViewById(R.id.btnReanudar);
        btnInicio = (Button)pausapopup.findViewById(R.id.btnInicio);
        btnReanudar = (Button)pausapopup.findViewById(R.id.btnRegreso);
        btnInicio.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
        btnReanudar.setOnClickListener(this);
        pausapopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pausapopup.show();

        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                pausapopup.cancel();
            };
        }, 3000);*/

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

    void starStop(){
        if(timeRunning){
            starTimer();
        }else{
            stopTimer();
        }

    }

    void starTimer(){
        timer = new CountDownTimer(timeleftinMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeleftinMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timeRunning = true;

    }

    void stopTimer(){
        timer.cancel();
        timeRunning = false;

    }

    void updateTimer(){
        int segundos = (int) timeleftinMilliseconds % 60000/1000;
        String leftTime;

        leftTime = "00:";
        if(segundos < 10) leftTime += "0";
        leftTime += segundos;

        if(segundos == 1){
            gamePresenter.checkScore(correctas);
            startActivity(new Intent(this, AfterGame.class));
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            fondo.reset();
            finish();
        }

        txtTimer.setText(leftTime);

    }

    @Override
    public void onClick(View view) {
        mp.start();
        switch (view.getId()) {
            case R.id.btnUno:
                checkButtomOne();
                break;
            case R.id.btnDos:
                checkButtomTwo();
                break;
            case R.id.btnReanudar:
                startActivity(new Intent(this,JuegoUno.class));
                fondo.reset();
                finish();
                break;
            case R.id.btnInicio:
                startActivity(new Intent(this,SplashScreen3.class));
                fondo.reset();
                finish();
                break;
            case R.id.btnRegreso:
                animationView.playAnimation();
                //psBandera = 0;
                timeRunning = true;
                starStop();
                pausapopup.cancel();
                break;
            case R.id.imgPausa:
                //if(psBandera == 0){
                    animationView.pauseAnimation();
                    //psBandera = 1;
                    timeRunning = false;
                    starStop();
                    showpopup(view);
                /*}else if(psBandera == 1){
                    animationView.playAnimation();
                    psBandera = 0;
                    timeRunning = true;
                    starStop();
                }*/
                break;
        }
    }

    private int numAleatorioA(int boton){
        int opc1 = (int) (Math.random() * 25) + 1;
        if(boton == 1){
            numUno = opc1;
        }else if(boton == 2){
            numDos = opc1;
        }
        return  opc1;
    }

    private int numAleatorioB(int boton){
        int opc1 = (int) (Math.random() * (50 - 25 + 1)) + 25;
        if(boton == 1){
            numUno = opc1;
        }else if(boton == 2){
            numDos = opc1;
        }
        return  opc1;
    }

    private  int numAleatorioSuma(int ran){
        int opc1 = 0;
        if(ran == 1){
            opc1 = (int) (Math.random() * 10) + 1;
        }else if(ran == 2){
            opc1 = (int) (Math.random() * 15) + 1;
        }else if(ran == 3){
            opc1 = (int) (Math.random() * 20) + 1;
        }

        return  opc1;
    }

    private void seccUno(){
        String txtUno = "", txtDos = "";
        numUno = 0;
        numDos = 0;

        if(niveles <= 7){
            txtUno = String.valueOf(numAleatorioA(1));
            txtDos = String.valueOf(numAleatorioA(2));
        }else if(niveles>7 && niveles<=15){
            txtUno = String.valueOf(numAleatorioB(1));
            txtDos = String.valueOf(numAleatorioB(2));
        }else {
            sec = 1;
            numOprA = numAleatorioSuma(1);
            numOprA2 = numAleatorioSuma(1);
            txtUno = String.valueOf(numOprA) + " + " +String.valueOf(numOprA2);
            numOprB = numAleatorioSuma(1);
            numOprB2 = numAleatorioSuma(1);
            txtDos = String.valueOf(numOprB) + " + " +String.valueOf(numOprB2);
            niveles = 0;
        }
        btnUno.setText(txtUno);
        btnDos.setText(txtDos);
    }

    private void seccDos(){
        numOprA = 0;
        numOprA2 = 0;
        numOprB = 0;
        numOprB2 = 0;

        if (niveles <= 7){
            numOprA = numAleatorioSuma(1);
            numOprA2 = numAleatorioSuma(1);

            numOprB = numAleatorioSuma(1);
            numOprB2 = numAleatorioSuma(1);

        }else if(niveles > 7 && niveles <= 15){
            numOprA = numAleatorioSuma(2);
            numOprA2 = numAleatorioSuma(2);

            numOprB = numAleatorioSuma(2);
            numOprB2 = numAleatorioSuma(2);

        }else if(niveles > 15){
            numOprA = numAleatorioSuma(3);
            numOprA2 = numAleatorioSuma(3);

            numOprB = numAleatorioSuma(3);
            numOprB2 = numAleatorioSuma(3);

        }
        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
    }

    private void checkButtomOne() {
        if(sec == 0){
            if(numUno > numDos || numUno == numDos){
                //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                correctas++;
            }else {
                //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
                if(niveles<=7){
                    niveles = 0;
                }else if(niveles > 7 && niveles <= 15){
                    niveles = 8;
                }
            }
            seccUno();

        } else if(sec == 1){
            if(numOprA2 + numOprA > numOprB2 + numOprB || numOprA2 + numOprA == numOprB2 + numOprB){
                //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                correctas++;
            }else{
                //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
            }

            seccDos();

        }
        niveles++;
    }

    private void checkButtomTwo() {
        if(sec == 0){
            if(numDos > numUno || numDos == numUno){
                //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                correctas++;
            }else {
                //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
                if(niveles<=7){
                    niveles = 0;
                }else if(niveles > 7 && niveles <= 15){
                    niveles = 8;
                }
            }
            seccUno();
        }else if(sec == 1){
            if(numOprB + numOprB2 > numOprA2 + numOprA || numOprA2 + numOprA == numOprB2 + numOprB){
                //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                correctas++;
            }else{
                //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
            }

            seccDos();
        }
        niveles++;
    }

}


