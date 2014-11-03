package com.example.RuFoos.team;

import com.example.RuFoos.domain.Team;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface TeamData {

        public Team getTeamByName(String name);
        public int addTeam(Team team);
}
