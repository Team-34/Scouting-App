package com.team34rockets.scoutingapp.presenter;

import android.view.View;

import com.google.gson.Gson;
import com.team34rockets.scoutingapp.adapters.MatchListAdapter;
import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.models.Competition;
import com.team34rockets.scoutingapp.models.Match;
import com.team34rockets.scoutingapp.models.ScoutingReport;

import java.util.ArrayList;
import java.util.List;

public class TeamViewPresenter implements TeamViewContract.Presenter {
    private TeamViewContract.View view;
    private Competition competition;
    private MatchListAdapter matchListAdapter;

    @Override
    public void onCreate() {
        competition = new Gson().fromJson(view.getTeamString(), Competition.class);
        matchListAdapter = new MatchListAdapter(generateMatches(view.getPosition()),
                view.getContext());
        matchListAdapter.setItemClickListener(new MatchListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        view.updateTeamData(competition.getTeam(view.getPosition()), matchListAdapter);

    }

    @Override
    public void matchSelect() {

    }

    private List<Match> generateMatches(int position) {
        List<Match> matches = new ArrayList<>();
        for (ScoutingReport report : competition.getTeam(position).getScoutingReports()) {
            matches.add(new Match(report));
        }
        return matches;
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
