package com.team34rockets.scoutingapp.models;

import java.util.List;

public class Competition {

    private List<Team> teamList;

    public Competition(List<Team> teamList) {
        this.teamList = teamList;
    }

    public Team get(int position) {
        return teamList.get(position);
    }

    public void add(Team team, int position) {
        teamList.set(position, team);
    }

    public void add(Team team) {
        teamList.add(team);
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public Team getTeamByNumber(int teamNumber) {
        for (Team team : teamList) {
            if (teamNumber == team.getNumber()) {
                return team;
            }
        }
        return null;
    }

    public double getHighestApr() {
        double score = 0.0;
        for (Team team : teamList) {
            if (team.getApr() > score) {
                score = team.getApr();
            }
        }
        return score;
    }

    public int getTeams() {
        return this.teamList.size();
    }
}
