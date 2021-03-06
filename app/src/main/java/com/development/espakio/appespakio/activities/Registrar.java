package com.development.espakio.appespakio.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.RegisterPresenter;
import com.development.espakio.appespakio.view.IRegisterView;

public class Registrar extends AppCompatActivity implements View.OnClickListener, IRegisterView {

    private Button btnAceptar;
    private TextView tvRegresar;
    private EditText etxtUser, etxtPassword, etxtCheckPassword;
    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar);

        etxtUser = (EditText) findViewById(R.id.regresar_etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.registrar_etxtPassword);
        etxtCheckPassword = (EditText) findViewById(R.id.registrar_etxtCheckPassword);

        btnAceptar = (Button) findViewById(R.id.registrar_btnAceptar);
        btnAceptar.setOnClickListener(this);

        tvRegresar = (TextView) findViewById(R.id.registrar_tvRegresar);
        tvRegresar.setOnClickListener(this);

        registerPresenter = new RegisterPresenter(Registrar.this, getApplicationContext());

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
        switch (view.getId())
        {
            case R.id.registrar_tvRegresar:
                startActivity(new Intent(this, Inicio.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.registrar_btnAceptar:
                String email = etxtUser.getText().toString();
                String password = etxtPassword.getText().toString();
                String verPassword = etxtCheckPassword.getText().toString();
                registerPresenter.performRegister(email, password, verPassword);
                break;
        }
    }

    @Override
    public void registerFormatValidation() {
        Toast.makeText(Registrar.this, "Formato de email inválido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailDuplicate() {
        Toast.makeText(Registrar.this, "El email ya esta regisrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordNotMatch() {
        Toast.makeText(Registrar.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerEmptyFields() {
        Toast.makeText(Registrar.this, "Favor llene todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent(Registrar.this, NuevoUsuario.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        finish();
    }

    @Override
    public void registerError() {
        Toast.makeText(Registrar.this, "Ocurrio un error en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFailed() {
        Toast.makeText(Registrar.this, "Hubo un fallo en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void idCliente(int id) {
        Toast.makeText(Registrar.this, "idCliente: " + id, Toast.LENGTH_LONG).show();
    }
}
