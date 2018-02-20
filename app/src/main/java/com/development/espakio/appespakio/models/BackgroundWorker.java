package com.development.espakio.appespakio.models;

import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.widget.Toast;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.activities.Ingresar;
import com.development.espakio.appespakio.activities.MenuUsuarios;

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

/**
 * Created by Spectre 13-4107la on 15/02/2018.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    private Activity activity;
    private Ingresar ing;
    private Class clase;

    public BackgroundWorker (Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String user = strings[1];
        String pass = strings[2];
        String url = "http://espakio.tk/public/connect.php?type=";

        switch (type)
        {
            case "login":
                clase = MenuUsuarios.class;
                return connectionDB(user,pass,url+type);

            case "register":
                clase = MenuUsuarios.class;
                return connectionDB(user,pass,url+type);

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.isEmpty())
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
        else {
            nextActivity();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private String connectionDB (String user, String pass, String type_url){
        try {

            URL url = new URL(type_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            OutputStream oStrem = urlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(oStrem, "UTF-8"));
            String post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8")+"&"
                    +URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(pass, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            oStrem.close();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
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

    private void nextActivity() {
        activity.startActivity(new Intent(activity, clase));
        activity.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        activity.finish();
    }
}
