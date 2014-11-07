package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.RuFoos.domain.ExhibitionMatch;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.domain.TeamMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.team.TeamService;
import com.example.RuFoos.team.TeamServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

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
    private ArrayList<String> teamNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteammatch);



        //load teams
        getTeams();

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
                    //Team t1 = (Team) ((Spinner) findViewById(R.id.WinningTeam)).getSelectedItem();
                    Team t2 = (Team) ((Spinner) findViewById(R.id.LosingTeam)).getSelectedItem();
                    //newMatch.setWinnerteam(t1.getName());
                    newMatch.setLoserteam(t2.getName());
                    newMatch.setUnderTable(underTable.isChecked());

                    SharedPreferences sharedPreferences = getSharedPreferences
                            (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "error");
                    System.out.println("token " + token);
                    if(token == "error") {
                        error = true;
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
        if(id == R.id.pickWinner)
        {
            //Create win Dialog
            createWinDialog();
        }
    }

    public void getTeams()
    {
        new Thread(new Runnable() {
            public void run() {
                TeamService _teamService = new TeamServiceData();
                teamList = _teamService.getAllTeams();
                System.out.println(teamList.size());

            }
        }).start();
    }

    public void createWinDialog()
    {

    }


}
