package com.example.RuFoos.team;

import com.example.RuFoos.domain.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public interface TeamService {

        public Team getTeamByName(String name);
        public String addTeam(Team team, String token);
        public List<Team> getAllTeams();
        public ArrayList<Team> getMyTeams(String username);
}
