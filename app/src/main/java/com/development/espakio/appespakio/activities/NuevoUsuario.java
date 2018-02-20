package com.development.espakio.appespakio.activities;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.models.BackgroundWorker;


public class NuevoUsuario extends AppCompatActivity {

    private Button btnAceptar;
    private EditText etxtNombre, etxtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_nuevo_usuario);

        etxtNombre = (EditText) findViewById(R.id.nuevoUsuario_etxtNombre);
        etxtBirthday = (EditText) findViewById(R.id.nuevoUsuario_etxtBirthday);

        btnAceptar = (Button) findViewById(R.id.nuevoUsuario_btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void OnAddNewUser () {
        String username = etxtNombre.getText().toString();
        String birthday = etxtBirthday.getText().toString();
        String type = "newUser";

        BackgroundWorker worker = new BackgroundWorker(this);
        worker.execute(type, username, birthday);
    }
}
