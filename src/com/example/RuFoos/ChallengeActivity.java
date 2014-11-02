package com.example.RuFoos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gadi on 2.11.2014.
 */
public class ChallengeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        int id = button.getId();

        if (id == R.id.challengePlayer) {

        }
        else if (id == R.id.challengeTeam) {
            startActivity(new Intent(this, SignUpActivity.class));
        }
        else if(id == R.id.myChallenges) {

        }
    }
}
