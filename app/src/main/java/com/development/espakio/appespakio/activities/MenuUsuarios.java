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
import android.widget.ImageView;
import android.content.pm.ActivityInfo;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.models.Cliente;

public class MenuUsuarios extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnUsuarioUno, btnUsuarioPlus;
    private Button btnConfig;
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_menu_usuarios);

        btnUsuarioUno = (ImageView) findViewById(R.id.menuUsu_imgUsuarioUno);
        btnUsuarioUno.setOnClickListener(this);

        btnUsuarioPlus = (ImageView) findViewById(R.id.menuUsu_imgUsuarioPlus);
        btnUsuarioPlus.setOnClickListener(this);

        btnConfig = (Button) findViewById(R.id.menuUsu_btnConfig);
        btnConfig.setOnClickListener(this);

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
        Intent intent = new Intent(this, NuevoUsuario.class);
        switch (view.getId()) {
            case R.id.menuUsu_imgUsuarioPlus:
                intent = new Intent(this, NuevoUsuario.class);
                intent.putExtra("cliente", cliente);
                break;
            case R.id.menuUsu_imgUsuarioUno:
                intent = new Intent(this, SplashScreen3.class);
                break;
            case R.id.menuUsu_btnConfig:
                intent = new Intent(this, Configuraciones.class);
                //overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}
