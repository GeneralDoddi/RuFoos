package com.example.RuFoos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.TeamMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;


/**
 * Created by thordurth on 6.11.2014.
 */
public class TeamMatchRegistrationActivity extends Activity {

    private EditText winnerteam,loserteam;
    private CheckBox underTable;
    private Button registerMatch;
    private MatchService matchService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerteammatch);

        winnerteam = (EditText)findViewById(R.id.winnerText);
        loserteam  = (EditText)findViewById(R.id.loserText);
        underTable = (CheckBox)findViewById(R.id.checkBox);
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

                    newMatch.setWinnerteam(winnerteam.getText().toString());
                    newMatch.setLoserteam(loserteam.getText().toString());
                    newMatch.setUnderTable(underTable.isChecked());

                    matchService = new MatchServiceData();
                    matchService.registerTeamMatch(newMatch);
                }
            }).start();
        }
    }


}
