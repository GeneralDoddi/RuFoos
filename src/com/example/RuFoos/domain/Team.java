package com.example.RuFoos.domain;

/**
 * * Domain class for team in ruFoos
 * Created by BearThor on 2.11.2014.
 */
public class Team {

    protected String teamName;
    protected String player1;
    protected String player2;
    protected int wins;
    protected int losses;
    protected int underTable;

    public Team() {

    }

    public Team(String teamName, String player1, String player2, int wins, int losses, int underTable) {
        this.teamName = teamName;
        this.player1 = player1;
        this.player2 = player2;
        this.wins = wins;
        this.losses = losses;
        this.underTable = underTable;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getUnderTable() {
        return underTable;
    }

    public void setUnderTable(int underTable) {
        this.underTable = underTable;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", underTable=" + underTable +
                '}';
    }
}
