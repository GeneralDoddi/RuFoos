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
                //service.leaveQuickMatch(userservice.getUserByUsername("marino"));
                QuickMatch quickMatch = service.quickMatchSignUp(userservice.getUserByUsername("marino"));

                SharedPreferences sharedPreferences = getSharedPreferences
                        (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("matchId", quickMatch.getId());
                editor.commit();

                /*if(quickMatch != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences
                            (LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("pickupPlayers_size", quickMatch.getPlayers().length);
                    for(int i=0; i < quickMatch.getPlayers().length; i++){
                        System.out.println("player " + i + " " + quickMatch.getPlayers()[i]);
                        editor.putString("pickupPlayers_" + i, quickMatch.getPlayers()[i]);
                    }
                    editor.commit();
                }*/

                //System.out.println("Quickmatch " + quickMatch);
 /*               if(quickMatch == null) {
                    // TODO: throw error
                }
   */         }
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
