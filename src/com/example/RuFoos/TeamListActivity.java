package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.RuFoos.domain.Match;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.extentions.MyAdapter;
import com.example.RuFoos.extentions.TeamAdapter;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.team.TeamService;
import com.example.RuFoos.team.TeamServiceData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gadi on 2.11.2014.
 */
public class TeamListActivity extends Activity {

    private List<String> teamlist = new ArrayList<String>();
    private ArrayList<Team> displayTeams = new ArrayList<Team>();
    private Context context;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamlist);

        generateTeams();
        context = this;
    }

    private void generateTeams(){
        AsyncRunner getTeamsTask = new AsyncRunner();
        getTeamsTask.execute();
    }

    private ArrayList<Team> getTeams() {
        return displayTeams;
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();
    }

    private class AsyncRunner extends AsyncTask<String, Void, List<String>> {
        String mTAG = "getTeamsTask";
        ArrayList<Team> teams = new ArrayList<Team>();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<String> doInBackground(String...arg) {
            Log.d(mTAG, "Fetching matches");

            TeamService service = new TeamServiceData();

            SharedPreferences sharedpreferences = getSharedPreferences
                    (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

            String userName = sharedpreferences.getString("username", "error");
            if(userName != "error") {
                teams = service.getMyTeams(userName);
            }

            displayTeams = teams;
            return teamlist;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
             //TODO: make new myadapter
            TeamAdapter adapter = new TeamAdapter(context, getTeams());
            ListView listView = (ListView) findViewById(R.id.teamview);
            listView.setAdapter(adapter);
        }
    }
}
