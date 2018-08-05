package com.development.espakio.appespakio.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.ConfiguracionPresenter;


public class JuegoSeis extends AppCompatActivity implements View.OnClickListener{


    //Interfaz del Juego
    TextView txtColor, txtEnunciado;
    ImageView btnPausa,imgColor, btnUno, btnDos, imgCambio;
    Button btnColor, btnFigura, btnNada;
    //

    //Animacion Interfaz
    Animation uptodown, downtoup;
    RelativeLayout head, headPregunta, Body, Buttom;
    //

    //Timer
    TextView txtTimer, txtNivel;
    CountDownTimer timer;
    long timeleftinMilliseconds = 60000;
    boolean timeRunning = true;
    int psBandera = 0;
    LottieAnimationView animationView, animationView2; //Lottie Time Animation
    //

    //Musica
    boolean conMusica, conSonido;
    ConfiguracionPresenter configuracionPresenter;
    MediaPlayer msIncorrecto, msCorrecto, fondo, fondo2, fondo3,fondo4, go, nivelPasado;
    //

    //Dialogs
    Dialog pausapopup, inicioJuegopopup, nivelAnim;
    //


    //Juego
    int[] img = {R.drawable.ic_insignia011, R.drawable.ic_insignia012, R.drawable.ic_insignia013, R.drawable.ic_insignia014, R.drawable.ic_insignia015,
            R.drawable.ic_planetaamarillo, R.drawable.ic_planetaazul, R.drawable.ic_planetagris, R.drawable.ic_planetarojo, R.drawable.ic_planetaverde};
    int points = 0, nivel = 0, actual = 0, anterio = 0, idJuego = 4;
    boolean Prube = true;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pantallaCompleta();
        setContentView(R.layout.activity_juego_seis);



        //Interfaz
        btnPausa = (ImageView)findViewById(R.id.imgPausa);
        txtEnunciado = (TextView) findViewById(R.id.txtEnunciado);
        imgCambio = (ImageView) findViewById(R.id.imgPicture);
        btnColor =  (Button) findViewById(R.id.btnUno);
        btnFigura = (Button) findViewById(R.id.btnDos);
        btnNada = (Button) findViewById(R.id.btnTres);
        //

        btnColor.setOnClickListener(this);
        btnFigura.setOnClickListener(this);
        btnNada.setOnClickListener(this);

        //Animacion Interfaz
        head = (RelativeLayout) findViewById(R.id.rlHead);
        headPregunta = (RelativeLayout) findViewById(R.id.rlHeadDos);
        Body = (RelativeLayout) findViewById(R.id.txtDesc);
        Buttom = (RelativeLayout) findViewById(R.id.rlButtom);

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        head.setVisibility(View.GONE);
        headPregunta.setVisibility(View.GONE);
        Body.setVisibility(View.GONE);
        Buttom.setVisibility(View.GONE);

        //

        //Timer
        animationView = (LottieAnimationView)findViewById(R.id.animationTime);
        animationView2 = (LottieAnimationView)findViewById(R.id.transicion);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        //

        //Dialogos
        pausapopup = new Dialog(this);
        inicioJuegopopup = new Dialog(this);
        nivelAnim = new Dialog(this);
        //

        //Configuracion
        configuracionPresenter = new ConfiguracionPresenter(this);
        conMusica = configuracionPresenter.getConfig_Musica();
        conSonido = configuracionPresenter.getConfig_Sonido();

        //Musica
        msIncorrecto = MediaPlayer.create(this, R.raw.botonsound);
        msCorrecto = MediaPlayer.create(this, R.raw.correct);
        fondo = MediaPlayer.create(this, R.raw.sonido_fondo);
        fondo2 = MediaPlayer.create(this, R.raw.sonido_fondo_niveldos);
        fondo3 = MediaPlayer.create(this, R.raw.sonido_fondo_niveltres);
        fondo4 = MediaPlayer.create(this, R.raw.sonido_fondo_nivelfinal);
        nivelPasado = MediaPlayer.create(this, R.raw.nuevo_nivel);
        go = MediaPlayer.create(this, R.raw.go);
        //

        showpopupdos(new View(this));


        btnPausa.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(JuegoSeis.this,MenuJuegos.class);
        startActivity(intent);
        fondo.reset();
        fondo2.reset();
        fondo3.reset();
        fondo4.reset();
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(psBandera == 0){
            animationView.pauseAnimation();
            psBandera = 1;
            timeRunning = false;
            starStop();
            showpopup(new View(this));
        }else if(psBandera == 1){
            animationView.playAnimation();
            psBandera = 0;
            timeRunning = true;
            starStop();
        }

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

    public void showpopup(View v){
        Button btnReanudar, btnReiniciar, btnInicio;
        pausapopup.setContentView(R.layout.pausapopup);
        btnReiniciar = (Button)pausapopup.findViewById(R.id.btnReanudar);
        btnInicio = (Button)pausapopup.findViewById(R.id.btnInicio);
        btnReanudar = (Button)pausapopup.findViewById(R.id.btnRegreso);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prube = false;
                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //
                //Intent intent = new Intent(JuegoSeis.this,MenuJuegos.class);
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
                Prube = false;
                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //
                Intent intent = new Intent(JuegoSeis.this,JuegoSiete.class);
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

    //Intro Juego
    public void showpopupdos(View v){

        new Handler().postDelayed(new Runnable(){
            public void run(){

                inicioJuegopopup.setContentView(R.layout.inicio_juegos);
                inicioJuegopopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                inicioJuegopopup.show();
                if(conSonido)
                    go.start();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        head.setVisibility(View.VISIBLE);
                        headPregunta.setVisibility(View.VISIBLE);
                        Body.setVisibility(View.VISIBLE);
                        Buttom.setVisibility(View.VISIBLE);

                        head.setAnimation(uptodown);
                        headPregunta.setAnimation(uptodown);
                        Body.setAnimation(downtoup);
                        Buttom.setAnimation(downtoup);
                        inicioJuegopopup.cancel();
                        animationView.playAnimation();
                        go.pause();
                        starStop();
                        starGame();
                        if(conMusica)
                            fondo.start();
                        pantallaCompleta();
                    };
                }, 6000);
            };
        }, 2000);


    }

    public void showpopupNiveles(View v){

        //Detenemos Tiempo
        animationView.pauseAnimation();
        psBandera = 1;
        timeRunning = false;
        starStop();
        //

        if(conSonido)
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
            //
            nivelAnim.setContentView(R.layout.nivel_tres_superado);
        }

        nivelAnim.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nivelAnim.show();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                pantallaCompleta();

                if(nivel == 1 && conMusica==true){
                    fondo2.start();
                } else if (nivel == 2 && conMusica==true){
                    fondo3.start();
                } else if(nivel == 3 && conMusica==true){
                    fondo4.start();
                }
                nivelAnim.cancel();
                pantallaCompleta();
            };
        }, 3000);
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

        if(Prube == true){
            if(segundos == 2){
                Intent intent = new Intent(JuegoSeis.this,AfterGame.class);
                intent.putExtra("correctos", points);
                intent.putExtra("juego", idJuego);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                fondo.reset();
                fondo2.reset();
                fondo3.reset();
                fondo4.reset();
                finish();
            }
        }



        txtTimer.setText(leftTime);

    }

    void revisarNievel(){
        if (points == 5 && nivel == 0){
            nivel = 1;
            showpopupNiveles(new View(this));
        }else if(points == 10 && nivel == 1){
            nivel = 2;
            showpopupNiveles(new View(this));
        }
        else if(points == 25 && nivel == 2){
            nivel = 3;
            showpopupNiveles(new View(this));
        }
    }

    void imgRandomAnt(){
        anterio = (int) (Math.random() * 10);
        imgCambio.setImageResource(img[anterio]);
    }

    void imgRandomAct(){
        actual = (int) (Math.random() * 10);
        imgCambio.setImageResource(img[actual]);
    }

    void starGame(){
        revisarNievel();
        // animationView2.playAnimation();
        imgRandomAnt();
        txtEnunciado.setText("¿Qué diferencia hay entre esta imagen...");
        //Detenemos Tiempo
        animationView.pauseAnimation();
        psBandera = 1;
        timeRunning = false;
        starStop();
        //

        new Handler().postDelayed(new Runnable(){
            public void run(){
                animationView2.playAnimation();
                txtEnunciado.setText("y esta otra imagen?");

                imgRandomAct();
                //Reanudamos el Tiempo
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();
                //

            };
        }, 5000);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnUno:
                if((anterio >= 5 && actual >= 5) || ((anterio >= 0 && anterio <=4)&&(actual >= 0 && actual <=4))){
                    if (conSonido)
                        msCorrecto.start();
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                actual = 0;
                anterio = 0;
                starGame();
                break;
            case R.id.btnDos:
                if(((anterio >= 0 && anterio <=4) && (actual >=5)) || ((anterio >= 5) && (actual >=0 && actual <= 4))){
                    if (conSonido)
                        msCorrecto.start();
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                actual = 0;
                anterio = 0;
                starGame();
                break;
            case R.id.btnTres:
                if(anterio == actual){
                    if (conSonido)
                        msCorrecto.start();
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                actual = 0;
                anterio = 0;
                starGame();
                break;
        }

    }
}
