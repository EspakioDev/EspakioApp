package com.development.espakio.appespakio.db;

import android.os.AsyncTask;

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

    private String type;

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
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8");
                    break;
                case "register":
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8");
                    break;
                case "newUser":
                    post_data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("fecha", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8")+"&"
                            + URLEncoder.encode("idCliente", "UTF-8")+"="+URLEncoder.encode(strings[3], "UTF-8")+"&"
                            + URLEncoder.encode("imagen", "UTF-8")+"="+URLEncoder.encode(strings[4], "UTF-8")+"&"
                            + URLEncoder.encode("vidas", "UTF-8")+"="+URLEncoder.encode(strings[5], "UTF-8")+"&"
                            + URLEncoder.encode("noJuegos", "UTF-8")+"="+URLEncoder.encode(strings[6], "UTF-8");
                    break;
                case "getUsers":
                    post_data = URLEncoder.encode("idCliente", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8");
                    break;
                case "getProgress":
                    post_data = URLEncoder.encode("idUsuario", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8");
                    break;
                case "setProgress":
                    post_data = URLEncoder.encode("idAvance", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("logros", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8")+"&"
                            + URLEncoder.encode("puntaje", "UTF-8")+"="+URLEncoder.encode(strings[3], "UTF-8");
                    break;
                case "getTest":
                    post_data = URLEncoder.encode("idUsuario", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8");
                    break;
                case "setImage":
                    post_data = URLEncoder.encode("idUsuario", "UTF-8")+"="+URLEncoder.encode(strings[1], "UTF-8")+"&"
                            + URLEncoder.encode("Image", "UTF-8")+"="+URLEncoder.encode(strings[2], "UTF-8");
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
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
