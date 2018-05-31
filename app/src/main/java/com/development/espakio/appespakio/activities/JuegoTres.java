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

public class JuegoTres extends AppCompatActivity implements View.OnClickListener {

    //Nombre del Juego
    int juego = 0;

    //Interfaz del Juego
    private TextView txtColor;
    private ImageView btnPausa,imgColor, btnUno, btnDos;
    //

    //Animacion Interfaz
    private Animation uptodown, downtoup;
    private RelativeLayout head, body, bottom;
    //

    //Timer
    private TextView txtTimer, txtNivel;
    private CountDownTimer timer;
    private long timeleftinMilliseconds = 60000;
    private boolean timeRunning = true;
    private int psBandera = 0;
    private LottieAnimationView animationView; //Lottie Time Animation
    //

    //Musica
    private MediaPlayer msIncorrecto, msCorrecto, fondo, fondo2, fondo3,fondo4, go, nivelPasado;
    //

    //Dialogs
    private Dialog pausapopup, inicioJuegopopup, nivelAnim;
    //

    //Juego
    private int correctas;
    private String[] colores = {"AZUL", "ROJO", "GRIS", "VERDE", "NEGR0", "AMARILLO","MORADO", "ROSA", "NARANJA" };
    private int[] imgColores = {R.drawable.ic_planetaazul, R.drawable.ic_planetarojo, R.drawable.ic_planetagris, R.drawable.ic_planetaverde, R.drawable.ic_planetanegro, R.drawable.ic_planetaamarillo, R.drawable.ic_planetamorado, R.drawable.ic_planetarosa, R.drawable.ic_planetanaranja};
    private int posColor = 0 , posRandom = 0, posboton = 0, correcta = 0, points = 0, nivel = 0;
    private int[] colors;
    private boolean notsmame = false;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_tres);
        pantallaCompleta();
        colors = this.getResources().getIntArray(R.array.juegoTres);

        //Interfaz del Juego
        txtColor = (TextView)findViewById( R.id.txtColor);
        btnPausa = (ImageView)findViewById(R.id.imgPausa);
        imgColor = (ImageView)findViewById(R.id.imgColor);
        btnUno = (ImageView)findViewById(R.id.imgUno);
        btnDos = (ImageView)findViewById(R.id.imgDos);

        btnUno.setOnClickListener(this);
        btnDos.setOnClickListener(this);

        //

        //Animacion Interfaz
        head = (RelativeLayout) findViewById(R.id.rlHead);
        body = (RelativeLayout) findViewById(R.id.rlBody);
        bottom = (RelativeLayout) findViewById(R.id.rlBottom);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        head.setAnimation(uptodown);
        body.setAnimation(downtoup);
        bottom.setAnimation(downtoup);
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

        //Juego
        cambioImageNivelUno();
        //

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
                Intent intent = new Intent(JuegoTres.this,MenuJuegos.class);
                startActivity(intent);
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
                Intent intent = new Intent(JuegoTres.this,JuegoTres.class);
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

        go.start();
        inicioJuegopopup.setContentView(R.layout.inicio_juegos);
        inicioJuegopopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inicioJuegopopup.show();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                inicioJuegopopup.cancel();
                animationView.playAnimation();
                go.pause();
                starStop();
                fondo.start();
            };
        }, 6000);

    }

    //Paso de Niveles
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

        if(segundos == 2){
            Intent intent = new Intent(JuegoTres.this,AfterGame.class);
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

    //Juego

    //Aleatorio para TXTcolor y TXTCorrecto
    int numAleatorioA(){
        int opc1 = (int) (Math.random() * 8);
        posColor = opc1;
        return  opc1;
    }

    //Aleatorio para Segundo Color
    int numAleatorioB(){
        int opc1 = (int) (Math.random() * 2) + 1;
        posboton = opc1;
        return  opc1;
    }

    //Aleatorio para
    int numAleatorioC(){
        int opc1 = (int) (Math.random() * 8);
        posRandom = opc1;
        return  opc1;
    }

    void cambioImageNivelUno(){
        do{
            if(numAleatorioA() != numAleatorioC()){
                txtColor.setText(colores[posColor]);
                txtColor.setTextColor(colors[posRandom]);
                if(numAleatorioB() == 1){
                    btnUno.setImageResource(imgColores[posColor]);
                    btnDos.setImageResource(imgColores[posRandom]);
                    correcta = 1;
                }else {
                    btnDos.setImageResource(imgColores[posColor]);
                    btnUno.setImageResource(imgColores[posRandom]);
                    correcta = 2;
                }
                notsmame = true;
                break;
            }else {
                posColor = 0;
                posboton = 0;
                posRandom = 0;
                correcta = 0;
            }


        }while ( notsmame == false);
    }

    //Revisa los aciertos
    void revisarNievel(){
        if (points == 15){
            nivel = 1;
            showpopupNiveles(new View(this));
        }else if(points == 30){
            nivel = 2;
            showpopupNiveles(new View(this));
        }
        else if(points == 60){
            nivel = 3;
            showpopupNiveles(new View(this));
        }
    }




    @Override
    public void onClick(View view) {
        revisarNievel();
        switch (view.getId()){
            case R.id.imgUno:
                if(posColor == posRandom){
                    msCorrecto.start();
                    points++;
                }else if(posboton == 1 && correcta == 1){
                    msCorrecto.start();
                    points++;
                }else {
                    msIncorrecto.start();
                }

                posColor = 0;
                posboton = 0;
                posRandom = 0;
                correcta = 0;
                notsmame = false;

                cambioImageNivelUno();
                break;
            case R.id.imgDos:
                if(posColor == posRandom){
                    msCorrecto.start();
                    points++;
                }else if(posboton == 2 && correcta == 2){
                    msCorrecto.start();
                    points++;
                }else {
                    msIncorrecto.start();
                }

                posColor = 0;
                posboton = 0;
                posRandom = 0;
                correcta = 0;
                notsmame = false;

                cambioImageNivelUno();
                break;
        }

    }
}
