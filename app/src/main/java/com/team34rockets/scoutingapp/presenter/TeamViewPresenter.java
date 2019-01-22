package com.team34rockets.scoutingapp.presenter;

import com.google.gson.Gson;
import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.models.Competition;

public class TeamViewPresenter implements TeamViewContract.Presenter {
    private TeamViewContract.View view;
    private Competition competition;

    @Override
    public void onCreate() {
        competition = new Gson().fromJson(view.getTeamString(), Competition.class);
        view.updateTeamData(competition.get(view.getPosition()));
    }

    @Override
    public void matchSelect() {

    }

    @Override
    public Competition getCompetition() {
        return competition;
    }

    @Override
    public void attach(TeamViewContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}
