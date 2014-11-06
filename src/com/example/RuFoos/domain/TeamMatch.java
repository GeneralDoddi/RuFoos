package com.example.RuFoos.domain;

import java.util.Date;

/**
 * Created by thordurth on 6.11.2014.
 */
public class TeamMatch {

    private String id;
    private String winnerteam;
    private String loserteam;
    private boolean underTable;

    public TeamMatch() {
    }

    public TeamMatch(String id, String winnerteam, String loserteam, boolean underTable) {
        this.id = id;
        this.winnerteam = winnerteam;
        this.loserteam = loserteam;
        this.underTable = underTable;
    }

    public TeamMatch(String winnerteam, String loserteam, boolean underTable) {
        this.winnerteam = winnerteam;
        this.loserteam = loserteam;
        this.underTable = underTable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWinnerteam() {
        return winnerteam;
    }

    public void setWinnerteam(String winnerteam) {
        this.winnerteam = winnerteam;
    }

    public String getLoserteam() {
        return loserteam;
    }

    public void setLoserteam(String loserteam) {
        this.loserteam = loserteam;
    }

    public boolean isUnderTable() {
        return underTable;
    }

    public void setUnderTable(boolean underTable) {
        this.underTable = underTable;
    }
}
