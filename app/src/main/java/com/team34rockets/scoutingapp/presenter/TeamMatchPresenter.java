package com.team34rockets.scoutingapp.presenter;

import com.google.gson.Gson;
import com.team34rockets.scoutingapp.adapters.ScoutingQuestionAdapter;
import com.team34rockets.scoutingapp.contracts.TeamMatchContract;
import com.team34rockets.scoutingapp.contracts.TeamMatchContract.View;
import com.team34rockets.scoutingapp.models.Competition;

public class TeamMatchPresenter implements TeamMatchContract.Presenter {

    private View view;
    private Competition competition;
    private ScoutingQuestionAdapter scoutingQuestionAdapter;

    @Override
    public void attach(View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void onCreate() {
        competition = new Gson().fromJson(view.retrieveExtra(), Competition.class);
        int position = view.getActivity().getIntent().getIntExtra("position", 0);
        int teamNumber = view.getActivity().getIntent().getIntExtra("teamNumber",
                34);
        scoutingQuestionAdapter = new ScoutingQuestionAdapter(
                competition.getTeamByNumber(teamNumber).getScoutingReports(), view.getContext());
    }
}
