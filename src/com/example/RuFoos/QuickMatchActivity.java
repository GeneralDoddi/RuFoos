package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.RuFoos.domain.ExhibitionMatch;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;

import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class QuickMatchActivity extends Activity{
    private Timer autoUpdate;
    private AlertDialog.Builder dialog;
    private boolean isFull = false;
    private Vibrator v;
    private boolean isReady = false;
    private boolean confirmed = false;
    private boolean hasPopped = false;
    private List<String> winners = new ArrayList<String>();
    private List<String> losers = new ArrayList<String>();
    private String[] matchPlayers;
    private boolean underTable = false;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickmatch);
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("QuickMatch ready");
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: post to api
                confirmed = true;
                confirmReady();
            }
        });
        dialog.setNegativeButton("Leave quickMatch", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                leaveQuickMatch();
            }
        });
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

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
        }, 0, 2000); // updates every 2 seconds
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
        if(isFull){
            //autoUpdate.cancel();

            if(!confirmed) {
                if(v.hasVibrator()){
                    v.vibrate(500);
                }
                dialog.show();
                hasPopped = true;
            }
        }

        if(isReady){
            System.out.println("You're ready now!");
            autoUpdate.cancel();
        }
    }

    public void confirmReady() {
        new Thread(new Runnable() {
            public void run() {
                MatchService service = new MatchServiceData();
                // TODO: sign up logged in user
                SharedPreferences sharedPreferences = getSharedPreferences
                        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "error");
                System.out.println("token " + token);
                if(token == "error") {
                    // TODO: throw error
                }
                else {
                    System.out.println("Token " + token);
                    QuickMatch quickMatch = service.confirmPickup(token);
                }
            }
        }).start();
        View button = findViewById(R.id.leaveQuickmatch);
        button.setVisibility(View.INVISIBLE);
    }

    public void leaveQuickMatch() {
        new Thread(new Runnable() {
            public void run() {
                UserService userservice = new UserServiceData();
                MatchService service = new MatchServiceData();
                // TODO: Make right user leave (logged in user)
                // TODO: remove matchId from sharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences
                        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                //String username = sharedPreferences.getString("name", "error");
                QuickMatch quickMatch = service.leaveQuickMatch(userservice.getUserByUsername("gadi"));
                // QuickMatch quickMatch = service.leaveQuickMatch(userservice.getUserByUsername(username));
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
            matchPlayers = quickMatch.getPlayers();
            for(int i = 0; i < quickMatch.getPlayers().length; i++) {
                players[i] = i+1 + ". " + quickMatch.getPlayers()[i];
                System.out.println("player " + players[i]);
            }
            if(quickMatch.isFull()){
                isFull = true;
                System.out.println("match is full man");
            }
            else {
                for(int i = quickMatch.getPlayers().length; i < 4; i++) {
                    players[i]= i+1 + ". Waiting for player...";
                }
            }
            System.out.println("IsReady? " + quickMatch.getReady().length);
            if(quickMatch.getReady().length >= 4) {
                System.out.println("Ready!");
                isReady = true;
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

    public void onButtonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();
        if(id == R.id.leaveQuickmatch) {
            leaveQuickMatch();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_p1:
                if(checked) {
                    System.out.println("Adding p1 to winners.");
                    winners.add(matchPlayers[0]);
                }
                else{
                    System.out.println("removing p1");
                    int location = winners.indexOf(matchPlayers[0]);
                    winners.remove(location);
                }
                break;
            case R.id.checkbox_p2:
                if(checked) {
                    System.out.println("Adding p1 to winners.");
                    winners.add(matchPlayers[1]);
                }
                else{
                    System.out.println("removing p1");
                    int location = winners.indexOf(matchPlayers[1]);
                    winners.remove(location);
                }
                break;
            case R.id.checkbox_p3:
                if(checked) {
                    System.out.println("Adding p1 to winners.");
                    winners.add(matchPlayers[2]);
                }
                else{
                    System.out.println("removing p1");
                    int location = winners.indexOf(matchPlayers[2]);
                    winners.remove(location);
                }
                break;
            case R.id.checkbox_p4:
                if(checked) {
                    System.out.println("Adding p1 to winners.");
                    winners.add(matchPlayers[3]);
                }
                else{
                    System.out.println("removing p1");
                    int location = winners.indexOf(matchPlayers[3]);
                    winners.remove(location);
                }
                break;
            case R.id.underTable:
                underTable = true;
                break;

        }
    }

    public void submitResults(View view){
        if(winners.size() == 2){
            // TODO: post results
            for(int i = 0; i < matchPlayers.length; i++) {
                if(!winners.contains(matchPlayers[i])){
                    losers.add(matchPlayers[i]);
                }
            }
            autoUpdate.cancel();
            System.out.println("Winners " + winners);
            System.out.println("Losers " + losers);

            new Thread(new Runnable() {
                public void run() {
                    ExhibitionMatch match = new ExhibitionMatch();
                    match.setLosers(losers);
                    match.setWinners(winners);
                    match.setUnderTable(underTable);
                    MatchService service = new MatchServiceData();
                    SharedPreferences sharedPreferences = getSharedPreferences
                            (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "error");
                    System.out.println("token " + token);
                    if(token == "error") {
                        // TODO: throw error
                    }
                    else {
                        System.out.println("Token " + token);
                        match = service.registerExhibitionMatch(match, token);
                    }

                }
            }).start();

        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "There can only be two winners.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
