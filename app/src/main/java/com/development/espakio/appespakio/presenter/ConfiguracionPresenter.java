package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfiguracionPresenter {
    private SharedPreferences confiPreferences;

    public ConfiguracionPresenter(Context context) {
        this.confiPreferences = context.getSharedPreferences("ConfiPreferences", Context.MODE_PRIVATE);
    }

    public boolean getConfig_Musica() {
        return confiPreferences.getBoolean("musica", true);
    }
    public boolean getConfig_Sonido() {
        return confiPreferences.getBoolean("sonido", true);
    }

    public void confiMusica(Boolean mus) {
        SharedPreferences.Editor editor = confiPreferences.edit();
        editor.putBoolean("musica", mus);
        editor.apply();
    }

    public void confiSonido(Boolean son){
        SharedPreferences.Editor editor = confiPreferences.edit();
        editor.putBoolean("sonido", son);
        editor.apply();
    }


}
