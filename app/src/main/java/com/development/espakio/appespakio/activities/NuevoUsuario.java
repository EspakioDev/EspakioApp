package com.development.espakio.appespakio.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.models.BackgroundWorker;
import com.development.espakio.appespakio.models.Cliente;

import java.util.Calendar;


public class NuevoUsuario extends AppCompatActivity implements View.OnClickListener{

    private Button btnAceptar;
    private EditText etxtNombre, etxtBirthday;
    private Cliente cliente;
    private int idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_nuevo_usuario);

        etxtNombre = (EditText) findViewById(R.id.nuevoUsuario_etxtNombre);
        etxtBirthday = (EditText) findViewById(R.id.nuevoUsuario_etxtBirthday);
        etxtBirthday.setOnClickListener(this);

        btnAceptar = (Button) findViewById(R.id.nuevoUsuario_btnAceptar);
        btnAceptar.setOnClickListener(this);

        cliente = (Cliente) getIntent().getExtras().getSerializable("cliente");
        Toast.makeText(this, "La informacion que recibi"+cliente.toString(), Toast.LENGTH_SHORT).show();

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
            case R.id.nuevoUsuario_etxtBirthday:

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

                break;
            case R.id.nuevoUsuario_btnAceptar:
                String username = etxtNombre.getText().toString();
                String birthday = etxtBirthday.getText().toString();
                String type = "newUser";

                BackgroundWorker worker = new BackgroundWorker(this);
                worker.execute(type, username, birthday, Integer.toString(cliente.getID()));
                break;
        }
    }
}
