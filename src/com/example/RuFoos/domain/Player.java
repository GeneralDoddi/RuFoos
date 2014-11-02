package com.example.RuFoos.domain;

/**
 * Domain class for player in ruFoos
 * Created by BearThor on 2.11.2014.
 */
public class Player {

    protected int wins;
    protected int lossses;
    protected int underTable;

    public Player() {
    }

    public Player(int wins, int lossses, int underTable) {
        this.wins = wins;
        this.lossses = lossses;
        this.underTable = underTable;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLossses() {
        return lossses;
    }

    public void setLossses(int lossses) {
        this.lossses = lossses;
    }

    public int getUnderTable() {
        return underTable;
    }

    public void setUnderTable(int underTable) {
        this.underTable = underTable;
    }

    @Override
    public String toString() {
        return "Player{" +
                "wins=" + wins +
                ", lossses=" + lossses +
                ", underTable=" + underTable +
                '}';
    }
}
