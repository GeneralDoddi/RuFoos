package com.example.RuFoos.match;

import com.example.RuFoos.domain.Match;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface MatchService {

    public int addMatch(Match match);
    public QuickMatch quickMatchSignUp(User user);
    public QuickMatch getQuickMatchById(String id);
    public QuickMatch leaveQuickMatch(User user);
}
