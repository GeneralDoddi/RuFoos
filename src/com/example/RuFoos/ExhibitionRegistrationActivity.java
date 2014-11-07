package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.RuFoos.domain.ExhibitionMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thordurth on 6.11.2014.
 */
public class ExhibitionRegistrationActivity extends Activity {

    private EditText winner1,winner2,loser1,loser2;
    private Button registerMatch;
    private CheckBox underTable;
    private MatchService matchService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerexhibitionmatch);

        winner1 = (EditText)findViewById(R.id.editText1);
        winner2  = (EditText)findViewById(R.id.editText2);
        loser1 = (EditText)findViewById(R.id.editText3);
        loser2  = (EditText)findViewById(R.id.editText4);
        underTable = (CheckBox)findViewById(R.id.underTheTable);
        registerMatch = (Button)findViewById(R.id.registerMatch);


    }

    public void buttonClick(View view){


        AsyncRunner mTask = new AsyncRunner();

        mTask.execute();


    }

    public class AsyncRunner extends AsyncTask<String, Void, ExhibitionMatch> {
        List<String> winners = new ArrayList<String>();
        List<String> losers = new ArrayList<String>();
        ExhibitionMatch exhibitionMatch = new ExhibitionMatch();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            winners.add(winner1.getText().toString());
            winners.add(winner2.getText().toString());
            exhibitionMatch.setWinners(winners);

            losers.add(loser1.getText().toString());
            losers.add(loser2.getText().toString());
            exhibitionMatch.setLosers(losers);

            exhibitionMatch.setUnderTable(underTable.isChecked());

        }

        @Override
        protected ExhibitionMatch doInBackground(String ...args) {
            SharedPreferences sharedPreferences = getSharedPreferences
                    (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "error");
            matchService = new MatchServiceData();
            ExhibitionMatch result = matchService.registerExhibitionMatch(exhibitionMatch, token);
            return result;

        }

        @Override
        protected void onPostExecute(ExhibitionMatch result) {

            if(result == null) {
                String error = "There was an error, please remain calm";
                Toast.makeText(ExhibitionRegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
            }
            else {
                String success = "Registered";
                Toast.makeText(ExhibitionRegistrationActivity.this, success, Toast.LENGTH_SHORT).show();
                ExhibitionRegistrationActivity.this.finish();
            }
        }
    }
}

