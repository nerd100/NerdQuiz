package com.example.dennis.nerdquiz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.dennis.nerdquiz.R;

/**
 * Created by Dennis on 25.08.2016.
 */
public class AddQuestion extends AppCompatActivity {

    EditText editQuestion, editRA, editFA1, editFA2, editFA3;
    Button btnAddData;
    Spinner editSpinner1,editSpinner2;
    String Question,Category,Difficulty,RA,FA1,FA2,FA3;
    boolean isInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);



        editSpinner1 = (Spinner) findViewById(R.id.spin);
        editSpinner2 = (Spinner) findViewById(R.id.spin2);
        editQuestion = (EditText) findViewById(R.id.editQuestion);
        editRA = (EditText) findViewById(R.id.editRightAnswer);
        editFA1 = (EditText) findViewById(R.id.editFalseAnswer1);
        editFA2 = (EditText) findViewById(R.id.editFalseAnswer2);
        editFA3 = (EditText) findViewById(R.id.editFalseAnswer3);
        btnAddData = (Button) findViewById(R.id.button_add);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo.isConnected()){
            btnAddData.setEnabled(true);
        }else{
            btnAddData.setEnabled(true);        //WICHTIG DAS true WIEDER zu false machen
        }

    }

    public void AddData(View view) {
        Question = editQuestion.getText().toString();
        Category = editSpinner1.getSelectedItem().toString();
        Difficulty = editSpinner2.getSelectedItem().toString();
        RA = editRA.getText().toString();
        FA1 = editFA1.getText().toString();
        FA2 = editFA2.getText().toString();
        FA3 = editFA3.getText().toString();
        String method = "Insert";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,Category,Difficulty,Question,RA,FA1,FA2,FA3);
        finish();
    }

}
