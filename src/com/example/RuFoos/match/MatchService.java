package com.example.RuFoos.match;

import com.example.RuFoos.domain.*;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface MatchService {

    public int addMatch(Match match);
    public QuickMatch quickMatchSignUp(User user);
    public QuickMatch getQuickMatchById(String id);
    public QuickMatch leaveQuickMatch(User user);
    public TeamMatch registerTeamMatch(TeamMatch teamMatch);
    public QuickMatch confirmPickup(String token);
    public ExhibitionMatch registerExhibitionMatch(ExhibitionMatch exhibitionMatch);
}
