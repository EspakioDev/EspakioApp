package com.development.espakio.appespakio.activities;
import com.development.espakio.appespakio.R;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;

public class JuegoUno extends AppCompatActivity implements View.OnClickListener {

    //Nombre del Juego
    int juego = 1;
    //
    //Musica
    MediaPlayer msIncorrecto, msCorrecto, fondo, fondo2, fondo3,fondo4, go, nivelPasado;

    //

    //Interfaz
    Button btnUno, btnDos, btnSame;
    ImageView pausa;
    Animation uptodown, downtoup;
    TextView txtPregunta;
    RelativeLayout l1, l2, l3;
    //

    //Timer
    TextView txtTimer, txtNivel;
    CountDownTimer timer;
    long timeleftinMilliseconds = 60000;
    boolean timeRunning = true;
    //

    //Juego
    int nivel = 1, sec = 0;
    int numUno = 0, numDos = 0;
    int numOprA = 0, numOprA2 = 0, numOprB = 0, numOprB2 = 0;
    int points = 0;
    int psBandera = 0, revDialog = 0;
    boolean notsame = false;
    //

    //Animacion Timer
    LottieAnimationView animationView;

    //Popups
    Dialog pausapopup, inicioJuego, nivelAnim;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_uno);
        pantallaCompleta();

        //Musica del Juego
        msIncorrecto = MediaPlayer.create(this, R.raw.botonsound);
        msCorrecto = MediaPlayer.create(this, R.raw.correct);
        fondo = MediaPlayer.create(this, R.raw.sonido_fondo);
        fondo2 = MediaPlayer.create(this, R.raw.sonido_fondo_niveldos);
        fondo3 = MediaPlayer.create(this, R.raw.sonido_fondo_niveltres);
        fondo4 = MediaPlayer.create(this, R.raw.sonido_fondo_nivelfinal);
        nivelPasado = MediaPlayer.create(this, R.raw.nuevo_nivel);
        go = MediaPlayer.create(this, R.raw.go);


        //


        //Timer
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        animationView = (LottieAnimationView)findViewById(R.id.animationTime);


        //Interfaz
        pausa = (ImageView)findViewById(R.id.imgPausa);
        btnUno = (Button)findViewById(R.id.btnUno);
        btnDos = (Button)findViewById(R.id.btnDos);
        btnSame = (Button)findViewById(R.id.btnSame);
        txtPregunta = (TextView)findViewById(R.id.txtPregunta);

        btnUno.setOnClickListener(this);
        btnDos.setOnClickListener(this);
        btnSame.setOnClickListener(this);
        btnSame.setEnabled(false);
        //



        //Animacion Interfaz
        l1 = (RelativeLayout) findViewById(R.id.rlHead);
        l2 = (RelativeLayout) findViewById(R.id.rlBotones);
        l3 = (RelativeLayout) findViewById(R.id.rlPregunta);

        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        btnSame.setVisibility(View.GONE);



        //Dialog
        pausapopup = new Dialog(this);
        inicioJuego = new Dialog(this);
        nivelAnim = new Dialog(this);

        //PopUp Intro Juego
        showpopupdos(new View(this));

        //  Pos Animacion

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        //


        //Juego
        procesoAleatorio();





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
                //Intent intent = new Intent(JuegoUno.this,MenuJuegos.class);
                //startActivity(intent);
                fondo.reset();
                fondo2.reset();
                fondo3.reset();
                fondo4.reset();
                finish();
            }
        });
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JuegoUno.this,JuegoUno.class);
                startActivity(intent);
                fondo.reset();
                fondo2.reset();
                fondo3.reset();
                fondo4.reset();
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

    public void showpopupdos(View v){

        go.start();
        inicioJuego.setContentView(R.layout.inicio_juegos);
        inicioJuego.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inicioJuego.show();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
                l1.setAnimation(uptodown);
                l2.setAnimation(downtoup);
                l3.setAnimation(uptodown);
                inicioJuego.cancel();
                animationView.playAnimation();
                go.pause();
                starStop();
                fondo.start();
            };
        }, 6000);

    }

    public void showpopupNiveles(View v){

        //Detenemos Tiempo
        animationView.pauseAnimation();
        psBandera = 1;
        timeRunning = false;
        starStop();
        //


        nivelPasado.start();
        if(nivel == 1){
            //Detenemos Musica
            fondo.reset();
            //
            nivelAnim.setContentView(R.layout.nivel_uno_superado);
        } else if (nivel == 2){
            //Detenemos Musica
            fondo2.reset();
            //
            nivelAnim.setContentView(R.layout.nivel_dos_superado);
        }else if(nivel == 3){
            //Detenemos Musica
            fondo3.stop();
            txtPregunta.setText("¿Cual de las dos sumas dan el resultado mas grande?");
            //
            nivelAnim.setContentView(R.layout.nivel_tres_superado);
        }

        nivelAnim.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nivelAnim.show();

        new Handler().postDelayed(new Runnable(){
            public void run(){

                //Reanudamos el Tiempo
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();
                //
                if(nivel == 1){
                    fondo2.start();
                } else if (nivel == 2){
                    fondo3.start();
                } else if(nivel == 3){
                    fondo4.start();
                }
                nivelAnim.cancel();
            };
        }, 3000);
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
            intent.putExtra("correctos", points);
            intent.putExtra("juego", juego);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            fondo.reset();
            fondo2.reset();
            fondo3.reset();
            fondo4.reset();
            finish();
        }

        txtTimer.setText(leftTime);

    }

    int numAleatorioA(){
        int opc1 = (int) (Math.random() * 100) + 1;
        return  opc1;
    }

    int numAleatorioB(){
        int opc1 = (int) (Math.random() * (150 - 100 + 1)) + 100;
        return  opc1;
    }

    int numAleatorioSuma(){
        int  opc1 = (int) (Math.random() * 10) + 1;
        return  opc1;
    }

    void procesoAleatorio(){

        do{
            if(nivel == 1){
                numUno = numAleatorioA();
                numDos = numAleatorioA();
                if(numUno != numDos){
                    btnUno.setText(String.valueOf(numUno));
                    btnDos.setText(String.valueOf(numDos));
                    notsame = true;
                    break;
                }
            }else if (nivel == 2){
                numUno = numAleatorioB();
                numDos = numAleatorioB();
                if(numUno != numDos){
                    btnUno.setText(String.valueOf(numUno));
                    btnDos.setText(String.valueOf(numDos));
                    notsame = true;
                    break;
                }

            }else if(nivel == 3){
                numOprA = numAleatorioSuma();
                numOprA2 = numAleatorioSuma();
                numOprB = numAleatorioSuma();
                numOprB2 = numAleatorioSuma();
                btnUno.setText(numOprA + " + " + numOprA2);
                btnDos.setText(numOprB + " + " + numOprB2);
                notsame = true;
                break;
            }

        }while (notsame == false);

    }

    void revisarNievel(){
        if (points == 5 && revDialog == 0){
            nivel = 1;
            revDialog = 1;
            showpopupNiveles(new View(this));
        }else if(points == 10 && revDialog == 1){
            nivel = 2;
            revDialog = 2;
            showpopupNiveles(new View(this));
        }
        else if(points == 15 && revDialog == 2){

            nivel = 3;
            showpopupNiveles(new View(this));
            btnSame.setVisibility(new View(this).VISIBLE);
            btnSame.setEnabled(true);

        }
    }


    @Override
    public void onClick(View view) {

        revisarNievel();
        switch (view.getId()) {
            case R.id.btnUno:
                if(nivel == 1 || nivel == 2){
                    if(numUno > numDos){
                        msCorrecto.start();
                        points++;
                        numUno = 0;
                        numDos = 0;
                        procesoAleatorio();
                    }else if(numUno < numDos){
                        msIncorrecto.start();
                        numUno = 0;
                        numDos = 0;
                        procesoAleatorio();
                    }
                }else if(nivel == 3) {
                    if (numOprA + numOprA2 > numOprB + numOprB2) {
                        msCorrecto.start();
                        points++;
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    } else if (numOprA + numOprA2 < numOprB + numOprB2) {
                        msIncorrecto.start();
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    } else if ((numOprA + numOprA2) == (numOprB + numOprB2)) {
                        msIncorrecto.start();
                        points++;
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    }
                }
                break;
            case R.id.btnDos:
                if(nivel == 1 || nivel == 2){
                    if(numUno < numDos){
                        msCorrecto.start();
                        points++;
                        numUno = 0;
                        numDos = 0;
                        procesoAleatorio();
                    }else if(numUno > numDos){
                        msIncorrecto.start();
                        numUno = 0;
                        numDos = 0;
                        procesoAleatorio();
                    }
                }else if(nivel == 3) {
                    if (numOprA + numOprA2 < numOprB + numOprB2) {
                        msCorrecto.start();
                        points++;
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    } else if (numOprA + numOprA2 > numOprB + numOprB2) {
                        msIncorrecto.start();
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    } else if ((numOprA + numOprA2) == (numOprB + numOprB2)) {
                        msIncorrecto.start();
                        points++;
                        numOprA = 0;
                        numOprA2 = 0;
                        numOprB = 0;
                        numOprB2 = 0;
                        procesoAleatorio();
                    }
                }
                break;
            case R.id.btnSame:
                if ((numOprA + numOprA2) == (numOprB + numOprB2)){
                    msCorrecto.start();
                    points++;
                    numOprA = 0;
                    numOprA2 = 0;
                    numOprB = 0;
                    numOprB2 = 0;
                    procesoAleatorio();
                }else {
                    msIncorrecto.start();
                    numOprA = 0;
                    numOprA2 = 0;
                    numOprB = 0;
                    numOprB2 = 0;
                    procesoAleatorio();
                }

                break;


        }
    }
}
