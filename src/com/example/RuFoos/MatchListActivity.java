package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Gadi on 2.11.2014.
 */
public class MatchListActivity extends Activity {

    String[] matches = {"test1", "match2", "match4"};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchlist);

        getMatches();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.matches, matches);

        ListView listView = (ListView) findViewById(R.id.match_list);
        listView.setAdapter(adapter);
    }

    public void getMatches(){
        SharedPreferences sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String userName = sharedpreferences.getString("username", "error");
        if(userName != "error") {

        }

    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();


    }

}
