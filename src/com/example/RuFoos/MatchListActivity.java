package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.RuFoos.domain.Match;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.extentions.MyAdapter;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gadi on 2.11.2014.
 */
public class MatchListActivity extends Activity {

    private List<String> matchlist = new ArrayList<String>();
    private String[] test = {"asdf", "asdæfkj", "asdlfjaweio"};
    private ArrayList<Match> displayMatches = new ArrayList<Match>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchlist);

        generateMatches();

        MyAdapter adapter = new MyAdapter(this, getMatches());
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }

    private ArrayList<Match> generateData(){
        ArrayList<Match> theseMatches = new ArrayList<Match>();
        List<String> winners = new ArrayList<String>();
        winners.add("gadi");
        winners.add("doddi");
        List<String> losers = new ArrayList<String>();
        losers.add("marino");
        losers.add("oli");
        theseMatches.add(new Match("1",4,winners,losers, new Date(),true,"winnerteam","loserteam"));
        theseMatches.add(new Match("1",4,losers,winners, new Date(),true,"winnerteam","loserteam"));
        return theseMatches;
    }

    private void generateMatches(){
        AsyncRunner getMatchesTask = new AsyncRunner();
        getMatchesTask.execute();

        /*new Thread(new Runnable() {
            public void run() {
                MatchService service = new MatchServiceData();

                SharedPreferences sharedpreferences = getSharedPreferences
                        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                String userName = sharedpreferences.getString("username", "error");
                if(userName != "error") {
                    List<Match> matches = service.getMatches(userName);
                    System.out.println("here " + matches);
                }
            }
        }).start();*/
        System.out.println("displayMatches1 " + displayMatches);
    }

    private ArrayList<Match> getMatches() {
        System.out.println("display3" + displayMatches);
        return displayMatches;
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();
    }

    private class AsyncRunner extends AsyncTask<String, Void, List<String>> {
        String mTAG = "getMatchesTask";
        ArrayList<Match> matches = new ArrayList<Match>();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<String> doInBackground(String...arg) {
            Log.d(mTAG, "Fetching matches");

            MatchService service = new MatchServiceData();

            SharedPreferences sharedpreferences = getSharedPreferences
                    (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String userName = sharedpreferences.getString("username", "error");
            if(userName != "error") {
                matches = service.getMatches(userName);
            }

            String username = sharedpreferences.getString("username","error");
            if(matches != null) {
                //matchlist = new String[matches.size()];
                for(int i = 0; i < matches.size(); i++) {
                    //if(matches.get(i).getWinnerteam() != null){
                        matchlist.add(matches.get(i).getWinners().get(0) + " and " +
                                      matches.get(i).getWinners().get(1) + " vs. " +
                                      matches.get(i).getLosers().get(0) + " and " +
                                      matches.get(i).getLosers().get(1));
                    //}
                }
            }
            displayMatches = matches;
            System.out.println("display 2 " + displayMatches);
            return matchlist;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            displayMatches(strings);
        }
    }

    public void displayMatches(List<String> strings){
        System.out.println("Strings " + strings);
        /*ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.matches, strings);

        ListView listView = (ListView) findViewById(R.id.match_list);
        listView.setAdapter(adapter);*/
    }


}
