package com.team34rockets.scoutingapp.presenter;

import com.team34rockets.scoutingapp.contracts.TeamViewContract;

public class TeamViewPresenter implements TeamViewContract.Presenter {
    private TeamViewContract.View view;

    @Override
    public void onCreate() {

    }

    @Override
    public void matchSelect() {

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
