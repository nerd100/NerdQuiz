package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SirLui on 09.09.2016.
 */
public class Score extends Activity {
    SharedPreferences shared_preferences;

    TextView rightAnswer,wrongAnswer,points;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        shared_preferences = getSharedPreferences("shared_preferences_test",
                MODE_PRIVATE);

        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        wrongAnswer = (TextView) findViewById(R.id.wrongAnswer);
        points = (TextView) findViewById(R.id.points);

        rightAnswer.setText(shared_preferences.getString("countRightAnswers","Default"));
        wrongAnswer.setText(shared_preferences.getString("countWrongAnswers","Default"));
        points.setText(shared_preferences.getString("countNerdIQ","Default"));
    }

}
