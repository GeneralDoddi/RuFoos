package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteammatch);

        //load teams
        getTeams();

        //Get spinners
        winTeam = (Spinner) findViewById(R.id.WinningTeam);
        loseTeam = (Spinner) findViewById(R.id.LosingTeam);

//        ArrayAdapter<Team> = ArrayAdapter.createFromResource(this, teamList, android.R.layout.simple_spinner_item);

        registerMatch = (Button)findViewById(R.id.registerMatch);
    }

    public void buttonClick(View view) {

       // MatchService service = new MatchServiceData();
        Button button = (Button) view;
        int id = button.getId();

        if(id == R.id.registerMatch){

            new Thread(new Runnable() {
                public void run() {
                    TeamMatch newMatch = new TeamMatch();

                    /*newMatch.setWinnerteam(winTeam.getText().toString());
                    newMatch.setLoserteam(loseTeam.get*/
                    newMatch.setUnderTable(underTable.isChecked());

                    SharedPreferences sharedPreferences = getSharedPreferences
                            (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "error");
                    System.out.println("token " + token);
                    if(token == "error") {
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
        }
    }

    public void getTeams()
    {
        new Thread(new Runnable() {
            public void run() {
                TeamService _teamService = new TeamServiceData();
                teamList = _teamService.getAllTeams();
                ArrayList<String> teamNames = new ArrayList<String>();

                for(Team i : teamList)
                {

                }
            }
        }).start();
    }


}
