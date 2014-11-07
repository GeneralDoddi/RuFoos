package com.example.RuFoos.domain;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Domain class for matches in ruFoos
 * Created by BearThor on 2.11.2014.
 */
public class Match {

    private String id;
    private int version;
    private List<String> winners;
    private List<String> losers;
    private Date date;
    private String winnerteam;
    private String loserteam;
    private boolean underTable;

    public Match() {
    }

    @JsonCreator
    public Match(@JsonProperty("_id") String id, @JsonProperty("__v") int version,
                 @JsonProperty("winners") List<String> winners, @JsonProperty("losers") List<String> losers,
                 @JsonProperty("date") Date date, @JsonProperty("underTable") boolean underTable,
                 @JsonProperty("winnerteam") String winnerteam, @JsonProperty("loserteam") String loserteam) {
        this.id = id;
        this.version = version;
        this.winners = winners;
        this.losers = losers;
        this.date = date;
        this.underTable = underTable;
        this.winnerteam = winnerteam;
        this.loserteam = loserteam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }

    public List<String> getLosers() {
        return losers;
    }

    public void setLosers(List<String> losers) {
        this.losers = losers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
