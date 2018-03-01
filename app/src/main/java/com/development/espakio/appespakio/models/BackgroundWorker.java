package com.development.espakio.appespakio.models;

import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.activities.Ingresar;
import com.development.espakio.appespakio.activities.MenuJuegos;
import com.development.espakio.appespakio.activities.MenuUsuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 15/02/2018.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    private Activity activity;
    private Ingresar ing;
    private Class clase;
    private String type;
    private Cliente cliente;
    private Usuario usuario;
    private String[] datos;

    public BackgroundWorker (Activity activity) {
        this.activity = activity;
    }

    public void setClient(Cliente client){
        this.cliente = client;
    }
    @Override
    protected String doInBackground(String... strings) {
        type = strings[0];
        String type_url = "http://espakio.tk/public/connect.php?type=";

        try {

            URL url = new URL(type_url+type);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            OutputStream oStrem = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(oStrem, "UTF-8"));

            String post_data = "";

            switch (type)
            {
                case "login":
                    datos = new String[]{strings[1]};
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8");
                    clase = MenuUsuarios.class;
                    break;
                case "register":
                    datos = new String[]{strings[1]};
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8");
                    clase = MenuUsuarios.class;
                    break;
                case "newUser":
                    datos = new String[]{strings[1], strings[2]};
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("fecha", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8")+"&"
                            + URLEncoder.encode("idCliente", "UTF-8")+"="+URLEncoder.encode(strings[3], "UTF-8");
                    clase = MenuJuegos.class;
                    break;
                case "getUsers":
                    post_data = URLEncoder.encode("idCliente", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8");
                    break;
            }

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            oStrem.close();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            urlConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if(status.contains("Failed") || status.contains("Error")) {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
            else {
                switch (type){
                    case "login":
                        cliente = new Cliente(jsonObject.getInt("result"));
                        nextActivity();
                        break;
                    case "register":
                        cliente = new Cliente(jsonObject.getInt("result"));
                        nextActivity();
                        break;
                    case "newUser":
                        cliente.nuevoUsuario(jsonObject.getInt("result"), datos[0], datos[1]);
                        usuario = cliente.ultimoUsuario();
                        nextActivity();
                        break;
                    case "getUsers":
                        String result = jsonObject.getString("result");
                        String ids = "";
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonOb = jsonArray.getJSONObject(i);
                            cliente.nuevoUsuario(jsonOb.getInt("idUsuario"), jsonOb.getString("Usuario"),
                                    jsonOb.getString("Fecha_Nacimiento"), jsonOb.getString("Imagen"),
                                    jsonOb.getInt("Vidas"), jsonOb.getInt("Logros"));
                            ids += jsonOb.getString("idUsuario") + ",";
                        }
                        Toast.makeText(activity, ids, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        ;
                }
            }
        } catch (JSONException e) {e.printStackTrace();}
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void nextActivity() {
        Intent intent = new Intent(activity, clase);
        switch (type) {
            case "login":
                intent.putExtra("cliente", cliente);
                break;
            case "register":
                intent.putExtra("cliente", cliente);
                break;
            case "newUser":
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                break;
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        activity.finish();
    }


}
