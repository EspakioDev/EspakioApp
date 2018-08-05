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

public class JuegoOcho extends AppCompatActivity implements View.OnClickListener {


    //Interfaz del Juego
    TextView txtColor, txtBnMal, txtPregunta;
    ImageView btnPausa,imgColor, btnUno, btnDos, imgPicture;
    Button btnOpcUno, btnOpcDos, btnOpcTres, btnOpcCuatro, btnOpcCinco, btnOpcSeis;
    //

    //Animacion Interfaz
    Animation uptodown, downtoup;
    RelativeLayout head , body, bottom;
    //

    //Timer
    TextView txtTimer, txtNivel;
    CountDownTimer timer;
    long timeleftinMilliseconds = 60000;
    boolean timeRunning = true;
    int psBandera = 0;
    LottieAnimationView animationView,  animationView2 ; //Lottie Time Animation
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
    String[] colores = {"AZUL", "ROJO", "GRIS", "VERDE", "NEGR0", "AMARILLO","MORADO", "ROSA", "NARANJA" };
    int[] imgColores = {R.drawable.ic_planetaazul, R.drawable.ic_planetarojo, R.drawable.ic_planetagris, R.drawable.ic_planetaverde, R.drawable.ic_planetanegro, R.drawable.ic_planetaamarillo, R.drawable.ic_planetamorado, R.drawable.ic_planetarosa, R.drawable.ic_planetanaranja};

    int points = 0, nivel = 0;
    boolean Prube = true;
    int correct = 0, btnU = 0, btnD = 0, btnT = 0, btnC = 0, btnCin = 0, btnCorrectPc = 0, idJuego = 1;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_ocho);
        pantallaCompleta();

        //Interfaz
        btnPausa = (ImageView)findViewById(R.id.imgPausa);
        txtBnMal = (TextView) findViewById(R.id.txtBienMal);
        txtPregunta = (TextView) findViewById(R.id.txtPregunta);
        imgPicture = (ImageView)findViewById(R.id.imgPicture);
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

        txtBnMal.setVisibility(View.INVISIBLE);
        //

        //Animacion Interfaz
        head = (RelativeLayout) findViewById(R.id.rlHead);
        body = (RelativeLayout) findViewById(R.id.rlBody);
        bottom = (RelativeLayout) findViewById(R.id.rlButtom);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        head.setVisibility(View.GONE);
        body.setVisibility(View.GONE);

        bottom.setVisibility(View.INVISIBLE);
        //bottom.setAnimation(downtoup);

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
        Prube = false;
        Intent intent = new Intent(JuegoOcho.this,MenuJuegos.class);
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
                //Intent intent = new Intent(JuegoOcho.this,MenuJuegos.class);
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
                Intent intent = new Intent(JuegoOcho.this,JuegoOcho.class);
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
                if(conSonido)
                    go.start();
                inicioJuegopopup.setContentView(R.layout.inicio_juegos);
                inicioJuegopopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                inicioJuegopopup.show();

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        head.setVisibility(View.VISIBLE);
                        body.setVisibility(View.VISIBLE);
                        head.setAnimation(uptodown);
                        body.setAnimation(uptodown);
                        inicioJuegopopup.cancel();
                        animationView.playAnimation();
                        go.pause();
                        starStop();
                        if(conMusica)
                            fondo.start();
                        starGame();
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
                //Reanudamos el Tiempo
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();
                //
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
                Intent intent = new Intent(JuegoOcho.this,AfterGame.class);
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

    void numRand(){
        correct= (int) (Math.random() * 9) ;

    }

    int numRandDos(){
        int opc= (int) (Math.random() * 9) ;
        return  opc;
    }

    int numRandBtn(){
        int opc = (int) (Math.random() * 6) ;
        return  opc;
    }

    void generateC(){
        numRand();
        boolean opc = false;
        do{
            btnU = numRandDos();
            btnD = numRandDos();
            btnT = numRandDos();
            btnC = numRandDos();
            btnCin = numRandDos();
            if(btnU != correct && btnD != correct && btnT != correct && btnC != correct && btnCin != correct){
                if(btnU != btnD && btnU != btnT && btnU != btnC && btnU != btnCin && btnD != btnT && btnD != btnC && btnD != btnCin && btnT != btnC && btnT != btnCin && btnC != btnCin){
                    opc = true;
                }
            }


        }while (opc == false);
    }

    void generateBtn(){
        generateC();
        btnCorrectPc = numRandBtn();
        switch (btnCorrectPc){
            case 0:
                btnOpcUno.setText(colores[correct]);
                btnOpcDos.setText(colores[btnU]);
                btnOpcTres.setText(colores[btnD]);
                btnOpcCuatro.setText(colores[btnT]);
                btnOpcCinco.setText(colores[btnC]);
                btnOpcSeis.setText(colores[btnCin]);
                break;
            case 1:
                btnOpcUno.setText(colores[btnU]);
                btnOpcDos.setText(colores[correct]);
                btnOpcTres.setText(colores[btnD]);
                btnOpcCuatro.setText(colores[btnT]);
                btnOpcCinco.setText(colores[btnC]);
                btnOpcSeis.setText(colores[btnCin]);
                break;
            case 2:
                btnOpcUno.setText(colores[btnD]);
                btnOpcDos.setText(colores[btnU]);
                btnOpcTres.setText(colores[correct]);
                btnOpcCuatro.setText(colores[btnT]);
                btnOpcCinco.setText(colores[btnC]);
                btnOpcSeis.setText(colores[btnCin]);
                break;
            case 3:
                btnOpcUno.setText(colores[btnT]);
                btnOpcDos.setText(colores[btnU]);
                btnOpcTres.setText(colores[btnD]);
                btnOpcCuatro.setText(colores[correct]);
                btnOpcCinco.setText(colores[btnC]);
                btnOpcSeis.setText(colores[btnCin]);
                break;
            case 4:
                btnOpcUno.setText(colores[btnC]);
                btnOpcDos.setText(colores[btnU]);
                btnOpcTres.setText(colores[btnD]);
                btnOpcCuatro.setText(colores[btnT]);
                btnOpcCinco.setText(colores[correct]);
                btnOpcSeis.setText(colores[btnCin]);
                break;
            case 5:
                btnOpcUno.setText(colores[btnCin]);
                btnOpcDos.setText(colores[btnU]);
                btnOpcTres.setText(colores[btnD]);
                btnOpcCuatro.setText(colores[btnT]);
                btnOpcCinco.setText(colores[btnC]);
                btnOpcSeis.setText(colores[correct]);
                break;
        }
    }

    void starGame(){

        revisarNievel();
        //Detenemos Tiempo
        animationView.pauseAnimation();
        psBandera = 1;
        timeRunning = false;
        starStop();
        //
        generateBtn();
        animationView2.playAnimation();
        imgPicture.setImageResource(imgColores[correct]);



        new Handler().postDelayed(new Runnable(){
            public void run(){
                //Reanudamos el Tiempo
                animationView.playAnimation();
                psBandera = 0;
                timeRunning = true;
                starStop();

                txtPregunta.setText("¿ De que color era el planeta ? ");
                animationView2.playAnimation();
                imgPicture.setVisibility(View.INVISIBLE);
                bottom.setVisibility(View.VISIBLE);

            };
        }, 5000);

    }

    void cleanVariables(){
        correct = 0;
        btnCorrectPc = 0;
        btnU = 0;
        btnD = 0;
        btnT = 0;
        btnC = 0;
        btnCin = 0;
    }

    @Override
    public void onClick(View v) {
        txtBnMal.setVisibility(View.VISIBLE);
        switch (v.getId()){
            case R.id.btnOpcUno:
                if(btnCorrectPc == 0){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcUno.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcUno.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;
            case R.id.btnOpcDos:
                if(btnCorrectPc == 1){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcDos.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcDos.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;
            case R.id.btnOpcTres:
                if(btnCorrectPc == 2){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcTres.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcTres.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;
            case R.id.btnOpcCuatro:
                if(btnCorrectPc == 3){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcCuatro.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcUno.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;
            case R.id.btnOpcCinco:
                if(btnCorrectPc == 4){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcCinco.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcCinco.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;
            case R.id.btnOpcSeis:
                if(btnCorrectPc == 5){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcSeis.setBackgroundResource(R.drawable.btnstyle);
                    txtBnMal.setText("¡ CORRECTO !");
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                    txtBnMal.setText("¡ INCORRECTO !");
                }

                animationView2.playAnimation();
                imgPicture.setVisibility(View.VISIBLE);

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();

                        cleanVariables();
                        txtBnMal.setVisibility(View.INVISIBLE);
                        btnOpcSeis.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtPregunta.setText("Observa el Plantena por 3 Segundos");
                        bottom.setVisibility(View.INVISIBLE);
                        starGame();

                    };
                }, 2000);

                break;

        }
    }
}
