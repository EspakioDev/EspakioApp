package com.development.espakio.appespakio.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.NewUserPresenter;
import com.development.espakio.appespakio.view.INewUserView;

import java.util.Calendar;


public class NuevoUsuario extends AppCompatActivity implements View.OnClickListener, INewUserView, View.OnFocusChangeListener{

    private Button btnAceptar;
    private ImageView imagenPerfil;
    private EditText etxtNombre, etxtBirthday;
    private NewUserPresenter newUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        etxtNombre = (EditText) findViewById(R.id.nuevoUsuario_etxtNombre);
        etxtNombre.setOnFocusChangeListener(this);

        etxtBirthday = (EditText) findViewById(R.id.nuevoUsuario_etxtBirthday);
        etxtBirthday.setOnClickListener(this);
        etxtBirthday.setOnFocusChangeListener(this);

        imagenPerfil = (ImageView) findViewById(R.id.nuevoUsuario_imgUserPerfil);
        imagenPerfil.setOnClickListener(this);

        btnAceptar = (Button) findViewById(R.id.nuevoUsuario_btnAceptar);
        btnAceptar.setOnClickListener(this);

        newUserPresenter = new NewUserPresenter(NuevoUsuario.this, getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        newUserPresenter.performChargeImage();
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
            case R.id.nuevoUsuario_etxtBirthday:
                getDate();
                break;
            case R.id.nuevoUsuario_btnAceptar:
                String username = etxtNombre.getText().toString();
                String birthday = etxtBirthday.getText().toString();
                newUserPresenter.performAddUser(username, birthday);
                break;
            case R.id.nuevoUsuario_imgUserPerfil:
                startActivity(new Intent(this, ImagenPerfilUsuario.class));
                overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                break;
        }
    }

    private void getDate() {
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, R.style.AppTheme1 ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                etxtBirthday.setText(day+"/"+(month+1)+"/"+year);
            }
        }, anio, mes, dia);
        pickerDialog.show();
    }

    @Override
    public void putUserImage(int idImage) {
        imagenPerfil.setImageResource(idImage);
    }

    @Override
    public void newUserEmptyFields() {
        Toast.makeText(NuevoUsuario.this, "Favor llene todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newUserSuccess() {
        Intent intent = new Intent(NuevoUsuario.this, SplashScreen3.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        finish();
    }

    @Override
    public void Error() {
        Toast.makeText(NuevoUsuario.this, "Ocurrio un error en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Failed() {
        Toast.makeText(NuevoUsuario.this, "Hubo un fallo en la conexion de base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void idUsuario(int id) {
        Toast.makeText(NuevoUsuario.this, "idUsuario: " + id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(view.getId() == R.id.nuevoUsuario_etxtNombre && !b) {
            hideKeyboard(view);
        }
        if(view.getId() == R.id.nuevoUsuario_etxtBirthday && b) {
            hideKeyboard(view);
        }
    }

    void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
