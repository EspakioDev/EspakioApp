package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.models.BackgroundWorker;

public class Registrar extends AppCompatActivity implements View.OnClickListener{

    private Button btnAceptar;
    private TextView tvRegresar;
    private EditText etxtUser, etxtPassword, etxtCheckPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_registrar);

        etxtUser = (EditText) findViewById(R.id.regresar_etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.registrar_etxtPassword);
        etxtCheckPassword = (EditText) findViewById(R.id.registrar_etxtCheckPassword);

        btnAceptar = (Button) findViewById(R.id.registrar_btnAceptar);
        btnAceptar.setOnClickListener(this);

        tvRegresar = (TextView) findViewById(R.id.registrar_tvRegresar);
        tvRegresar.setOnClickListener(this);

        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.registrar_tvRegresar:
                startActivity(new Intent(this, Inicio.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.registrar_btnAceptar:
                String username = etxtUser.getText().toString();
                String password = etxtPassword.getText().toString();
                String verPassword = etxtCheckPassword.getText().toString();

                String type = "register";

                if(password.equals(verPassword)) {
                    BackgroundWorker worker = new BackgroundWorker(this);
                    worker.execute(type, username, password);
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
