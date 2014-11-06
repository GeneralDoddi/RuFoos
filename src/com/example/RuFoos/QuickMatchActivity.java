package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.ArrayList;
import java.util.List;

public class QuickMatchActivity extends Activity{

    // Array of strings...
    String[] countryArray = {"India", "Pakistan", "USA", "UK"};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickmatch);

        QuickMatch quickMatch;
        /*new Thread(new Runnable() {
            public void run() {
                MatchService service = new MatchServiceData();
                quickMatch = service.getQuickMatchById(id);
                if(quickMatch == null){
                    System.out.println("get quickmatch error");
                }
                else {
                    System.out.println("quickmatch get is all good");
                }
            }
        }).start();*/

        /*MatchService service = new MatchServiceData();
        QuickMatch quickMatch = service.getQuickMatchById(id);
        if(quickMatch == null){
            System.out.println("get quickmatch error");
        }
        else {
            System.out.println("quickmatch get is all good");
        }*/
        /*String array[] = new String[size];
        for(int i=0; i < size; i++)
            array[i] = sharedpreferences.getString("pickupPlayers_" + i, null);*/

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.quickmatch_playerlist, countryArray);

        ListView listView = (ListView) findViewById(R.id.country_list);
        listView.setAdapter(adapter);
    }

    public void leaveQuickMatch(View view) {
        new Thread(new Runnable() {
            public void run() {
                UserService userservice = new UserServiceData();
                MatchService service = new MatchServiceData();
                // TODO: Make right user leave (logged in user)
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

    //Method called on clicking button
    public void startTask(View view) {
        AsyncRunner pickupSignup = new AsyncRunner();

        // Fetch pickup players from preferences
        SharedPreferences sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String id = sharedpreferences.getString("matchId", "error");
        System.out.println("Preftest " + id);
        pickupSignup.execute(id);
    }

    private class AsyncRunner extends AsyncTask<String, Void, String[]> {
        String mTAG = "myAsyncTask";
        QuickMatch quickMatch;

        @Override
        protected void onPreExecute() {
            TextView output = (TextView)findViewById(R.id.output);
            output.setText("Hello from onPreExecute");
        }

        @Override
        protected String[] doInBackground(String...arg) {
            Log.d(mTAG, "Just started doing stuff in asynctask");

            MatchService service = new MatchServiceData();
            quickMatch = service.getQuickMatchById(arg[0]);
            if(quickMatch == null){
                System.out.println("get quickmatch error");
            }
            else {
                System.out.println("quickmatch get is all good");
            }

            String[] players = quickMatch.getPlayers();
            /*for(int i = 0; i <= 0; i++){
                System.out.println(players[i]);
            }*/
            /*try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*Log.d(mTAG, "I got "+arg.length+" arguments and they are: ");
            String result = null;
            for (int i = 0 ; i < arg.length ; i++ ) {
                result = arg[i]+",";
                Log.d(mTAG, (i+1)+" => "+arg[i]);
            }

            runOnUiThread(new Thread() {
                public void run() {
                    TextView output = (TextView) findViewById(R.id.output);
                    output.setText("I am done");
                }
            });

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            return players;
        }

        @Override
        protected void onPostExecute(String[] result) {
            Log.d(mTAG, "Inside onPostExecute");
            TextView output = (TextView) findViewById(R.id.output);
            output.setText("Result of the computation is: "+ result[0]);
        }
    }
}
