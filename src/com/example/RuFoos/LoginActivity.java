package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;


/**
 * Created by Gadi on 2.11.2014.
 */

public class LoginActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String token = "token";
    public static final String user = "username";
    public SharedPreferences sharedpreferences;
    private EditText username, password;
    private AlertDialog.Builder dialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

    }

    @Override
    protected void onResume() {

        if (sharedpreferences.contains(token)) {
            Intent i = new Intent(this, FoosActivity.class);
            startActivity(i);

        }
        super.onResume();
    }

    public void login(View view) {

        switch(view.getId()){

            case (R.id.login):
                boolean invalid = false;
                if(username.getText().toString().equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if(invalid = false){
                    AsyncRunner mTask = new AsyncRunner();
                    mTask.execute();
                }
        }

        AsyncRunner mTask = new AsyncRunner();

        mTask.execute();


    }

    //Intent i = new Intent(this,com.example.RuFoos.FoosActivity.class);
    //startActivity(i);


    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }


    private class AsyncRunner extends AsyncTask<String, Integer, User> {


        User loginUser = new User(username.getText().toString(),null,password.getText().toString());

        @Override
        protected User doInBackground(String ...args) {

            final UserService userService = new UserServiceData();
            User user = userService.loginUser(loginUser);
            loginUser.setToken(user.getToken());

            return user;
        }

        @Override
        protected void onPostExecute(User resultUser) {
            //System.out.println(resultUser.getToken());
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", LoginActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", resultUser.getToken());
            editor.putString("username", resultUser.getUserName());
            editor.commit();
            Intent i = new Intent();
            startActivity(new Intent(getApplicationContext(), FoosActivity.class));
        }
    }

}
