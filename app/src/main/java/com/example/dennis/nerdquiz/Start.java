package com.example.dennis.nerdquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.ArrayList;
import java.util.Random;


public class Start extends AppCompatActivity {
    SharedPreferences shared_preferences;
    TextView question;
    Button btn1, btn2, btn3, btn4;
    ArrayList QuestionAndButtons;
    String[] QuestionAndButtonsParts;
    String firstQuestion = "";
    String whichQuiz = "";
    int rightAnswer = 0;
    Random rand = new Random();
    Random r = new Random();


    private ProgressDialog loading;
    private static final String JSON_ARRAY = "server_response";
    private JSONArray ques = null;

    int QuestionNumber = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        shared_preferences = getSharedPreferences("shared_preferences_test", MODE_PRIVATE);
        whichQuiz = shared_preferences.getString("Number", "Default");
        rightAnswer = r.nextInt(4);
        if(whichQuiz == "1") {
            getData("", "", String.valueOf(QuestionNumber));
        }else{
            String Diff,Cate;
            Diff = shared_preferences.getString("Difficulty", "Default");
            Cate = shared_preferences.getString("Category", "Default");
            getData(Diff, Cate, String.valueOf(QuestionNumber));
        }

    }

    private ArrayList extractJSON(String response) {
        ArrayList<String> list = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        try {
            JSONObject jsonObject = new JSONObject(response);
            ques = jsonObject.getJSONArray(JSON_ARRAY);        //Hier Parsen hopefully
            for (int i = 0; i < ques.length(); i++) {
                JSONObject jsonobject = ques.getJSONObject(i);
                String Question = jsonobject.getString("Question");
                String RA = jsonobject.getString("RA");
                String FA1 = jsonobject.getString("FA1");
                String FA2 = jsonobject.getString("FA2");
                String FA3 = jsonobject.getString("FA3");
                buffer.append(Question + ";");
                buffer.append(RA + ";");
                buffer.append(FA1 + ";");
                buffer.append(FA2 + ";");
                buffer.append(FA3 + ";");
                list.add(buffer.toString());
                buffer.delete(0, buffer.length());
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return list;
    }

    public String Question(ArrayList questionList) {
        String question;
        int i = rand.nextInt(questionList.size());
        question = questionList.get(i).toString();
        questionList.remove(i);
        return question;
    }

    public void next() {
        rightAnswer = r.nextInt(4);
        firstQuestion = Question(QuestionAndButtons);
        QuestionAndButtonsParts = firstQuestion.split(";");
        NameButtons();
    }

    public void NameButtons() {
        question = (TextView) findViewById(R.id.question1);
        question.setText(QuestionAndButtonsParts[0]);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        //mix all buttons
        mixButtons(rightAnswer, QuestionAndButtonsParts[1], QuestionAndButtonsParts[2], QuestionAndButtonsParts[3], QuestionAndButtonsParts[4]);

    }


    public void mixButtons(int i, final String RA, String FA1, String FA2, String FA3) {


            switch (i) {
                case 0:
                    btn1.setText(RA);
                    btn2.setText(FA1);
                    btn3.setText(FA2);
                    btn4.setText(FA3);
                    break;
                case 1:
                    btn1.setText(FA1);
                    btn2.setText(RA);
                    btn3.setText(FA2);
                    btn4.setText(FA3);
                    break;
                case 2:
                    btn1.setText(FA2);
                    btn2.setText(FA1);
                    btn3.setText(RA);
                    btn4.setText(FA3);
                    break;
                case 3:
                    btn1.setText(FA3);
                    btn2.setText(FA1);
                    btn3.setText(FA2);
                    btn4.setText(RA);
                    break;
                default:
                    break;
            }
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn1.getText() == RA) {
                        Toast.makeText(getApplicationContext(), "10Punkte", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            finish();
                            startActivity(new Intent(Start.this, MainActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            startActivity(new Intent(Start.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn2.getText() == RA) {
                        Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            finish();
                            startActivity(new Intent(Start.this, MainActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            startActivity(new Intent(Start.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn3.getText() == RA) {
                        Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            finish();
                            startActivity(new Intent(Start.this, MainActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            startActivity(new Intent(Start.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn4.getText() == RA) {
                        Toast.makeText(getApplicationContext(), "Korrekt", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            finish();
                            startActivity(new Intent(Start.this, MainActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Falsch", Toast.LENGTH_SHORT)
                                .show();
                        if (QuestionAndButtons.size() > 0) {
                            next();
                        } else {
                            startActivity(new Intent(Start.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            });

    }

    private void getData(String Diff,String Cate,String Num){
        final String Difficulty,Category,Number;
        Difficulty = Diff;
        Category = Cate;
        Number = Num;
     class GetDataJSON extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Start.this, "Downloading Quiz", "Wait....", true);
        }

        protected String doInBackground(String... params) {
            String JSON_STRING;
            String result = "";
            try {
                String json_url = "http://quizdb.net23.net/json_get_data.php";
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data =
                        URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(Category, "UTF-8") + "&" +
                        URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(Difficulty, "UTF-8") + "&" +
                URLEncoder.encode("Number", "UTF-8") + "=" + URLEncoder.encode(Number, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                result = stringBuilder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            QuestionAndButtons = extractJSON(result);
            firstQuestion = Question(QuestionAndButtons);
            QuestionAndButtonsParts = firstQuestion.split(";");

            NameButtons();
        }
    }
        GetDataJSON asyncObject = new GetDataJSON();
        asyncObject.execute("");
}
}