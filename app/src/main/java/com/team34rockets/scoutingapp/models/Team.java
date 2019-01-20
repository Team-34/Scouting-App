package com.team34rockets.scoutingapp.models;

import java.util.List;

public class Team {

    private long number;
    private String name;

    private int rank;
    private double apr;
    private double opr;
    private double dpr;
    private double ccwm;

    private List<TeamMatch> matchHistory;


    public Team(long number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public double getOpr() {
        return opr;
    }

    public void setOpr(double opr) {
        this.opr = opr;
    }

    public double getDpr() {
        return dpr;
    }

    public void setDpr(double dpr) {
        this.dpr = dpr;
    }

    public double getCcwm() {
        return ccwm;
    }

    public void setCcwm(double ccwm) {
        this.ccwm = ccwm;
    }

    public List<TeamMatch> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(List<TeamMatch> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
