package com.example.RuFoos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.ArrayList;
import java.util.List;

public class QuickMatchActivity extends Activity{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickmatch);

        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.quickmatch, players);
        /*Resources res = getResources();
        String[] players = res.getStringArray(R.array.quickMatchPlayers);
        System.out.println("res: " + players);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.quickmatch, players);
        ListView listView = (ListView) findViewById(R.id.quickMatchList);
        listView.setAdapter(adapter);*/
    }

    public void leaveQuickMatch(View view) {
        new Thread(new Runnable() {
            public void run() {
                UserService service = new UserServiceData();
                // TODO: Make right user leave (logged in user)
                QuickMatch quickMatch = service.leaveQuickMatch(service.getUserByUsername("gadi"));
            }
        }).start();
        Context context = getApplicationContext();
        CharSequence text = "You signed out of quick match polling.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }
}
