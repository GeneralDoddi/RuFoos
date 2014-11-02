package com.example.RuFoos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FoosActivity extends Activity{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();

        if (id == R.id.quickMatch) {
            // TODO: add this user to quick match pool
            Toast.makeText(this, "You've been signed up for a quick match", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.challenge) {
            startActivity(new Intent(this, ChallengeActivity.class));
        }
        else if(id == R.id.matches) {

        }
        else if(id == R.id.teams) {

        }
        else if(id == R.id.competitions) {

        }
        else if(id == R.id.settings) {

        }
    }
}
