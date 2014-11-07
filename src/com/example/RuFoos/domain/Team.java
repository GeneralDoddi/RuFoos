package com.example.RuFoos.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * * Domain class for team in ruFoos
 * Created by BearThor on 2.11.2014.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Team {

    protected String id;
    protected String name;
    protected String p1;
    protected String p2;
    protected int wins;
    protected int losses;
    protected int underTable;
    protected int version;

    public Team() {
    }

    @JsonCreator
    public Team(@JsonProperty("_id") String id, @JsonProperty("__v") int version,
                @JsonProperty("name") String name, @JsonProperty("p1") String p1,
                @JsonProperty("p2") String p2, @JsonProperty("wins") int wins,
                @JsonProperty("losses") int losses, @JsonProperty("underTable") int underTable)
     {
        this.id = id;
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        this.wins = wins;
        this.losses = losses;
        this.underTable = underTable;
        this.version = version;

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
