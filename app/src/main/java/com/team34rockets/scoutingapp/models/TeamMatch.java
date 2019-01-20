package com.team34rockets.scoutingapp.models;

public class TeamMatch {

    private Team team;

    private double apr;

    private int blueScore;
    private int redScore;


    public TeamMatch(Team team, double apr, int blueScore, int redScore) {
        this.team = team;
        this.apr = apr;
        this.blueScore = blueScore;
        this.redScore = redScore;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }
}
