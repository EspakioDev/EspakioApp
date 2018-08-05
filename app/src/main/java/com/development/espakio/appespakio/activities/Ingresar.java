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
import com.development.espakio.appespakio.presenter.LoginPresenter;
import com.development.espakio.appespakio.view.ILoginView;

public class Ingresar extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private Button btnAceptar;
    private TextView tvRegresar;
    private EditText etxtUser, etxtPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        etxtUser = (EditText) findViewById(R.id.ingresar_etxtUsuario);
        etxtPassword = (EditText) findViewById(R.id.ingresar_etxtPassword);

        btnAceptar = (Button) findViewById(R.id.ingresar_btnAceptar);
        btnAceptar.setOnClickListener(this);

        tvRegresar = (TextView) findViewById(R.id.ingresar_tvRegresar);
        tvRegresar.setOnClickListener(this);

        loginPresenter = new LoginPresenter(Ingresar.this, getApplicationContext());
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
            case R.id.ingresar_tvRegresar:
                startActivity(new Intent(this, Inicio.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
            case R.id.ingresar_btnAceptar:
                String username = etxtUser.getText().toString();
                String password = etxtPassword.getText().toString();
                loginPresenter.performLogin(username,password);
                break;
        }
    }

    @Override
    public void loginFormatValidation() {
        Toast.makeText(getApplicationContext(), "Formato de email inválido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginEmptyFields() {
        Toast.makeText(getApplicationContext(), "Favor llene todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(Ingresar.this, MenuUsuarios.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        finish();
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Ocurrio un error en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(), "Hubo un fallo en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginAccesFailed() {
        Toast.makeText(getApplicationContext(), "El email o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void idCliente(int id) {
        Toast.makeText(Ingresar.this, "idCliente: " + id, Toast.LENGTH_LONG).show();
    }
}
