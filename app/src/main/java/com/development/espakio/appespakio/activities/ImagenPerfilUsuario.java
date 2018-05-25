package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;

public class ImagenPerfilUsuario extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int[] imgUsuario;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_perfil_usuario);

        gridView = (GridView) findViewById(R.id.gvUsuarioImg);
        imgUsuario = new int[]{
                R.drawable.img_usuario_azul,
                R.drawable.img_usuario_amarillo,
                R.drawable.img_usuario_rojo,
                R.drawable.img_usuario_verde,
                R.drawable.img_usuario_luna,
                R.drawable.img_usuario_rosa,
                R.drawable.img_usuario_astronauta

        };

        gridView.setOnItemClickListener(this);

        MyAdapter adapter =  new MyAdapter(this, R.layout.gridview, imgUsuario);
        gridView.setAdapter(adapter);

        fullScreen();
    }

    private void fullScreen() {
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if ( i == 1){
            startActivity(new Intent(ImagenPerfilUsuario.this, NuevoUsuario.class));
        }
    }
}
