package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.models.BackgroundWorker;

import java.util.concurrent.ExecutionException;

public class Ingresar extends AppCompatActivity {

    private Button btnAceptar;
    private TextView tvRegresar;
    private EditText etxtUser, etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_ingresar);

        etxtUser = (EditText) findViewById(R.id.ingresar_etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.ingresar_etxtPassword);

        btnAceptar = (Button) findViewById(R.id.ingresar_btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLogin();
            }
        });

        tvRegresar = (TextView) findViewById(R.id.ingresar_tvRegresar);

        tvRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ingresar.this, Inicio.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });

        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void OnLogin () {
        String username = etxtUser.getText().toString();
        String password = etxtPassword.getText().toString();
        String type = "login";

        BackgroundWorker worker = new BackgroundWorker(this);
        worker.execute(type, username, password);
    }

}
