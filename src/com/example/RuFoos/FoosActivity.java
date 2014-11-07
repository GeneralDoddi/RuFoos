package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.match.MatchService;
import com.example.RuFoos.match.MatchServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

public class FoosActivity extends Activity{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        moveTaskToBack(true);
        FoosActivity.this.finish();
    }

    @Override
    public void onBackPressed(){
        SharedPreferences sharedpreferences = getSharedPreferences
                (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("quickedUp", false);
        editor.commit();
        moveTaskToBack(true);
        FoosActivity.this.finish();
    }

    public void signUpForPickup(View view) {
        new Thread(new Runnable() {
            public void run() {
                MatchService service = new MatchServiceData();
                UserService userservice = new UserServiceData();
                // TODO: sign up logged in user
                SharedPreferences sharedPreferences = getSharedPreferences
                        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "error");
                String token = sharedPreferences.getString("token", "error");
                boolean quickedUp = sharedPreferences.getBoolean("quickedUp", false);

                if(username != "error"){
                    System.out.println("Quicked up " + quickedUp);
                    if(!quickedUp) {
                        QuickMatch quickMatch = service.quickMatchSignUp(userservice.getUserByUsername(username), token);

                        if(quickMatch != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("matchId", quickMatch.getId());
                            editor.putBoolean("quickedUp", true);
                            editor.commit();
                        }
                    }
                }
            }
        }).start();
        startActivity(new Intent(this, QuickMatchActivity.class));
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();
        if(id == R.id.challenge) {
            startActivity(new Intent(this, ChallengeActivity.class));
        }
        else if(id == R.id.matches) {
            startActivity(new Intent(this, MatchListActivity.class));
        }
        else if(id == R.id.teams) {

        }
        else if(id == R.id.competitions) {

        }
        else if(id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }
}
