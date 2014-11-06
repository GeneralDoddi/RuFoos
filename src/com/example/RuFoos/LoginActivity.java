package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.List;

/**
 * Created by Gadi on 2.11.2014.
 */
public class LoginActivity extends Activity {

    private EditText username,password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey";
    public static final String pass = "passwordKey";
    SharedPreferences sharedpreferences;
    private AlertDialog.Builder dialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

    }
    @Override
    protected void onResume() {
        sharedpreferences=getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name))
        {
            if(sharedpreferences.contains(pass)){
                Intent i = new Intent(this,
                        FoosActivity.class);
                startActivity(i);
            }
        }
        super.onResume();
    }

    public void login(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String u = username.getText().toString();
        String p = password.getText().toString();

        //TODO: check if user exists and password is right
        boolean allGood = true;
        if(allGood) {
            System.out.println("Logging in");
            startActivity(new Intent(this, FoosActivity.class));
        }
        else {
            dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Invalid username/password");
            dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            dialog.show();
        }

        final UserService userService = new UserServiceData();
        editor.putString(name, u);
        editor.putString(pass, p);
        editor.commit();
        Intent i = new Intent(this,com.example.RuFoos.FoosActivity.class);
        startActivity(i);
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
