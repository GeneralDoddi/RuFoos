package com.example.RuFoos.extentions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.RuFoos.R;
import com.example.RuFoos.domain.Team;

import java.util.ArrayList;

public class TeamAdapter extends ArrayAdapter<Team> {

    private final Context context;
    private final ArrayList<Team> list;

    public TeamAdapter(Context context, ArrayList<Team> list) {

        super(context, R.layout.teamrow, list);

        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.teamrow, parent, false);

        TextView team = (TextView) rowView.findViewById(R.id.teamname);
        TextView players = (TextView) rowView.findViewById(R.id.p);
        TextView wins = (TextView) rowView.findViewById(R.id.wins);
        TextView losses = (TextView) rowView.findViewById(R.id.losses);
        TextView table = (TextView) rowView.findViewById(R.id.table);

        team.setText(list.get(position).getName());
        players.setText(list.get(position).getP1() + " and " + list.get(position).getP2());
        wins.setText("Wins: " + list.get(position).getWins());
        losses.setText("Losses: " + list.get(position).getLosses());
        if(list.get(position).getUnderTable() == 1) {
            table.setText("This team has gone under the table " + list.get(position).getUnderTable() + " time.");
        }
        else if(list.get(position).getUnderTable() > 1) {
            table.setText("This team has gone under the table " + list.get(position).getUnderTable() + " times...");
        }
        else {
            table.setText("This team has never gone under the table!!!");
        }

        return rowView;
    }
}