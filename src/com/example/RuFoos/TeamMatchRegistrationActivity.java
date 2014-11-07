package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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

        Spinner winTeam = (Spinner) findViewById(R.id.WinningTeam);
        Spinner loseTeam = (Spinner) findViewById(R.id.LosingTeam);
        //load teams
        getTeams();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.registerteammatch, teamNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //THIS IS ALL BROKEN FUUUUUUUUUUUUUUUEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe.. e
        //winTeam.setAdapter(adapter);
        //loseTeam.setAdapter(adapter);
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
                    Team t1 = (Team) ((Spinner) findViewById(R.id.WinningTeam)).getSelectedItem();
                    Team t2 = (Team) ((Spinner) findViewById(R.id.LosingTeam)).getSelectedItem();
                    newMatch.setWinnerteam(t1.getName());
                    newMatch.setLoserteam(t2.getName());
                    newMatch.setUnderTable(underTable.isChecked());

                    _matchService = new MatchServiceData();
                    UserService userservice = new UserServiceData();
                    MatchService service = new MatchServiceData();
                    // TODO: Make right user leave (logged in user)
                    System.out.println(newMatch);
                    _matchService.registerTeamMatch(newMatch);
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


                for(Team i : teamList)
                {
                    teamNames.add(i.getName());
                    System.out.println(i.getName());
                }

                System.out.println(teamList.size());
                //Get spinners

            }
        }).start();
    }

    public void addItemsOnSpinner()
    {





    }


}
