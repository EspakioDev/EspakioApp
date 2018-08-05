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

public class JuegoSiete extends AppCompatActivity implements View.OnClickListener{

    //Interfaz del Juego
    TextView txtColor, txtHead, txtBody;
    ImageView btnPausa,imgColor, btnUno, btnDos;
    Button btnPalUno, btnPalDos, btnPalTres, btnPalCuatro;
    Button btnOpcUno, btnOpcDos, btnOpcTres, btnOpcCuatro, btnOpcCinco, btnOpcSeis;
    //

    //Animacion Interfaz
    Animation uptodown, downtoup;
    RelativeLayout head, rlBody, rlButtom;
    //

    //Timer
    TextView txtTimer, txtNivel;
    CountDownTimer timer;
    long timeleftinMilliseconds = 60000;
    boolean timeRunning = true;
    int psBandera = 0;
    LottieAnimationView animationView; //Lottie Time Animation
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
    String[] campos = {"Leo", "Tauro", "Aries", "Capricornio", "Picis", "Virgo", "Acuario", "Cancer", "Libra", "Geminis",
            "Orion", "Andromeda", "Caelum", "Ara", "Fenix", "Astro", "Asteroide", "Luna", "Cometa", "Estrella",
            "Saturno", "Neptuno", "Mercurio", "Venus", "Urano", "Astronauta", "Cohete", "Satelite", "Luna", "Sol"};
    int PalUno = 0, PalDos = 0, PalTres = 0, PalCuatro = 0;
    int OpcUno = 0, OpcDos = 0, OpcTres = 0, OpcCuatro = 0;
    int btnU = 0, btnD = 0, btnT = 0, btnC = 0, btnCin = 0, btnS = 0;
    int points = 0, nivel = 0, idJuego = 0;
    boolean Prube = true;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_siete);
        pantallaCompleta();

        //Interfaz
        btnPausa = (ImageView)findViewById(R.id.imgPausa);

        txtHead =  (TextView) findViewById(R.id.txtHead);
        txtBody =  (TextView) findViewById(R.id.txtBody);

        btnPalUno = (Button) findViewById(R.id.btnPalUno);
        btnPalDos = (Button) findViewById(R.id.btnPalDos);
        btnPalTres = (Button) findViewById(R.id.btnPalTres);
        btnPalCuatro = (Button) findViewById(R.id.btnPalCuatro);

        btnPalTres.setVisibility(View.INVISIBLE);
        btnPalCuatro.setVisibility(View.INVISIBLE);

        btnOpcUno = (Button) findViewById(R.id.btnOpcUno);
        btnOpcDos = (Button) findViewById(R.id.btnOpcDos);
        btnOpcTres = (Button) findViewById(R.id.btnOpcTres);
        btnOpcCuatro = (Button) findViewById(R.id.btnOpcCuatro);
        btnOpcCinco = (Button) findViewById(R.id.btnOpcCinco);
        btnOpcSeis = (Button) findViewById(R.id.btnOpcSeis);

        btnOpcUno.setOnClickListener(this);
        btnOpcDos.setOnClickListener(this);
        btnOpcTres.setOnClickListener(this);
        btnOpcCuatro.setOnClickListener(this);
        btnOpcCinco.setOnClickListener(this);
        btnOpcSeis.setOnClickListener(this);
        txtHead.setOnClickListener(this);
        //

        //Animacion Interfaz
        head = (RelativeLayout) findViewById(R.id.rlHead);
        rlBody = (RelativeLayout) findViewById(R.id.txtDesc);
        rlButtom = (RelativeLayout) findViewById(R.id.rlButtom);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        rlButtom.setVisibility(View.INVISIBLE);

        rlBody.setAnimation(downtoup);
        head.setAnimation(uptodown);
        //

        //Timer
        animationView = (LottieAnimationView)findViewById(R.id.animationTime);
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
        Intent intent = new Intent(JuegoSiete.this,MenuJuegos.class);
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
                //Intent intent = new Intent(JuegoSiete.this,MenuJuegos.class);
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
                Intent intent = new Intent(JuegoSiete.this,JuegoSiete.class);
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
                pantallaCompleta();

            }
        });
        pausapopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pausapopup.show();


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
                Intent intent = new Intent(JuegoSiete.this,AfterGame.class);
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

    int numRand(){
        int opc = (int) (Math.random() * 30);
        return  opc;
    }

    int numRandLugarChec(){
        int opc = (int) (Math.random() * 4) ;
        return  opc;
    }

    void numRandListaNivelUno(){
        boolean diferentes = false;
        do{
            PalUno = numRand();
            PalDos = numRand();
            OpcUno = numRand();
            OpcDos = numRand();
            OpcTres = numRand();
            OpcCuatro = numRand();
            if((PalUno != PalDos)){
                if((PalUno != OpcUno) && (PalUno != OpcDos) && (PalUno != OpcTres) && (PalUno != OpcCuatro)  && (PalDos != OpcUno) && (PalDos != OpcDos) && (PalDos != OpcTres) && (PalDos != OpcCuatro) ){
                    if((OpcUno != OpcDos) && (OpcUno != OpcTres) && (OpcUno != OpcCuatro)  && (OpcDos != OpcTres) && (OpcDos != OpcCuatro) && (OpcTres != OpcCuatro)){
                        btnPalUno.setText(campos[PalUno]);
                        btnPalDos.setText(campos[PalDos]);
                        diferentes = true;
                    }

                }
            }


        }while(diferentes == false);
    }

    void numRandListaNivelDos(){
        boolean diferentes = false;
        do{
            PalUno = numRand();
            PalDos = numRand();
            PalTres = numRand();
            OpcUno = numRand();
            OpcDos = numRand();
            OpcTres = numRand();
            if((PalUno != PalDos) && (PalUno != PalTres) && (PalDos != PalTres)){
                if((PalUno != OpcUno) && (PalUno != OpcDos) && (PalUno != OpcTres) && (PalDos != OpcUno) && (PalDos != OpcDos) && (PalDos != OpcTres) && (PalTres != OpcUno) && (PalTres != OpcDos) && (PalTres != OpcTres)){
                    if((OpcUno != OpcDos) && (OpcUno != OpcTres) && (OpcDos != OpcTres)){
                        btnPalUno.setText(campos[PalUno]);
                        btnPalDos.setText(campos[PalDos]);
                        btnPalTres.setText(campos[PalTres]);
                        diferentes = true;
                    }

                }
            }


        }while(diferentes == false);
    }

    void numRandListaNivelTres(){
        boolean diferentes = false;
        do{
            PalUno = numRand();
            PalDos = numRand();
            PalTres = numRand();
            PalCuatro = numRand();
            OpcUno = numRand();
            OpcDos = numRand();

            if((PalUno != PalDos) && (PalUno != PalTres) && (PalUno != PalCuatro)  && (PalDos != PalTres) && (PalDos != PalCuatro) && (PalTres != PalCuatro)){
                if((PalUno != OpcUno) && (PalUno != OpcDos) && (PalDos != OpcUno) && (PalDos != OpcDos) && (PalTres != OpcUno) && (PalTres != OpcDos) && (PalCuatro != OpcUno) && (PalCuatro != OpcDos)){
                    if((OpcUno != OpcDos)){
                        btnPalUno.setText(campos[PalUno]);
                        btnPalDos.setText(campos[PalDos]);
                        btnPalTres.setText(campos[PalTres]);
                        btnPalCuatro.setText(campos[PalCuatro]);
                        diferentes = true;
                    }

                }
            }


        }while(diferentes == false);
    }

    void numRandBotonesNivelUno(){
        if(numRandLugarChec() == 0){
            btnU = 1;
            btnS = 1;

            btnOpcUno.setText(campos[PalUno]);
            btnOpcDos.setText(campos[OpcUno]);
            btnOpcTres.setText(campos[OpcDos]);
            btnOpcCuatro.setText(campos[OpcTres]);
            btnOpcCinco.setText(campos[OpcCuatro]);
            btnOpcSeis.setText(campos[PalDos]);

        }else if(numRandLugarChec() == 1){
            btnD = 1;
            btnCin = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[PalUno]);
            btnOpcTres.setText(campos[OpcDos]);
            btnOpcCuatro.setText(campos[OpcTres]);
            btnOpcCinco.setText(campos[PalDos]);
            btnOpcSeis.setText(campos[OpcCuatro]);

        }else if(numRandLugarChec() == 2){
            btnD = 1;
            btnT = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[PalUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[OpcDos]);
            btnOpcCinco.setText(campos[OpcTres]);
            btnOpcSeis.setText(campos[OpcCuatro]);

        }else {
            btnT = 1;
            btnS = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[OpcDos]);
            btnOpcTres.setText(campos[PalUno]);
            btnOpcCuatro.setText(campos[OpcTres]);
            btnOpcCinco.setText(campos[OpcCuatro]);
            btnOpcSeis.setText(campos[PalDos]);
        }

    }

    void numRandBotonesNivelDos(){
        if(numRandLugarChec() == 0){
            btnU = 1;
            btnT = 1;
            btnS = 1;

            btnOpcUno.setText(campos[PalUno]);
            btnOpcDos.setText(campos[OpcUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[OpcDos]);
            btnOpcCinco.setText(campos[OpcTres]);
            btnOpcSeis.setText(campos[PalTres]);

        }else if(numRandLugarChec() == 1){
            btnD = 1;
            btnC = 1;
            btnCin = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[PalUno]);
            btnOpcTres.setText(campos[OpcDos]);
            btnOpcCuatro.setText(campos[PalDos]);
            btnOpcCinco.setText(campos[PalTres]);
            btnOpcSeis.setText(campos[OpcTres]);

        }else if(numRandLugarChec() == 2){
            btnU = 1;
            btnD = 1;
            btnT = 1;

            btnOpcUno.setText(campos[PalUno]);
            btnOpcDos.setText(campos[PalDos]);
            btnOpcTres.setText(campos[PalTres]);
            btnOpcCuatro.setText(campos[OpcUno]);
            btnOpcCinco.setText(campos[OpcDos]);
            btnOpcSeis.setText(campos[OpcTres]);

        }else {
            btnC = 1;
            btnCin = 1;
            btnS = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[OpcDos]);
            btnOpcTres.setText(campos[OpcTres]);
            btnOpcCuatro.setText(campos[PalUno]);
            btnOpcCinco.setText(campos[PalDos]);
            btnOpcSeis.setText(campos[PalTres]);
        }


    }

    void numRandBotonesNivelTres(){
        if(numRandLugarChec() == 0){
            btnU = 1;
            btnT = 1;
            btnC = 1;
            btnS = 1;

            btnOpcUno.setText(campos[PalUno]);
            btnOpcDos.setText(campos[OpcUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[PalTres]);
            btnOpcCinco.setText(campos[OpcDos]);
            btnOpcSeis.setText(campos[PalCuatro]);

        }else if(numRandLugarChec() == 1){
            btnD = 1;
            btnT = 1;
            btnC = 1;
            btnCin = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[PalUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[PalTres]);
            btnOpcCinco.setText(campos[PalCuatro]);
            btnOpcSeis.setText(campos[OpcDos]);

        }else if(numRandLugarChec() == 2){
            btnD = 1;
            btnT = 1;
            btnC = 1;
            btnS = 1;

            btnOpcUno.setText(campos[OpcUno]);
            btnOpcDos.setText(campos[PalUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[PalTres]);
            btnOpcCinco.setText(campos[OpcDos]);
            btnOpcSeis.setText(campos[PalCuatro]);

        }else {
            btnU = 1;
            btnT = 1;
            btnC = 1;
            btnCin = 1;

            btnOpcUno.setText(campos[PalUno]);
            btnOpcDos.setText(campos[OpcUno]);
            btnOpcTres.setText(campos[PalDos]);
            btnOpcCuatro.setText(campos[PalTres]);
            btnOpcCinco.setText(campos[PalCuatro]);
            btnOpcSeis.setText(campos[OpcDos]);
        }

    }

    void revisarNievel(){
        if (points == 6 && nivel == 0){
            nivel = 1;
            showpopupNiveles(new View(this));
        }else if(points == 12 && nivel == 1){
            nivel = 2;
            showpopupNiveles(new View(this));
        }
        else if(points == 20 && nivel == 2){
            nivel = 3;
            showpopupNiveles(new View(this));
        }
    }

    void cleanBotones(){
        btnOpcUno.setText(" ");
        btnOpcDos.setText(" ");
        btnOpcTres.setText(" ");
        btnOpcCuatro.setText(" ");
        btnOpcCinco.setText(" ");
        btnOpcSeis.setText(" ");
    }

    void cleanVariables(){
        PalUno = 0;
        PalDos = 0;
        PalTres = 0;
        PalCuatro = 0;
        OpcUno = 0;
        OpcDos = 0;
        OpcTres = 0;
        OpcCuatro = 0;
        btnU = 0;
        btnD = 0;
        btnT = 0;
        btnC = 0;
        btnCin = 0;
        btnS = 0;

    }

    void checSeleccion(){
        if((btnU == 0) && (btnD == 0) && (btnT == 0) && (btnC == 0) && (btnCin == 0) && (btnS == 0)){
            cleanVariables();
            btnOpcUno.setBackgroundResource(R.drawable.btnstyle_amarillo);
            btnOpcDos.setBackgroundResource(R.drawable.btnstyle_amarillo);
            btnOpcTres.setBackgroundResource(R.drawable.btnstyle_amarillo);
            btnOpcCuatro.setBackgroundResource(R.drawable.btnstyle_amarillo);
            btnOpcCinco.setBackgroundResource(R.drawable.btnstyle_amarillo);
            btnOpcSeis.setBackgroundResource(R.drawable.btnstyle_amarillo);
            starGame();
        }
    }

    void starGame(){
        revisarNievel();
        rlButtom.setVisibility(View.INVISIBLE);
        cleanBotones();

        //Detenemos Tiempo
        animationView.pauseAnimation();
        psBandera = 1;
        timeRunning = false;
        starStop();
        //

        if(nivel == 0){
            numRandListaNivelUno();
        }else if(nivel == 1){
            btnPalTres.setVisibility(View.VISIBLE);
            numRandListaNivelDos();
        }else {
            btnPalCuatro.setVisibility(View.VISIBLE);
            numRandListaNivelTres();
        }


        new Handler().postDelayed(new Runnable(){
            public void run(){
                rlButtom.setVisibility(View.VISIBLE);
                rlButtom.setAnimation(downtoup);

                //Reanudamos el Tiempo
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();
                //

                if(nivel == 0){
                    btnPalUno.setText(" ");
                    btnPalDos.setText(" ");
                    numRandBotonesNivelUno();
                }else if(nivel == 1){
                    btnPalUno.setText(" ");
                    btnPalDos.setText(" ");
                    btnPalTres.setText(" ");
                    numRandBotonesNivelDos();
                }else {
                    btnPalUno.setText(" ");
                    btnPalDos.setText(" ");
                    btnPalTres.setText(" ");
                    btnPalCuatro.setText(" ");
                    numRandBotonesNivelTres();
                }


            };
        }, 10000);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnOpcUno:
                if(btnU == 1){
                    points++;
                    btnOpcUno.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnU = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.btnOpcDos:
                if(btnD == 1){
                    points++;
                    btnOpcDos.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnD = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.btnOpcTres:
                if(btnT == 1){
                    points++;
                    btnOpcTres.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnT = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.btnOpcCuatro:
                if(btnC == 1){
                    points++;
                    btnOpcCuatro.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnC = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.btnOpcCinco:
                if(btnCin == 1){
                    points++;
                    btnOpcCinco.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnCin = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.btnOpcSeis:
                if(btnS == 1){
                    points++;
                    btnOpcSeis.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    btnS = 0;
                    checSeleccion();
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                break;
            case R.id.txtHead:
                starGame();
                break;
        }
    }


}
