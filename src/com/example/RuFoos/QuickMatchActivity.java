package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.Timer;
import java.util.TimerTask;


public class QuickMatchActivity extends Activity{
    private Timer autoUpdate;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickmatch);

        queryList();
    }

    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        queryList();
                    }
                });
            }
        }, 0, 10000); // updates every 10 seconds
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }

    public void queryList(){
        AsyncRunner pickupSignup = new AsyncRunner();
        SharedPreferences sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String id = sharedpreferences.getString("matchId", "error");
        if(id != "error") {
            pickupSignup.execute(id);
        }
    }

    public void leaveQuickMatch(View view) {
        new Thread(new Runnable() {
            public void run() {
                UserService userservice = new UserServiceData();
                MatchService service = new MatchServiceData();
                // TODO: Make right user leave (logged in user)
                // TODO: remove matchId from sharedpreferences
                QuickMatch quickMatch = service.leaveQuickMatch(userservice.getUserByUsername("gadi"));
            }
        }).start();
        Context context = getApplicationContext();
        CharSequence text = "You signed out of quick match polling.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        QuickMatchActivity.this.finish();
    }

    private class AsyncRunner extends AsyncTask<String, Void, String[]> {
        String mTAG = "pickupSignup";
        QuickMatch quickMatch;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String...arg) {
            Log.d(mTAG, "Fetching players");

            MatchService service = new MatchServiceData();
            System.out.println("argument 0 is " + arg[0]);
            quickMatch = service.getQuickMatchById(arg[0]);
            if(quickMatch == null){
                System.out.println("get quickmatch error");
            }
            else {
                System.out.println("quickmatch get is all good");
                System.out.println("getByIdResult: " + quickMatch.getId() + " " + quickMatch.getPlayers() + " " + quickMatch.getVersion() + " " + quickMatch.isFull());
            }

            System.out.println("players in quickmatch " + quickMatch.getPlayers());
            String[] players = new String[4];
            for(int i = 0; i < quickMatch.getPlayers().length; i++) {
                players[i] = i+1 + ". " + quickMatch.getPlayers()[i];
                System.out.println("player " + players[i]);
            }
            for(int i = quickMatch.getPlayers().length; i < 4; i++) {
                players[i]= i+1 + ". Waiting for player...";
            }
            return players;
        }

        @Override
        protected void onPostExecute(String[] result) {
            displayList(result);
        }
    }

    public void displayList(String[] players) {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.quickmatch_playerlist, players);

        ListView listView = (ListView) findViewById(R.id.player_list);
        listView.setAdapter(adapter);
    }
}
