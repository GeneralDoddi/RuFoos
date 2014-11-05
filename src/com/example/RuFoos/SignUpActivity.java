package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gadi on 2.11.2014.
 */
public class SignUpActivity extends Activity {

    private AlertDialog.Builder dialog;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^[a-zA-Z0-9_-]{6,20}$";
    private Pattern pattern;
    private Matcher matcher;

    private EditText username;
    private EditText email;
    private EditText password;
    private UserService userService = new UserServiceData();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }


    public void signUp(View view) {

        AsyncRunner mTask = new AsyncRunner();
        mTask.execute();


        //Run api calls in threads like these


    }

    private class AsyncRunner extends AsyncTask<String, Integer, String> {
        String mTAG = "myAsyncTask";
        User user = new User();

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Verifying", Toast.LENGTH_LONG).show();

            //displayProgressBar("Loading....");


            user.setUserName(username.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            System.out.println(user.toString());
        }

        @Override
        protected String doInBackground(String... arg) {
            Log.d(mTAG, "Just started doing stuff in asynctask");
            if(user.getUserName() == ""){
                //System.out.println(user.toString());
                return "Username must be entered";
            }
            else  {

                User isExistingUser = userService.getUserByUsername(username.getText().toString());

                //System.out.println(isExistingUser.toString());

                if (isExistingUser == null) {
                    String userReturn = userService.addUser(user);

                    return userReturn;

                    //finish();

                } else {
                    return "Username exists";
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

    }

}




