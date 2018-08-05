package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.development.espakio.appespakio.R;

public class Inicio extends AppCompatActivity implements View.OnClickListener{

    private Button btnIngresar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar:
                startActivity(new Intent(Inicio.this, Ingresar.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.btnRegistrar:
                startActivity(new Intent(Inicio.this, Registrar.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
        }
    }
}
