package com.example.RuFoos.extentions;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.RuFoos.LoginActivity;
import com.example.RuFoos.R;
import com.example.RuFoos.domain.Match;

public class MyAdapter extends ArrayAdapter<Match> {

    private final Context context;
    private final ArrayList<Match> list;

    public MyAdapter(Context context, ArrayList<Match> list) {

        super(context, R.layout.row, list);

        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // 3. Get the two text view from the rowView
        TextView winners = (TextView) rowView.findViewById(R.id.winners);
        TextView losers = (TextView) rowView.findViewById(R.id.losers);
        TextView time = (TextView) rowView.findViewById(R.id.time);
        TextView wentUnder = (TextView) rowView.findViewById(R.id.wentunder);

        // 4. Set the text for textView
        String win = "Winners: " + list.get(position).getWinners().get(0) + " and " + list.get(position).getWinners().get(1);
        String lose = "Losers: " + list.get(position).getLosers().get(0) + " and " + list.get(position).getLosers().get(1);

        winners.setText(win);
        losers.setText(lose);
        time.setText(list.get(position).getDate().toString());
        if(list.get(position).isUnderTable()){
            winners.setBackgroundColor(Color.GRAY);
            losers.setBackgroundColor(Color.GRAY);
            wentUnder.setText("Losers were sent under table.");
            wentUnder.setBackgroundColor(Color.GRAY);
            time.setBackgroundColor(Color.GRAY);
        }

        // 5. retrn rowView
        return rowView;
    }
}