package com.example.RuFoos.domain;

import java.util.Date;

/**
 * Domain class for matches in ruFoos
 * Created by BearThor on 2.11.2014.
 */
public class Match {

    protected String team1;
    protected String team2;
    protected int team1Score;
    protected int team2Score;
    protected Date date;
    protected int competitionId;

    public Match() {
    }

    public Match(String team1, String team2, int team1Score, int team2Score, Date date, int competitionId) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.date = date;
        this.competitionId = competitionId;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "Match{" +
                "team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", team1Score=" + team1Score +
                ", team2Score=" + team2Score +
                ", date=" + date +
                ", competitionId=" + competitionId +
                '}';
    }
}
