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
                String username = sharedPreferences.getString("name", "error");
                //if(username != "error"){
                    service.leaveQuickMatch(userservice.getUserByUsername("gadi"));
                    //service.leaveQuickMatch(userservice.getUserByUsername(username));
                    QuickMatch quickMatch = service.quickMatchSignUp(userservice.getUserByUsername("gadi"));
                    //QuickMatch quickMatch = service.quickMatchSignUp(userservice.getUserByUsername(username));

                    if(quickMatch != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("matchId", quickMatch.getId());
                        editor.commit();
                    }
                //}
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
