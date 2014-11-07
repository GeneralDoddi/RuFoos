package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.domain.TeamMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.team.TeamService;
import com.example.RuFoos.team.TeamServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by thordurth on 6.11.2014.
 */
public class TeamMatchRegistrationActivity extends Activity {

    private Spinner winTeam, loseTeam;
    private CheckBox underTable;
    private Button registerMatch;
    private MatchService _matchService;
    private TeamService _teamService;
    private List<Team> teamList;
    private ArrayList<String> teamNames = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteammatch);

        new loadDataTask().execute();


        registerMatch = (Button)findViewById(R.id.registerMatch);
    }

    public void buttonClick(View view) {

       // MatchService service = new MatchServiceData();
        Button button = (Button) view;
        int id = button.getId();

        if(id == R.id.registerMatch){

           final boolean error = false;

            new Thread(new Runnable() {
                public void run() {
                    TeamMatch newMatch = new TeamMatch();
                    String t1 =  ((Spinner) findViewById(R.id.WinningTeam)).getSelectedItem().toString();
                    String t2 =  ((Spinner) findViewById(R.id.LosingTeam)).getSelectedItem().toString();

                   /* String t1 = "winningteam";
                    String t2 = "losingteam";*/
                    System.out.println(t1 + " - " + t2);
                    newMatch.setWinnerteam(t1);
                    newMatch.setLoserteam(t2);
                    newMatch.setUnderTable(underTable.isChecked());
                    System.out.println("checked? " +  underTable.isChecked());

                    SharedPreferences sharedPreferences = getSharedPreferences
                            (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "error");
                    System.out.println("token " + token);
                    if(token == "error") {
                        //error = true;
                        // TODO: throw error
                    }
                    else {
                        _matchService = new MatchServiceData();
                        UserService userservice = new UserServiceData();
                        MatchService service = new MatchServiceData();
                        // TODO: Make right user leave (logged in user)
                        System.out.println(newMatch);
                        _matchService.registerTeamMatch(newMatch, token);
                    }
                }
            }).start();

            CharSequence text = null;
            if(!error)            {
                text = "Results have been posted";
            }
            else{
                text = "Error posting results";
            }
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(TeamMatchRegistrationActivity.this, text, duration).show();
            TeamMatchRegistrationActivity.this.finish();
        }

    }

    private class loadDataTask extends AsyncTask<URL, Integer, Long> {

        protected Long doInBackground(URL... urls) {
            TeamService _teamService = new TeamServiceData();
            teamList = _teamService.getAllTeams();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

            winTeam = (Spinner) findViewById(R.id.WinningTeam);
            loseTeam = (Spinner) findViewById(R.id.LosingTeam);
            underTable = (CheckBox) findViewById(R.id.underTheTable);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeamMatchRegistrationActivity.this, android.R.layout.simple_spinner_item) {

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
            for (Team team : teamList) {
                adapter.add(team.getName());
            }
            adapter.add("Select team");
            winTeam.setAdapter(adapter);
            winTeam.setSelection(adapter.getCount()); //display hint
            loseTeam.setAdapter(adapter);
            loseTeam.setSelection(adapter.getCount()); //display hint
        }
    }




}
