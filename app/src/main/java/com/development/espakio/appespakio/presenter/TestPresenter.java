package com.development.espakio.appespakio.presenter;

import android.content.Context;

import com.development.espakio.appespakio.db.BackgroundWorker;
import com.development.espakio.appespakio.db.tblUsuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TestPresenter {
    private Context context;
    private String status;

    public TestPresenter(Context context){
        this.context=context;

    }

    public Number[] getDatos(String fecha){
        String type = "getTest";
        Number[] datos = new Number[0];
        int puntosMemo,puntosAten,puntosAgil;
        BackgroundWorker worker = new BackgroundWorker();

        tblUsuarios tblUsuarios = new tblUsuarios(context);
        int idUsuario = tblUsuarios.getUserSelec().getID();

        worker.execute(type, Integer.toString(idUsuario),fecha);

        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError()){
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonOb = jsonArray.getJSONObject(i);
                    puntosMemo = jsonArray.getInt(0);
                    puntosAten = jsonArray.getInt(1);
                    puntosAgil = jsonArray.getInt(2);
                    datos = new Number[] {0, puntosMemo, puntosAten, puntosAgil, 0};
                }
            }
        } catch (InterruptedException e) {e.printStackTrace();
        } catch (ExecutionException e) {e.printStackTrace();
        } catch (JSONException e) {e.printStackTrace();}

        return datos;
    }
    private boolean onFailed() {
        if(status.contains("Failed"))
            return true;
        return false;
    }

    private boolean onError() {
        if(status.contains("Error"))
            return true;
        return false;
    }


    private void insertDatos(){

    }
}
