package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.RuFoos.domain.ExhibitionMatch;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thordurth on 6.11.2014.
 */
public class ExhibitionRegistrationActivity extends Activity {

    private Spinner winner1, winner2, loser1, loser2;
    private Button registerMatch;
    private CheckBox underTable;
    private MatchService matchService;

    private List<User> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerexhibitionmatch);

        winner1 = (Spinner) findViewById(R.id.winningUser1);
        winner2 = (Spinner) findViewById(R.id.winningUser2);
        loser1 = (Spinner) findViewById(R.id.losingUser1);
        loser2 = (Spinner) findViewById(R.id.losingUser2);
        underTable = (CheckBox) findViewById(R.id.underTheTable);
        registerMatch = (Button) findViewById(R.id.registerMatch);

        MountUserList mTask = new MountUserList();
        mTask.execute();

    }

    public void buttonClick(View view) {

        Button button = (Button) view;
        int id = button.getId();

        if (id == R.id.registerQuickMatch) {

            String ble = ((Spinner) findViewById(R.id.winningUser2)).getSelectedItem().toString();
            String errormsg = "User field cannot be left blank";
            boolean error = false;

            if(((Spinner) findViewById(R.id.winningUser1)).getSelectedItem().toString() == "Select username") {
                error = true;
                Toast.makeText(ExhibitionRegistrationActivity.this, errormsg, Toast.LENGTH_SHORT).show();
            }
            else if(((Spinner) findViewById(R.id.winningUser2)).getSelectedItem().toString() == "Select username") {
                error = true;
                Toast.makeText(ExhibitionRegistrationActivity.this, errormsg, Toast.LENGTH_SHORT).show();
            }
            else if(((Spinner) findViewById(R.id.losingUser1)).getSelectedItem().toString() == "Select username") {
                error = true;
                Toast.makeText(ExhibitionRegistrationActivity.this, errormsg, Toast.LENGTH_SHORT).show();
            }
            else if(((Spinner) findViewById(R.id.losingUser2)).getSelectedItem().toString().equals("Select username") ) {
                error = true;
                Toast.makeText(ExhibitionRegistrationActivity.this, errormsg, Toast.LENGTH_SHORT).show();
            }
            else if(error == false){
                PostAsyncRunner runner = new PostAsyncRunner();
                runner.execute();
            }

        }
    }

    public void addItemOnSpinners() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExhibitionRegistrationActivity.this, android.R.layout.simple_spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (User user : userList) {
            adapter.add(user.getUserName());
        }
        adapter.add("Select username");

        winner1.setAdapter(adapter);
        winner1.setSelection(adapter.getCount()); //display hint
        winner2.setAdapter(adapter);
        winner2.setSelection(adapter.getCount()); //display hint
        loser1.setAdapter(adapter);
        loser1.setSelection(adapter.getCount()); //display hint
        loser2.setAdapter(adapter);
        loser2.setSelection(adapter.getCount()); //display hint



    }

    public class PostAsyncRunner extends AsyncTask<String, Void, ExhibitionMatch> {
        List<String> winners = new ArrayList<String>();
        List<String> losers = new ArrayList<String>();
        ExhibitionMatch exhibitionMatch = new ExhibitionMatch();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            winners.add(((Spinner) findViewById(R.id.winningUser1)).getSelectedItem().toString());
            winners.add(((Spinner) findViewById(R.id.winningUser2)).getSelectedItem().toString());
            exhibitionMatch.setWinners(winners);

            losers.add(((Spinner) findViewById(R.id.losingUser1)).getSelectedItem().toString());
            losers.add(((Spinner) findViewById(R.id.losingUser2)).getSelectedItem().toString());
            exhibitionMatch.setLosers(losers);

            exhibitionMatch.setUnderTable(underTable.isChecked());

        }

        @Override
        protected ExhibitionMatch doInBackground(String... args) {


            SharedPreferences sharedPreferences = getSharedPreferences
                    (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "error");
            matchService = new MatchServiceData();
            ExhibitionMatch result = matchService.registerExhibitionMatch(exhibitionMatch, token, null);
            return result;

        }

        @Override
        protected void onPostExecute(ExhibitionMatch result) {

            if (result == null) {
                String error = "There was an error, please remain calm";
                Toast.makeText(ExhibitionRegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
            } else {
                String success = "Registered";
                Toast.makeText(ExhibitionRegistrationActivity.this, success, Toast.LENGTH_SHORT).show();
                ExhibitionRegistrationActivity.this.finish();
            }
        }
    }

    public class MountUserList extends AsyncTask<String, Void, List> {

        @Override
        protected List<Team> doInBackground(String... args) {
            UserService userService = new UserServiceData();
            List list = userService.getAllUsers();
            return list;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            userList = list;
            addItemOnSpinners();
        }
    }
}

