package com.example.RuFoos.domain;

import java.util.Date;
import java.util.List;

/**
 * Domain class for matches in ruFoos
 * Created by BearThor on 2.11.2014.
 */
public class Match {

    // Exhibition match objects
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


/*    @JsonCreator
    public TeamMatch(@JsonProperty("_id")String id,@JsonProperty("__v") int version,@JsonProperty("winnerteam") String winnerteam,@JsonProperty("loserteam") String loserteam,@JsonProperty("undertable") boolean underTable) {
*/
        // Exhibition match
    public Match(String id, int version, List<String> winners, List<String> losers, Date date, boolean underTable) {
        this.id = id;
        this.version = version;
        this.winners = winners;
        this.losers = losers;
        this.date = date;
        this.underTable = underTable;
    }

    // Team match
    public Match(String id, int version, String winnerteam, String loserteam, boolean underTable) {
        this.id = id;
        this.version = version;
        this.winnerteam = winnerteam;
        this.loserteam = loserteam;
        this.underTable = underTable;
    }






}
