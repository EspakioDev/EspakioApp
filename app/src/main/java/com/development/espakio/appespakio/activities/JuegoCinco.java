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

public class JuegoCinco extends AppCompatActivity implements View.OnClickListener {


    //Interfaz del Juego
    TextView txtColor, txtRes;
    ImageView btnPausa,imgColor, btnUno, btnDos;
    Button opcUno, opcDos, opcTres, btnOpcUno, btnOpcDos,btnOpcTres;
    //

    //Animacion Interfaz
    Animation uptodown, downtoup, prueba;
    RelativeLayout head, body, bottom;
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
    int points = 0, nivel = 0;
    String[] campos ={"Figura","Animal","Color"};
    String[] fig ={"Triangulo","Cuadrado","Circulo","Rombo","Rectangulo","Trapecio","Pentegono"," Ovalo","Hexagono"}; //9 Opc
    String[] ani ={"Perro","Gato","Vaca","Pato","Lobo","Pez","Leon","Raton", "Serpiente"}; // 9 Opc
    String[] col ={"Blanco","Azul","Verde","Rojo","Negro","Morado","Amarillo","Gris","Rosa"}; //9 Opc
    int OpcA = 0, OpcB = 0, list = 0, ResPosition = 0, numAlt = 0, resOpc = 0;
    boolean Prube = true;
    int numJuego = 6;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_cinco);
        pantallaCompleta();

        //Interfaz
        btnPausa = (ImageView)findViewById(R.id.imgPausa);
        opcUno = (Button) findViewById(R.id.btnOpcUno);
        opcDos = (Button) findViewById(R.id.btnOpcDos);
        opcTres = (Button) findViewById(R.id.btnOpcTres);
        btnOpcUno = (Button) findViewById(R.id.btnRespUno);
        btnOpcDos = (Button) findViewById(R.id.btnRespDos);
        btnOpcTres = (Button) findViewById(R.id.btnRespTres);
        txtRes= (TextView) findViewById(R.id.txtRespuesta);

        btnOpcUno.setOnClickListener(this);
        btnOpcDos.setOnClickListener(this);
        btnOpcTres.setOnClickListener(this);
        //

        //Animacion Interfaz
        head = (RelativeLayout) findViewById(R.id.rlHead);
        body = (RelativeLayout) findViewById(R.id.txtDesc);
        bottom = (RelativeLayout) findViewById(R.id.rlBottom);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);


        head.setVisibility(View.GONE);
        body.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);

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

        gameStar();
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
        Intent intent = new Intent(JuegoCinco.this,MenuJuegos.class);
        startActivity(intent);
        fondo.reset();
        fondo2.reset();
        fondo3.reset();
        fondo4.reset();
        finish();

    }

    @Override
    protected void onPause() {
        Prube = false;
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
                //Intent intent = new Intent(JuegoCinco.this,MenuJuegos.class);
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
                Intent intent = new Intent(JuegoCinco.this,JuegoCinco.class);
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
                        body.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.VISIBLE);
                        head.setAnimation(uptodown);
                        body.setAnimation(uptodown);
                        bottom.setAnimation(downtoup);
                        inicioJuegopopup.cancel();
                        animationView.playAnimation();
                        go.pause();
                        starStop();
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
                Intent intent = new Intent(JuegoCinco.this,AfterGame.class);
                intent.putExtra("correctos", points);
                intent.putExtra("juego", numJuego);
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

    // Opciones entre Fig, Color y Animal
    int numAleatorioA(){
        int opc1 = (int) (Math.random() * 3);
        return  opc1;
    }

    int numAleatorioCampos(){
        int opc1 = (int) (Math.random() * 3);
        return  opc1;
    }

    //Opciones entre las Figuras
    int numAleatorioLista(){
        int opc1 = (int) (Math.random() * 9);
        return  opc1;
    }

    void seleccionDeCampos(){
        boolean correcto = false;
        do{
            OpcA = numAleatorioCampos();
            OpcB = numAleatorioCampos();
            if(OpcA != OpcB){
                opcUno.setText(campos[OpcA]);
                opcTres.setText(campos[OpcB]);
                correcto = true;
            }
        }while (correcto == false);
    }

    void seleccionUno(){
        if(OpcA == 0){
            opcDos.setText(fig[numAleatorioLista()]);
        }else if(OpcA == 1){
            opcDos.setText(ani[numAleatorioLista()]);
        }else {
            opcDos.setText(col[numAleatorioLista()]);
        }
    }

    void selectBotonCorrecto(String[] lista){
        int opc1 = numAleatorioA();

        if(opc1 == 0){
            ResPosition = 0;
            if(OpcB == 0){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(lista[resOpc]);
                btnOpcDos.setText(ani[numAleatorioLista()]);
                btnOpcTres.setText(col[numAleatorioLista()]);
            }else if(OpcB == 1){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(lista[resOpc]);
                btnOpcDos.setText(fig[numAleatorioLista()]);
                btnOpcTres.setText(col[numAleatorioLista()]);
            }else{
                resOpc = numAleatorioLista();
                btnOpcUno.setText(lista[resOpc]);
                btnOpcDos.setText(ani[numAleatorioLista()]);
                btnOpcTres.setText(fig[numAleatorioLista()]);
            }
        }else if(opc1 == 1){
            ResPosition = 1;
            if(OpcB == 0){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(col[numAleatorioLista()]);
                btnOpcDos.setText(lista[resOpc]);
                btnOpcTres.setText(col[numAleatorioLista()]);
            }else if(OpcB == 1){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(fig[numAleatorioLista()]);
                btnOpcDos.setText(lista[resOpc]);
                btnOpcTres.setText(col[numAleatorioLista()]);
            }else{
                resOpc = numAleatorioLista();
                btnOpcUno.setText(fig[numAleatorioLista()]);
                btnOpcDos.setText(lista[resOpc]);
                btnOpcTres.setText(ani[numAleatorioLista()]);
            }

        }else {
            ResPosition = 2;
            if(OpcB == 0){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(ani[numAleatorioLista()]);
                btnOpcDos.setText(col[numAleatorioLista()]);
                btnOpcTres.setText(lista[resOpc]);
            }else if(OpcB == 1){
                resOpc = numAleatorioLista();
                btnOpcUno.setText(col[numAleatorioLista()]);
                btnOpcDos.setText(fig[numAleatorioLista()]);
                btnOpcTres.setText(lista[resOpc]);
            }else{
                resOpc = numAleatorioLista();
                btnOpcUno.setText(ani[numAleatorioLista()]);
                btnOpcDos.setText(fig[numAleatorioLista()]);
                btnOpcTres.setText(lista[resOpc]);
            }
        }

    }

    void revisarNievel(){
        if (points == 15 && nivel == 0){
            nivel = 1;
            showpopupNiveles(new View(this));
        }else if(points == 30 && nivel == 1){
            nivel = 2;
            showpopupNiveles(new View(this));
        }
        else if(points == 60 && nivel == 2){
            nivel = 3;
            showpopupNiveles(new View(this));
        }
    }

    void mostrarResp(){
        /*if(OpcB == 0){
            txtRes.setText(fig[numAlt]);
        }else if(OpcB == 1){
            txtRes.setText(ani[numAlt]);
        }else{
            txtRes.setText(col[numAlt]);
        }*/
    }

    void gameStar(){
        revisarNievel();
        seleccionDeCampos();
        seleccionUno();
        if(OpcB == 0){
            list = 0;
            selectBotonCorrecto(fig);
        }else if(OpcB == 1){
            list = 1;
            selectBotonCorrecto(ani);
        }else{
            list = 2;
            selectBotonCorrecto(col);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRespUno:
                mostrarResp();
                if(ResPosition == 0){
                    btnOpcUno.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    if(list == 0){
                        txtRes.setText(fig[resOpc]);
                    }else if(list == 1){
                        txtRes.setText(ani[resOpc]);
                    }else{
                        txtRes.setText(col[resOpc]);
                    }
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //



                new Handler().postDelayed(new Runnable(){
                    public void run(){

                        btnOpcUno.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtRes.setText(" ... ");
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();
                        //
                        resOpc = 0;
                        OpcA = 0;
                        OpcB = 0;
                        ResPosition = 0;
                        numAlt=0;
                        gameStar();

                    };
                }, 2000);

                break;
            case R.id.btnRespDos:
                mostrarResp();
                if(ResPosition == 1){
                    if (conSonido)
                        msCorrecto.start();
                    btnOpcDos.setBackgroundResource(R.drawable.btnstyle);
                    if(list == 0){
                        txtRes.setText(fig[resOpc]);
                    }else if(list == 1){
                        txtRes.setText(ani[resOpc]);
                    }else{
                        txtRes.setText(col[resOpc]);
                    }
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }

                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //



                new Handler().postDelayed(new Runnable(){
                    public void run(){

                        btnOpcDos.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtRes.setText(" ... ");
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();
                        //
                        resOpc = 0;
                        OpcA = 0;
                        OpcB = 0;
                        ResPosition = 0;
                        numAlt=0;
                        gameStar();

                    };
                }, 2000);

                break;

            case R.id.btnRespTres:
                mostrarResp();
                if(ResPosition == 2){
                    btnOpcTres.setBackgroundResource(R.drawable.btnstyle);
                    if (conSonido)
                        msCorrecto.start();
                    if(list == 0){
                        txtRes.setText(fig[resOpc]);
                    }else if(list == 1){
                        txtRes.setText(ani[resOpc]);
                    }else{
                        txtRes.setText(col[resOpc]);
                    }
                    points++;
                }else {
                    if (conSonido)
                        msIncorrecto.start();
                }
                //Detenemos Tiempo
                animationView.pauseAnimation();
                psBandera = 1;
                timeRunning = false;
                starStop();
                //



                new Handler().postDelayed(new Runnable(){
                    public void run(){

                        btnOpcTres.setBackgroundResource(R.drawable.btnstyle_amarillo);
                        txtRes.setText(" ... ");
                        //Reanudamos el Tiempo
                        animationView.playAnimation();
                        psBandera = 0;
                        timeRunning = true;
                        starStop();
                        //
                        resOpc = 0;
                        OpcA = 0;
                        OpcB = 0;
                        ResPosition = 0;
                        numAlt=0;
                        gameStar();

                    };
                }, 2000);
                break;



        }
    }
}


