package com.example.dennis.nerdquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by Dennis on 06.09.2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx;
    String json_url;
    String JSON_STRING;
    public String json_string;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }

    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        json_url = "http://quizdb.net23.net/json_get_data.php";
    }

    @Override
    protected String doInBackground(String... params) {
        if(params[0] == "Insert") {
            String insert_url = "http://quizdb.net23.net/insert.php";
            String method = params[0];
            if (method.equals("Insert")) {
                String Category = params[1];
                String Difficulty = params[2];
                String Question = params[3];
                String RA = params[4];
                String FA1 = params[5];
                String FA2 = params[6];
                String FA3 = params[7];

                try {
                    URL url = new URL(insert_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(Category, "UTF-8") + "&" +
                            URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(Difficulty, "UTF-8") + "&" +
                            URLEncoder.encode("Question", "UTF-8") + "=" + URLEncoder.encode(Question, "UTF-8") + "&" +
                            URLEncoder.encode("RA", "UTF-8") + "=" + URLEncoder.encode(RA, "UTF-8") + "&" +
                            URLEncoder.encode("FA1", "UTF-8") + "=" + URLEncoder.encode(FA1, "UTF-8") + "&" +
                            URLEncoder.encode("FA2", "UTF-8") + "=" + URLEncoder.encode(FA2, "UTF-8") + "&" +
                            URLEncoder.encode("FA3", "UTF-8") + "=" + URLEncoder.encode(FA3, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    return "Data Inserted";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }else{
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //Toast.makeText(ctx,JSON_STRING,Toast.LENGTH_LONG).show();

                return stringBuilder.toString().trim();


            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
/*
    protected String doInBackground() {
        String json_url;
        String JSON_STRING;
        json_url = "http://quizdb.net23.net/json_get_data.php";
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine())!=null){
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuilder.toString().trim();

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
