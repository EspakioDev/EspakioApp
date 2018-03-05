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
import com.development.espakio.appespakio.models.BackgroundWorker;
import com.development.espakio.appespakio.models.Cliente;
import com.development.espakio.appespakio.models.Usuario;

import java.lang.ref.Reference;

public class MenuUsuarios extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnUsuarioUno, btnUsuarioDos, btnUsuarioTres, btnUsuarioPlus;
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

        btnUsuarioDos = (ImageView) findViewById(R.id.menuUsu_imgUsuarioDos);
        btnUsuarioDos.setOnClickListener(this);

        btnUsuarioTres = (ImageView) findViewById(R.id.menuUsu_imgUsuarioTres);
        btnUsuarioTres.setOnClickListener(this);

        btnUsuarioPlus = (ImageView) findViewById(R.id.menuUsu_imgUsuarioPlus);
        btnUsuarioPlus.setOnClickListener(this);

        btnConfig = (Button) findViewById(R.id.menuUsu_btnConfig);
        btnConfig.setOnClickListener(this);

        cliente = (Cliente) getIntent().getExtras().getSerializable("cliente");
        //obtenerUsuarios();
        Toast.makeText(this, "La informacion que recibi"+cliente.IDtoString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "ID de Usuarios:"+cliente.IDUsuarios(), Toast.LENGTH_SHORT).show();

        int i = cliente.sizeUsuarios();

        switch (i) {
            case 0:
                btnUsuarioUno.setVisibility(View.INVISIBLE);
                btnUsuarioDos.setVisibility(View.INVISIBLE);
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 1:
                btnUsuarioDos.setVisibility(View.INVISIBLE);
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btnUsuarioTres.setVisibility(View.INVISIBLE);
                break;
            case 3:
                btnUsuarioPlus.setVisibility(View.INVISIBLE);
                break;
        }

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
                intent = new Intent(this, MenuJuegos.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", cliente.getUsuario(0));
                break;
            case R.id.menuUsu_imgUsuarioDos:
                intent = new Intent(this, MenuJuegos.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", cliente.getUsuario(1));
                break;
            case R.id.menuUsu_imgUsuarioTres:
                intent = new Intent(this, MenuJuegos.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", cliente.getUsuario(2));
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

    public void obtenerUsuarios() {
        String type = "getUsers";
        BackgroundWorker worker = new BackgroundWorker(this);
        worker.setClient(cliente);
        worker.execute(type, Integer.toString(cliente.getID()));
    }

}
