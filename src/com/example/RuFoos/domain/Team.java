package com.example.RuFoos.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * * Domain class for team in ruFoos
 * Created by BearThor on 2.11.2014.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Team {

    protected String name;
    protected String p1;
    protected String p2;
    protected int wins;
    protected int losses;
    protected int underTable;

    public Team() {
    }

    public Team(String name, String p1, String p2, int wins, int losses, int underTable) {
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        this.wins = wins;
        this.losses = losses;
        this.underTable = underTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
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
                "name='" + name + '\'' +
                ", p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", underTable=" + underTable +
                '}';
    }
}
