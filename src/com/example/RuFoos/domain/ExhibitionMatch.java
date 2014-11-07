package com.example.RuFoos.domain;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by thordurth on 6.11.2014.
 */
public class ExhibitionMatch {

    private String id;
    private int version;
    private List<String> winners;
    private List<String> losers;
    private boolean underTable;
    private Date date;

    public ExhibitionMatch() {
    }

    @JsonCreator
    public ExhibitionMatch(@JsonProperty("_id") String id, @JsonProperty("__v") int version, @JsonProperty("winners") List<String> winners, @JsonProperty("losers") List<String> losers, @JsonProperty("underTable") boolean underTable, @JsonProperty("date") Date date){

        this.id = id;
        this.winners = winners;
        this.losers = losers;
        this.underTable = underTable;
        this.version = version;
        this.date = date;
    }

    public ExhibitionMatch(int version ,List<String> winners, List<String> losers, boolean underTable) {
        this.winners = winners;
        this.losers = losers;
        this.underTable = underTable;
        this.version = version;
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

    public boolean isUnderTable() {
        return underTable;
    }

    public void setUnderTable(boolean underTable) {
        this.underTable = underTable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
