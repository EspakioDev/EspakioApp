package com.development.espakio.appespakio.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.Constants;
import com.development.espakio.appespakio.presenter.UserImagePresenter;
import com.development.espakio.appespakio.view.IUserImageView;

public class ImagenPerfilUsuario extends AppCompatActivity implements AdapterView.OnItemClickListener, IUserImageView {

    private GridView gridView;
    private UserImagePresenter userImagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_perfil_usuario);

        gridView = (GridView) findViewById(R.id.gvUsuarioImg);
        gridView.setOnItemClickListener(this);

        MyAdapter adapter =  new MyAdapter(this, R.layout.gridview, Constants.ID_IMAGEN);
        gridView.setAdapter(adapter);

        userImagePresenter = new UserImagePresenter(this, getApplicationContext());
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        userImagePresenter.setImageUser(i);
        // startActivity(new Intent(ImagenPerfilUsuario.this, NuevoUsuario.class));
        finish();
    }
}
