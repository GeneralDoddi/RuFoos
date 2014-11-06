package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
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
public class SignUpActivity extends Activity {

    private AlertDialog.Builder dialog;


    private EditText username;
    private EditText email;
    private EditText password;
    private EditText password2;
    private UserService userService = new UserServiceData();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
    }


    public void signUp(View view) {

        switch (view.getId()) {
            case (R.id.signup):
                boolean invalid = false;

                if (username.getText().toString().equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Please enter a userame", Toast.LENGTH_SHORT).show();
                } else if (password.getText().equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }else if(!((password2.getText().toString()).equals(password.getText().toString()))){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
                } else if (email.getText().equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Please enter your Email ID", Toast.LENGTH_SHORT).show();
                }
                else if (invalid == false) {
                    AsyncRunner mTask = new AsyncRunner();

                    mTask.execute();
                }
                break;
            case (R.id.cancel):
                SignUpActivity.this.finish();
                break;
        }

    }

    private class AsyncRunner extends AsyncTask<String, Integer, String> {

        User user = new User();

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Verifying", Toast.LENGTH_SHORT).show();
            user.setUserName(username.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
        }

        @Override
        protected String doInBackground(String... arg) {

            String userReturn = null;

            User isExistingUser = userService.getUserByUsername(username.getText().toString());
            if (isExistingUser == null) {
                userReturn = userService.addUser(user);
                if (userReturn.equals("Sucessfully Registered")) {
                    return userReturn;
                }
                else{
                    return userReturn;
                }
            }
            else{
                return "Username already exists";

            }

        }
        @Override
        protected void onPostExecute(String result) {

            if(result.equals("Sucessfully Registered")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }
}




