package com.example.RuFoos.match;

import com.example.RuFoos.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface MatchService {

    public int addMatch(Match match);
    public QuickMatch quickMatchSignUp(User user, String token);
    public QuickMatch getQuickMatchById(String id);
    public QuickMatch leaveQuickMatch(String token);
    public TeamMatch registerTeamMatch(TeamMatch teamMatch, String token);
    public QuickMatch confirmPickup(String token);
    public ExhibitionMatch registerExhibitionMatch(ExhibitionMatch exhibitionMatch, String token, String pickupId);
    public ArrayList<Match> getMatches(String username);
}
