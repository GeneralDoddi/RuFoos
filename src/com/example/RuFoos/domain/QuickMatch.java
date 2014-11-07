package com.example.RuFoos.domain;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gadi on 5.11.2014.
 */
public class QuickMatch {
    private String id;
    int version;
    private boolean full;
    private String[] players;
    private boolean[] ready;

    public QuickMatch() {
    }

    @JsonCreator
    public QuickMatch(@JsonProperty("_id") String id, @JsonProperty("__v") int version, @JsonProperty("full") boolean full, @JsonProperty("players") String[] players, @JsonProperty("ready") boolean[] ready) {
        this.id = id;
        this.version = version;
        this.full = full;
        this.players = players;
        this.ready = ready;
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

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public boolean[] getReady() {
        return ready;
    }
}
