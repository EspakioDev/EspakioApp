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

    public Number[][] getDatos(){
        String type = "getTest";
        Number[][] datos = null;

        int puntosMemo,puntosAten,puntosAgil;
        BackgroundWorker worker = new BackgroundWorker();

        tblUsuarios tblUsuarios = new tblUsuarios(context);
        int idUsuario = tblUsuarios.getUserSelec().getID();

        worker.execute(type, Integer.toString(idUsuario));

        try {
            String result = worker.get();
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getString("status");
            if(!onFailed() && !onError()){
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                datos = new Number[3][jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonOb = jsonArray.getJSONObject(i);
                    datos[0][i] = jsonOb.getInt("PuntajeMemo");
                    datos[1][i] = jsonOb.getInt("PuntajeAten");
                    datos[2][i] = jsonOb.getInt("PuntajeAgil");

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
