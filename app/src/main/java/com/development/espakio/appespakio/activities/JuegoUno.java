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

public class JuegoUno extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mp, fondo;


    Button btnUno, btnDos;
    ImageView pausa;

    //Timer
    TextView txtTimer, txtNivel;
    CountDownTimer timer;
    long timeleftinMilliseconds = 60000;
    boolean timeRunning = true;
    //

    int niveles = 0, sec = 0;
    int numUno = 0, numDos = 0;
    int numOprA = 0, numOprA2 = 0, numOprB = 0, numOprB2 = 0;
    int correctas = 0;
    int psBandera = 0;

    LottieAnimationView animationView;

    //Pause Boton
    Dialog pausapopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_uno);

        pantallaCompleta();
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
        pausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(psBandera == 0){
                    animationView.pauseAnimation();
                    psBandera = 1;
                    timeRunning = false;
                    starStop();
                    showpopup(view);
                }else if(psBandera == 1){
                    animationView.playAnimation();
                    psBandera = 0;
                    timeRunning = true;
                    starStop();
                }

            }
        });
    }

    public void showpopup(View v){
        Button btnReanudar, btnReiniciar, btnInicio;
        pausapopup.setContentView(R.layout.pausapopup);
        btnReiniciar = (Button)pausapopup.findViewById(R.id.btnReanudar);
        btnInicio = (Button)pausapopup.findViewById(R.id.btnInicio);
        btnReanudar = (Button)pausapopup.findViewById(R.id.btnRegreso);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JuegoUno.this,SplashScreen3.class);
                startActivity(intent);
                fondo.reset();
                finish();
            }
        });
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JuegoUno.this,JuegoUno.class);
                startActivity(intent);
                fondo.reset();
                finish();
            }
        });
        btnReanudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();
                pausapopup.cancel();

            }
        });
        pausapopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pausapopup.show();

        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                pausapopup.cancel();
            };
        }, 3000);*/

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
            Intent intent = new Intent(JuegoUno.this,AfterGame.class);
            intent.putExtra("correctos", correctas);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            fondo.reset();
            finish();
        }

        txtTimer.setText(leftTime);

    }

    int numAleatorioA(int boton){
        int opc1 = (int) (Math.random() * 25) + 1;
        if(boton == 1){
            numUno = opc1;
        }else if(boton == 2){
            numDos = opc1;
        }
        return  opc1;
    }

    int numAleatorioB(int boton){
        int opc1 = (int) (Math.random() * (50 - 25 + 1)) + 25;
        if(boton == 1){
            numUno = opc1;
        }else if(boton == 2){
            numDos = opc1;
        }
        return  opc1;
    }

    int numAleatorioSuma(int ran){
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


    @Override
    public void onClick(View view) {
        mp.start();
        switch (view.getId()) {
            case R.id.btnUno:

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

                    numUno = 0;
                    numDos = 0;

                    if(niveles <= 7){
                        btnUno.setText(String.valueOf(numAleatorioA(1)));
                        btnDos.setText(String.valueOf(numAleatorioA(2)));
                    }else if(niveles>7 && niveles<=15){
                        btnUno.setText(String.valueOf(numAleatorioB(1)));
                        btnDos.setText(String.valueOf(numAleatorioB(2)));
                    }else {
                        sec = 1;
                        numOprA = numAleatorioSuma(1);
                        numOprA2 = numAleatorioSuma(1);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(1);
                        numOprB2 = numAleatorioSuma(1);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                        niveles = 0;
                    }
                    niveles++;
                } else if(sec == 1){
                    if(numOprA2 + numOprA > numOprB2 + numOprB || numOprA2 + numOprA == numOprB2 + numOprB){
                        //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                        correctas++;
                    }else{
                        //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
                    }

                    numOprA = 0;
                    numOprA2 = 0;
                    numOprB = 0;
                    numOprB2 = 0;

                    if (niveles <= 7){
                        numOprA = numAleatorioSuma(1);
                        numOprA2 = numAleatorioSuma(1);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(1);
                        numOprB2 = numAleatorioSuma(1);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }else if(niveles > 7 && niveles <= 15){
                        numOprA = numAleatorioSuma(2);
                        numOprA2 = numAleatorioSuma(2);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(2);
                        numOprB2 = numAleatorioSuma(2);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }else if(niveles > 15){
                        numOprA = numAleatorioSuma(3);
                        numOprA2 = numAleatorioSuma(3);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(3);
                        numOprB2 = numAleatorioSuma(3);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }
                    niveles++;
                }



                break;
            case R.id.btnDos:
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
                    numUno = 0;
                    numDos = 0;

                    if(niveles <= 7){
                        btnUno.setText(String.valueOf(numAleatorioA(1)));
                        btnDos.setText(String.valueOf(numAleatorioA(2)));
                    }else if(niveles>7 && niveles<=15){
                        btnUno.setText(String.valueOf(numAleatorioB(1)));
                        btnDos.setText(String.valueOf(numAleatorioB(2)));
                    }else{
                        sec = 1;
                        sec = 1;
                        numOprA = numAleatorioSuma(1);
                        numOprA2 = numAleatorioSuma(1);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(1);
                        numOprB2 = numAleatorioSuma(1);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                        niveles = 0;
                        niveles = 0;
                    }
                    niveles++;
                }else if(sec == 1){
                    if(numOprB + numOprB2 > numOprA2 + numOprA || numOprA2 + numOprA == numOprB2 + numOprB){
                        //Toast.makeText(JuegoUno.this, "Ganaste", Toast.LENGTH_SHORT).show();
                        correctas++;
                    }else{
                        //Toast.makeText(JuegoUno.this, "Perdiste", Toast.LENGTH_SHORT).show();
                    }

                    numOprA = 0;
                    numOprA2 = 0;
                    numOprB = 0;
                    numOprB2 = 0;

                    if (niveles <= 7){
                        numOprA = numAleatorioSuma(1);
                        numOprA2 = numAleatorioSuma(1);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(1);
                        numOprB2 = numAleatorioSuma(1);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }else if(niveles > 7 && niveles <= 15){
                        numOprA = numAleatorioSuma(2);
                        numOprA2 = numAleatorioSuma(2);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(2);
                        numOprB2 = numAleatorioSuma(2);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }else if(niveles > 15){
                        numOprA = numAleatorioSuma(3);
                        numOprA2 = numAleatorioSuma(3);
                        btnUno.setText(String.valueOf(numOprA) + " + " +String.valueOf(numOprA2));
                        numOprB = numAleatorioSuma(3);
                        numOprB2 = numAleatorioSuma(3);
                        btnDos.setText(String.valueOf(numOprB) + " + " +String.valueOf(numOprB2));
                    }
                    niveles++;

                }

                break;


        }
    }
}


