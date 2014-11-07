package com.example.RuFoos;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.team.TeamService;
import com.example.RuFoos.team.TeamServiceData;
import com.example.RuFoos.user.UserService;
import com.example.RuFoos.user.UserServiceData;

import java.util.List;

/**
 * Created by BearThor on 7.11.2014.
 */
public class AddTeamActivity extends Activity {
    public List<User>userList;
    private Spinner user1,user2;
    private EditText teamname;
    TeamService service = new TeamServiceData();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addteam);
        user1 = (Spinner) findViewById(R.id.user1);
        user2 = (Spinner) findViewById(R.id.user2);
        teamname = (EditText) findViewById(R.id.teamname);
        MountUserList mTask = new MountUserList();
        mTask.execute();

    }
    public void addItemOnSpinners() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTeamActivity.this, android.R.layout.simple_spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (User user : userList) {
            adapter.add(user.getUserName());
        }
        adapter.add("Select username");

        user1.setAdapter(adapter);
        user1.setSelection(adapter.getCount()); //display hint
        user2.setAdapter(adapter);
        user2.setSelection(adapter.getCount()); //display hint



    }
    public class MountUserList extends AsyncTask<String, Void, List> {

        @Override
        protected List<Team> doInBackground(String... args) {
            UserService userService = new UserServiceData();
            List list = userService.getAllUsers();
            return list;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            userList = list;
            addItemOnSpinners();
        }
    }

}
