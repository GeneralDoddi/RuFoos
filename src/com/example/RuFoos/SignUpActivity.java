package com.example.RuFoos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void signUp(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText password2 = (EditText) findViewById(R.id.password);
        StringBuilder errorMsg = new StringBuilder();
        boolean error = false;
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Errors");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        pattern = Pattern.compile(USERNAME_PATTERN);
        // Username check
        matcher = pattern.matcher(username.getText());
        if(!matcher.matches()) {
            errorMsg.append("- Invalid username \n");
            error = true;
        }

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email.getText());
        if(!matcher.matches()) {
            errorMsg.append("- Invalid email address \n");
            error = true;
        }

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password.getText());
        if(!matcher.matches()) {
            errorMsg.append("- Password must contain 1 uppercase letter, 1 lowercase letter and a number. Password length must be 6 or more \n");
            error = true;
        }
        if(password != password2) {
            errorMsg.append("- Passwords don't match \n");
            error = true;
        }
        if(error){
            dialog.setMessage(errorMsg);
            dialog.show();
        }
        else {
            finish();
        }
    }
}
