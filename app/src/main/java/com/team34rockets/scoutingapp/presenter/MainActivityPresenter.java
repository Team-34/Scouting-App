package com.team34rockets.scoutingapp.presenter;

import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.models.Team;

import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View view;
    List<Team> teamList;

    @Override
    public void onCreate() {
        view.updateTeamList(teamList);
    }

    @Override
    public void displayTeam(Team team) {

    }

    @Override
    public void attach(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {

    }

    public void refresh() {

    }
}
